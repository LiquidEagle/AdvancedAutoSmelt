package me.pulsi_.advancedautosmelt.managers;

import me.pulsi_.advancedautosmelt.AdvancedAutoSmelt;
import me.pulsi_.advancedautosmelt.utils.AASLogger;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class AASConfigs {

    private static final String configVersion = "Config-Version";

    private final AdvancedAutoSmelt plugin;

    boolean isToUpdate = false;

    public AASConfigs(AdvancedAutoSmelt plugin) {
        this.plugin = plugin;

        if (getFile("config.yml").exists()) {
            FileConfiguration config = getConfig("config.yml");

            String version = config.getString(configVersion);
            if (version == null) isToUpdate = true;
            else {
                boolean autoUpdate = config.getBoolean("Auto-Update");
                if (!autoUpdate) isToUpdate = false;
                else {
                    String plVersion = plugin.getDescription().getVersion();
                    isToUpdate = !plVersion.equals(version);

                    File configFile = getFile("config.yml");
                    if (isToUpdate && configFile.exists()) {
                        config.set(configVersion, plVersion);
                        try { // Set the config to the updated version to load it in the setup method correctly.
                            config.save(configFile);
                        } catch (IOException e) {
                            AASLogger.warn("Could not update config file version: " + e.getMessage());
                        }
                    }
                }
            }
        }
    }

    /**
     * Method to call after the constructor to generate, and in case, update the existing files.
     */
    public void setupConfigFiles() {
        setupFile("config.yml");
        setupFile("messages.yml");
        if (!getFile("sell_prices.yml").exists())
            plugin.saveResource("sell_prices.yml", false);

        isToUpdate = false;
    }

    /**
     * Load and return the specified config file.
     *
     * @param name The name of the config file.
     * @return The config file loaded on the moment.
     */
    public FileConfiguration getConfig(String name) {
        YamlConfiguration config = new YamlConfiguration();
        File file = getFile(name);
        if (file == null) {
            AASLogger.warn("Could not load configuration file \"" + name + "\" because it does not exist.");
            return config;
        }

        try {
            config.load(file);
        } catch (Exception e) {
            AASLogger.error(e, "Could not load configuration file \"" + name + "\".");
        }
        return config;
    }

    public File getFile(String name) {
        return new File(plugin.getDataFolder(), name);
    }

    /**
     * Set up the file, create it if missing or update it if necessary.
     *
     * @param fileName The name of the file.
     */
    public void setupFile(String fileName) {
        File folderFile = getFile(fileName);
        boolean exist = folderFile.exists();

        if (!exist) {
            plugin.saveResource(fileName, false);
            return;
        } else if (!isToUpdate) return;

        HashMap<Integer, FileLine> file = new HashMap<>();

        List<String> fileAsList = new ArrayList<>();
        Scanner scanner = new Scanner(new InputStreamReader(plugin.getResource(fileName)));
        while (scanner.hasNext()) fileAsList.add(scanner.nextLine());

        YamlConfiguration folderConfig = YamlConfiguration.loadConfiguration(folderFile), jarConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(plugin.getResource(fileName)));

        int position = 1, size = fileAsList.size();
        HashMap<Integer, String> headers = new HashMap<>();

        boolean isInSection = false;
        int sectionPoint = 0;

        for (int i = 0; i < size; i++) {
            String line = fileAsList.get(i);
            if (isListContent(line)) continue; // If it's content of a list, skip and add it later

            if (line.isEmpty() || isComment(line) || !line.contains(":")) {
                file.put(position, new FileLine(line, false, false, false, false));
                position++;
                continue;
            }

            int point = getPoint(line);
            if (isInSection) {
                if (point <= sectionPoint) isInSection = false;
                else continue;
            }

            String key = getKey(line);
            String[] split = line.split(":");

            boolean isHeader = split.length == 1 || isComment(split[1]);
            if (isHeader) headers.put(point, key);

            // If is the start of a section, add the key and register all the sections.
            boolean isSection = isSection(line);
            if (isSection) {
                file.put(position, new FileLine(line, false, false, false, true));
                isInSection = true;
                sectionPoint = point;

                position++;
                continue;
            }

            boolean isValue = split.length > 1 && !isComment(split[1]);
            boolean isListStart = isHeader && i + 1 < size && isListContent(fileAsList.get(i + 1)); // As last condition, check if the next file line is content of the list.

            file.put(position, new FileLine(line, isValue, isHeader, isListStart, false));
            position++;
        }

        StringBuilder builder = new StringBuilder();
        headers.clear();

        for (int pos = 1; pos < position; pos++) {
            FileLine fileLine = file.get(pos);

            String line = fileLine.getLine(), key = getKey(line);
            int point = getPoint(line);

            if (fileLine.isHeader() && !fileLine.isListStart()) {
                headers.put(point, key);
                builder.append(line).append("\n");
                continue;
            }

            if (fileLine.isListStart()) {
                addKey(builder, key, point);

                // Get the list from the user config and place it where it should be.
                // Use the path of the list with all the headers behind with #getPath();
                String path = getPath(headers, point, key);

                List<String> value;
                if (exist) value = folderConfig.getStringList(path);
                else value = jarConfig.getStringList(path);

                if (value.isEmpty()) builder.append(" []\n");
                else {
                    builder.append("\n");
                    for (String listLine : value) {
                        addSpaces(builder, point + 1);
                        builder.append("- \"").append(listLine).append("\"\n");
                    }
                }
                continue;
            }

            if (fileLine.isValue()) {
                addKey(builder, key, point);

                // Use the path of the list with all the headers behind with #getPath();
                String path = getPath(headers, point, key);
                Object value = folderConfig.get(path);
                if (!exist || value == null) value = jarConfig.get(path);

                if (value instanceof String) builder.append(" \"").append(value).append("\"\n");
                else builder.append(" ").append(value).append("\n");
                continue;
            }

            if (fileLine.isSection()) {
                builder.append(line.replace("$", "")).append("\n");

                ConfigurationSection section = folderConfig.getConfigurationSection(getPath(headers, point, key.replace("$", "")));
                if (section != null) writeSection(section, builder, point);
                else writeSection(jarConfig.getConfigurationSection(getPath(headers, point, key)), builder, point);
                continue;
            }

            builder.append(line).append("\n");
        }
        recreateFile(folderFile, builder.toString());
    }

    public void recreateFile(File file, String fileBuilder) {
        if (fileBuilder == null) return;
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(fileBuilder);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            AASLogger.error(e, e.getMessage());
        }
    }

    private String getKey(String s) {
        if (!s.contains(":")) return null;
        return s.replace(" ", "").split(":")[0];
    }

    private String getPath(HashMap<Integer, String> headers, int point, String key) {
        StringBuilder path = new StringBuilder();
        for (int i = 0; i <= point - 1; i++) {
            String header = headers.get(i);
            if (header != null) path.append(header).append(".");
        }
        return path.append(key).toString();
    }

    private int getPoint(String s) {
        int spaces = 0;
        for (char c : s.toCharArray()) {
            if (c == ' ') spaces++;
            else break;
        }
        return spaces / 2;
    }

    private boolean isComment(String s) {
        return s.replace(" ", "").startsWith("#");
    }

    private boolean isListContent(String s) {
        return s.replace(" ", "").startsWith("-");
    }

    private boolean isSection(String s) {
        return s.replace(" ", "").startsWith("$");
    }

    private void addSpaces(StringBuilder builder, int point) {
        for (int i = 0; i < point * 2; i++) builder.append(" ");
    }

    private void addKey(StringBuilder builder, String key, int point) {
        addSpaces(builder, point);
        builder.append(key).append(":");
    }

    private void writeSection(ConfigurationSection section, StringBuilder builder, int point) {
        for (String key : section.getKeys(false)) {
            addKey(builder, key, point + 1);

            ConfigurationSection possibleSection = section.getConfigurationSection(key);
            if (possibleSection != null) {
                builder.append("\n");
                writeSection(possibleSection, builder, point + 1);
                continue;
            }

            List<String> list = section.getStringList(key);
            if (list.isEmpty()) {
                Object value = section.get(key);
                if (value == null) {
                    builder.append(" []\n");
                    continue;
                }

                if (value instanceof String) builder.append(" \"").append(value).append("\"\n");
                else builder.append(" ").append(value).append("\n");
            } else {
                builder.append("\n");
                for (String listLine : list) {
                    addSpaces(builder, point + 2);
                    builder.append("- \"").append(listLine).append("\"\n");
                }
            }
        }
    }

    private static class FileLine {

        private final String line;
        private final boolean isValue, isHeader, isListStart, isSection;

        public FileLine(String line, boolean isValue, boolean isHeader, boolean isListStart, boolean isSection) {
            this.line = line;
            this.isValue = isValue;
            this.isHeader = isHeader;
            this.isListStart = isListStart;
            this.isSection = isSection;
        }

        public String getLine() {
            return line;
        }

        public boolean isValue() {
            return isValue;
        }

        public boolean isHeader() {
            return isHeader;
        }

        public boolean isListStart() {
            return isListStart;
        }

        public boolean isSection() {
            return isSection;
        }
    }
}