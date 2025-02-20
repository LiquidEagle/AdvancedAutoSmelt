package me.pulsi_.advancedautosmelt.utils;

import me.pulsi_.advancedautosmelt.values.ConfigValues;

public class AASFormatter {

    private static final String[] order = new String[]
            {
                    "", ConfigValues.getThousands(), ConfigValues.getMillions(), ConfigValues.getBillions(),
                    ConfigValues.getTrillions(), ConfigValues.getQuadrillions(), ConfigValues.getQuintillions()
            };

    private static final int limit = order.length - 1;

    /**
     * Return numbers like as 23.54k, 765.536M, 91.3T.
     * This formatting method correspond to the placeholder %money_formatted%.
     *
     * @param amount The amount.
     * @return A string.
     */
    public static String formatPrecise(double amount) {
        double thousand = 1000, scale = thousand;
        int count = 0;

        while (amount >= scale && count < limit) {
            scale = scale * thousand;
            count++;
        }

        return amount / (scale / thousand) + order[count];
    }

    /**
     * Return numbers like as 657k, 123k, 97M.
     * This formatting method correspond to the placeholder %money_formatted_long%.
     *
     * @param amount The amount.
     * @return A string.
     */
    public static String formatLong(double amount) {
        double thousand = 1000, scale = thousand;
        int count = 0;

        while (amount >= scale && count < limit) {
            scale = scale * thousand;
            count++;
        }

        return (int) (amount / (scale / thousand)) + order[count];
    }

    /**
     * Return numbers like as 14.243,12, 75.249, 231.785.
     * This formatting method correspond to the placeholder %balance%.
     *
     * @param amount The amount.
     * @return A string.
     */
    public static String formatCommas(double amount) {
        String number = String.valueOf(amount), numbers = number, decimals = "", result;

        if (number.contains(".")) { // In BigDecimals numbers, decimals are divided by .
            String[] split = number.split("\\.");
            numbers = split[0];
            decimals = split[1];
        }

        String thousands = ConfigValues.getThousandsSeparator(), tens = ConfigValues.getDecimalsSeparator();
        StringBuilder builder = new StringBuilder();
        for (int i = numbers.length() - 1, count = 0; i >= 0; i--, count++) {
            if (count >= 3) {
                builder.append(thousands);
                count = 0;
            }
            builder.append(numbers.charAt(i));
        }
        builder.reverse();
        result = builder + (decimals.isEmpty() ? "" : (tens + decimals));

        return result;
    }
}