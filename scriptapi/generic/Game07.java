package scripts.api.scriptapi.generic;

import org.tribot.api2007.Game;
import org.tribot.api2007.Player;

/**
 * The Game07 class is a utility class that provides information about the game state.
 * <p>
 * @author Nolan
 */
public class Game07 {

    /**
     * A constant representing the loaded game state.
     */
    private static final int LOADED = 30;

    /**
     * A constant representing the loading game state.
     */
    private static final int LOADING = 25;

    /**
     * Gets the game state.
     * <p>
     * @return The game state.
     */
    public static final int getState() {
        return Game.getGameState();
    }

    /**
     * Checks to see if the game is loaded.
     * <p>
     * @return True if the game is loaded, false otherwise.
     */
    public static boolean isGameLoaded() {
        return Game.getGameState() == LOADED;
    }

    /**
     * Check to see if the game is loading.
     * <p>
     * @return True if the game is loading, false otherwise.
     */
    public static boolean isGameLoading() {
        return Game.getGameState() == LOADING;
    }

    /**
     * Returns true if the 07 TRiBot client is loaded, and false otherwise.
     * <p>
     * @return true if the 07 TRiBot client is loaded, and false otherwise.
     */
    public static boolean isTRiBot() {
        try {
            Player.getRSPlayer();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
