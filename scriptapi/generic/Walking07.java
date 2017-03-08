package scripts.api.scriptapi.generic;

import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Game;
import org.tribot.api2007.Walking;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSTile;

import scripts.api.scriptapi.antiban.AntiBan;

/**
 * @author Nolan
 */
public class Walking07 {
  
    /**
     * Walks in a straight line towards the destination.
     * <p>
     * @param tile The destination.
     * @return True if we reached the destination, false otherwise.
     */
    public static boolean straightWalk(final RSTile tile) {
        if (tile == null) {
            return false;
        }
        if (compareGameDestination(tile) || comparePlayerPosition(tile)) {
            return true;
        }
        return Walking.blindWalkTo(tile, new Condition() {
            @Override
            public boolean active() {
                AntiBan.activateRun();
                return compareGameDestination(tile);
            }
        }, 500);
    }

    /**
     * Walks in a straight line towards the destination.
     * <p>
     * @param tile      The destination.
     * @param condition The stopping condition.
     * @return True if we reached the destination, false otherwise.
     */
    public static boolean straightWalk(final RSTile tile, final Condition condition) {
        if (tile == null) {
            return false;
        }
        if (compareGameDestination(tile) || comparePlayerPosition(tile)) {
            return true;
        }
        return Walking.blindWalkTo(tile, new Condition() {
            @Override
            public boolean active() {
                Client.sleep(100);
                AntiBan.activateRun();
                if (condition != null && condition.active()) {
                    return true;
                }
                return compareGameDestination(tile);
            }
        }, 500);
    }

    /**


    /**
     * Walks to the destination via web walking.
     * <p>
     * @param tile The destination.
     * @return True if we reached the destination, false otherwise.
     */
    public static boolean webWalk(final RSTile tile) {
        if (tile == null) {
            return false;
        }
        return WebWalking.walkTo(tile, new Condition() {
            @Override
            public boolean active() {
                AntiBan.activateRun();
                return compareGameDestination(tile);
            }
        }, 500);
    }

    /**
     * Compares the game destination to the specified tile to see if they are related.
     * <p>
     * @param tile The tile to compare.
     * @return True if the game destination and the specified tile are related, false otherwise.
     */
    private static boolean compareGameDestination(RSTile tile) {
        RSTile game_destination = Game.getDestination();
        if (tile == null || game_destination == null) {
            return false;
        }
        return tile.distanceTo(game_destination) < 1.5;
    }

    /**
     * Compares the player position to the specified tile to see if they are related.
     * <p>
     * @param tile The tile to compare.
     * @return True if the game destination and the specified tile are related, false otherwise.
     */
    private static boolean comparePlayerPosition(RSTile tile) {
        RSTile player_position = Player07.getPosition();
        if (tile == null || player_position == null) {
            return false;
        }
        return tile.equals(player_position);
    }


   
}
