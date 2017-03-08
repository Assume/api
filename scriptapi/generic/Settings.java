package scripts.api.scriptapi.generic;

import org.tribot.api2007.Game;

/**
 * The Settings class is a utility class that provides methods related to game settings.
 * <p>
 * @author Nolan
 */
public class Settings {

    /**
     * Gets the value for the setting at the specified index.
     * <p>
     * @param index The index of the setting.
     * @return The value.
     */
    public static final int get(int index) {
        return Game.getSetting(index);
    }

    /**
     * Gets all of the settings values.
     * <p>
     * @return The settings values.
     */
    public static final int[] getAll() {
        int[] settings = Game.getSettingsArray();
        return settings == null ? new int[0] : settings;
    }

    /**
     * Gets all of the setting indexes.
     * <p>
     * @return The setting indexes.
     */
    public static final int[] getAllIndexes() {
        int[] indexes = new int[getAll().length];
        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = i;
        }
        return indexes;
    }

    /**
     * Gets the bits in between the start and end bit (inclusive) of the specified value.
     * <p>
     * Bit position is read from right to left starting at zero and ending at the values binary length - 1.
     * <p>
     * @param value    The value.
     * @param startBit The start bit.
     * @param endBit   The end bit.
     * @return The bits.
     */
    public static final int getBits(int value, int startBit, int endBit) {
        // Clear unnecessary high bits
        int tempValue = value << (31 - endBit);
        // Shift back to the lowest bits
        return tempValue >>> (31 - endBit + startBit);
    }

    /**
     * Gets the bit at the specified location of the specified value.
     * <p>
     * This method treats bit location as if it was read from right to left starting at zero and ending at the values binary length - 1.
     * <p>
     * @param value The value.
     * @param bit   The bit location.
     * @return The bit.
     */
    public static final int getBit(int value, int bit) {
        return getBits(value, bit, bit);
    }

    /**
     * Gets the binary string for the value specified.
     * <p>
     * @param value The value.
     * @return The binary string.
     */
    public static final String getBinary(int value) {
        return Integer.toBinaryString(value);
    }

    /**
     * Gets the hexadecimal string for the value specified.
     * <p>
     * @param value The value.
     * @return The hexadecimal string.
     */
    public static final String getHexadecimal(int value) {
        return Integer.toHexString(value);
    }

    /**
     * A class containing setting index constants
     */
    public static final class Indexes {

        /**
         * Setting indexes for the local player.
         */
        public static final class Player {

            public static final int POISONED_INDEX = 102;
        }

        /**
         * Setting indexes for player owned houses.
         */
        public static final class POH {

            public static final int SERVANT_TASK_INDEX = 738;
            public static final int BUILDING_MODE_INDEX = 780;
        }
    }
}
