package scripts.api.scriptapi.generic;

import org.tribot.api2007.Game;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.api2007.types.RSCharacter;
import org.tribot.api2007.types.RSPlayer;
import org.tribot.api2007.types.RSTile;


/**
 * The LocalPlayer class provides a way to safely retrieve information about the local player.
 * <p>
 * @author Nolan
 */
public class Player07 {

    /**
     * Gets the player's name.
     * <p>
     * @return The player's name. If no name was found, an empty string is returned.
     */
    public static final String getName() {
        RSPlayer player = getPlayer();
        if (player == null) {
            return "";
        }
        String name = player.getName();
        return name == null ? "" : name;
    }

    /**
     * Gets the players current hit points as a percent between 0 and 100.
     * <p>
     * @return The players current hit points as a percent.
     */
    public static final int getHPPercent() {
        return (int) (100.0 * ((double) SKILLS.HITPOINTS.getCurrentLevel() / (double) SKILLS.HITPOINTS.getActualLevel()));
    }

    /**
     * Gets the interacting character of the player.
     * <p>
     * @return The interacting character. Null if no such character exists.
     */
    public static final RSCharacter getInteractingCharacter() {
        RSPlayer myPlayer = getPlayer();
        if (myPlayer != null) {
            return myPlayer.getInteractingCharacter();
        }
        return null;
    }

    /**
     * Gets the player as a RSPlayer object.
     * <p>
     * @return The player. Null if the local player is not loaded.
     */
    public static final RSPlayer getPlayer() {
        return Player.getRSPlayer();
    }

    /**
     * Checks to see if the player is animating.
     * <p>
     * @return True if the player is animating, false otherwise.
     */
    public static final boolean isAnimating() {
        return Player.getAnimation() != -1;
    }


    /**
     * Checks to see if the player is poisoned.
     * <p>
     * @return True if the player is poisoned, false otherwise.
     */
    public static final boolean isPoisoned() {
        return Settings.get(Settings.Indexes.Player.POISONED_INDEX) > 0;
    }

    /**
     * Checks to see if the player is immune to poison.
     * <p>
     * A player is immune to poison if the effect of an anti-poison potion is still active.
     * <p>
     * @return True if the player is immune to poison, false otherwise.
     */
    public static final boolean isImmuneToPoison() {
        return Game.getSetting(Settings.Indexes.Player.POISONED_INDEX) < 0;
    }

    /**
     * Returns the player's position, or null if the player's position is not accurate (the game is loading).
     *
     * Will block for 1.5 seconds, or until the game is no longer loading.
     *
     * @return The player's position, or null if the player's position is not accurate (the game is loading).
     */
    public static final RSTile getPosition() {
        Timer t = new Timer(1500);
        t.start();
        RSTile position = reloadPosition(null);
        while (Game07.isGameLoading() && !t.timedOut()) {
            Client.sleep(5);
        }
        return reloadPosition(position);
    }
    
    private static RSTile reloadPosition(RSTile tile) {
        RSTile position = tile;
        if (!Game07.isGameLoading()) {
            position = Player.getPosition();
            Client.sleep(15);
        }
        return position;
    }
}
