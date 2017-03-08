package scripts.api.scriptapi.generic;

import java.util.ArrayList;
import java.util.List;

import org.tribot.api.General;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Game;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSTile;

import scripts.api.scriptapi.antiban.AntiBan;
import scripts.starfox.api2007.banking.Bank07;
import scripts.starfox.api2007.walking.Map07;
import scripts.starfox.manager.moveSet.ActionSet;

/**
 * @author Nolan
 */
public class Walking07 {

    private static ArrayList<RSTile> current_path;
    private static final Object path_lock;

    static {
        path_lock = new Object();
    }

    public static ArrayList<RSTile> getCurrentPath() {
        if (current_path == null) {
            return null;
        }
        synchronized (path_lock) {
            return (ArrayList<RSTile>) current_path.clone();
        }
    }


  
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
     * Attempts to screen-walk to a random reachable nearby tile.
     *
     * @return True if the walking was successful, false otherwise.
     */
    public static boolean walkNearbyTile() {
        final RSTile pos = Player07.getPosition();
        if (pos != null) {
            RSTile tile = null;
            while (tile == null || !Map07.isStandardBlocked(tile)) {
                tile = pos.translate(General.random(-5, 5), General.random(-5, 5));
            }
            return Walking.clickTileMS(tile, "Walk here");
        }
        return false;
    }

    /**
     * Attempts to walk directly to a target tile using the screen.
     *
     * This method will attempt to reach the EXACT tile that you pass in for 7.5 seconds. If the tile that is passed in is not pathable, this method returns false.
     *
     * @param target The target tile.
     * @return True if the target was reached, false otherwise.
     */
    public static boolean walkDirect(RSTile target) {
        RSTile destination = Game.getDestination();
        if (destination != null && destination.equals(target)) {
            return true;
        }
        final RSTile pos = Player07.getPosition();
        if (target != null && pos != null) {
            if (Map07.isStandardBlocked(target)) {
                return false;
            }
            if (!target.isOnScreen()) {
                Camera.turnToTile(target);
                Client.sleep(150, 300);
            }
            Timer timer = new Timer(7500);
            timer.start();
            while (!timer.timedOut() && !Player07.getPosition().equals(target)) {
                Client.sleep(25, 200);
                AntiBan.moveCamera();
                if (!Player.isMoving()) {
                    Walking.clickTileMS(target, "Walk here");
                }
            }
        }
        return false;
    }

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
     * Walks to the nearest bank via {@link Bank07#walkTo()}.
     * <p>
     * @see Bank07#walkTo()
     * @return True if we reached a bank, false otherwise.
     */
    public static boolean walkToBank07() {
        return Bank07.walkTo();
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

    /**
     * Executes the specified of ActionSets in order.
     * <p>
     * @param actionSets The ActionSets.
     * @return True if the ActionSets were successfully executed, false otherwise.
     */
    public static boolean executeActionSets(ActionSet... actionSets) {
        for (ActionSet actionSet : actionSets) {
            if (!actionSet.execute()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Executes the specified of ActionSets in order.
     * <p>
     * @param actionSets The ActionSets.
     * @return True if the ActionSets were successfully executed, false otherwise.
     */
    public static boolean executeActionSets(List<ActionSet> actionSets) {
        return executeActionSets(actionSets.toArray(new ActionSet[actionSets.size()]));
    }
}
