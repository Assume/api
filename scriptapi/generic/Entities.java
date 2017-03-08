package scripts.api.scriptapi.generic;

import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.interfaces.Clickable;
import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Game;
import org.tribot.api2007.Player;
import org.tribot.api2007.Projection;
import org.tribot.api2007.types.RSCharacter;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSModel;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

/**
 * The Entities class is used to get information about entities.
 * <p>
 * @author Nolan
 */
public class Entities {

    /**
     * Gets the area of the specified model.
     * <p>
     * @param model The model.
     * @return The area.
     */
    public static Polygon getModelArea(final RSModel model) {
        if (model == null) {
            return new Polygon();
        }
        return model.getEnclosedArea();
    }

    /**
     * Checks to see if the bot mouse is hovering inside the specified model.
     * <p>
     * @param model The model.
     * @return True if the bot mouse is hovering inside the model, false otherwise.
     */
    public static boolean isHovering(final RSModel model) {
        final Polygon area = getModelArea(model);
        return area != null && area.contains(Mouse.getPos());
    }

    /**
     * Returns an RSTile represented by the specified Point.
     *
     * @param p The Point that is specified.
     * @return An RSTile represented by the specified Point.
     */
    public static RSTile pointToRSTile(Point p) {
        return new RSTile(p.x, p.y);
    }

    /**
     * Returns a string representation of a Positionable.
     *
     * @param pos The Positionable that is being converted into a String.
     * @return A string representation of a Positionable.
     */
    public static String tileToString(final Positionable pos) {
        final RSTile tile = pos != null ? pos.getPosition() : null;
        return tile != null ? "[X: " + tile.getPosition().getX() + " | Y: " + tile.getPosition().getY() + " | P: " + tile.getPosition().getPlane() + "]" : "null";
    }
   

    /**
     * Compares the distance between the player and the two specified Positionables using the distance formula. Returns true if the first Positionable is
     * closer, false if the
     * second Positionable is closer.
     *
     * @param pos1 The first Positionable.
     * @param pos2 The second Positionable.
     * @return True if the first Positionable is closer, false if the second Positionable is closer.
     */
    public static boolean compare(final Positionable pos1, final Positionable pos2) {
        return distanceTo(pos1) < distanceTo(pos2);
    }

    /**
     * Uses the specified RSItem on the specified Clickable. If the Clickable is also a Positionable, the Positionable will be walked to.
     *
     * @param item      The RSItem.
     * @param clickable The Clickable.
     * @return True if the item was successfully used, false otherwise.
     */
  
    /**
     * Returns true if the specified Positionable is on the screen, false otherwise.
     *
     * @param position The Positionable being checked.
     * @return True if the specified Positionable is on the screen, false otherwise.
     */
    public static boolean isOnScreen(Positionable position) {
        if (position == null) {
            return false;
        }
        if (position instanceof RSObject) {
            return ((RSObject) position).isOnScreen();
        } else if (position instanceof RSTile) {
            return ((RSTile) position).isOnScreen();
        } else if (position instanceof RSCharacter) {
            return ((RSCharacter) position).isOnScreen();
        } else if (position instanceof RSObject) {
            return ((RSObject) position).isOnScreen();
        } else if (position instanceof RSGroundItem) {
            return ((RSGroundItem) position).isOnScreen();
        } else {
            return false;
        }
    }

    /**
     * Returns true if the specified Positionable is on the screen, false otherwise.
     *
     * In order for this method to return true, the center point of the Positionable's model must be on the screen. If the Positionable's model is null,
     * {@link #isOnScreen(Positionable)} is used.
     *
     * @param position The Positionable being checked.
     * @return True if the specified Positionable is on the screen, false otherwise.
     */
    public static boolean isCenterOnScreen(Positionable position) {
        if (position == null) {
            return false;
        }
        RSModel model;
        if (position instanceof RSObject) {
            model = ((RSObject) position).getModel();
        } else if (position instanceof RSCharacter) {
            model = ((RSCharacter) position).getModel();
        } else if (position instanceof RSGroundItem) {
            model = ((RSGroundItem) position).getModel();
        } else {
            model = null;
        }
        if (model != null) {
            Point p = model.getCentrePoint();
            return p != null && Projection.isInViewport(p);
        } else {
            return isOnScreen(position);
        }
    }

    /**
     * Checks if any of the specified Positionables are on the screen.
     *
     * @param positions The array of Positionables.
     * @return True if any of the specified Positionables are on the screen, false otherwise.
     * @see #isOnScreen(Positionable)
     */
    public static boolean isOnScreen(Positionable... positions) {
        if (positions == null || positions.length == 0) {
            return false;
        }
        for (Positionable position : positions) {
            if (position != null && isOnScreen(position)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if any of the specified Positionables are on the screen.
     *
     * This method checks for the center point using {@link #isCenterOnScreen(Positionable)}.
     *
     * @param positions The array of Positionables.
     * @see #isCenterOnScreen(Positionable)
     * @return True if any of the specified Positionables are on the screen, false otherwise.
     */
    public static boolean isCenterOnScreen(Positionable... positions) {
        if (positions == null || positions.length == 0) {
            return false;
        }
        for (Positionable position : positions) {
            if (position != null && isCenterOnScreen(position)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Gets the positionable nearest to your player from the specified array of positionables using the distance formula.
     * <p>
     * @param positionables The positionables to search through.
     * @return The positionable nearest to your player.
     * @see #distanceTo(org.tribot.api.interfaces.Positionable)
     */
    public static Positionable getNearest(Positionable... positionables) {
        return getNearest(Player.getPosition(), positionables);
    }

    /**
     * Gets the positionable nearest to the specified position from the specified array of positionables using the distance formula.
     * <p>
     * @param position      The position to sort from.
     * @param positionables The positionables to search through.
     * @return The positionable nearest to the specified position.
     * @see #distanceTo(org.tribot.api.interfaces.Positionable)
     */
    public static Positionable getNearest(Positionable position, Positionable... positionables) {
        Positionable nearest = null;
        for (Positionable positonable : positionables) {
            if (nearest == null || distanceTo(positonable, position) < distanceTo(nearest, position)) {
                nearest = positonable;
            }
        }
        return nearest;
    }
    /**
     * Returns the distance between the player's position and the specified Positionable.
     * <p>
     * @param pos The Positionable being tested.
     * @return The distance between the player's position and the specified Positionable.
     */
    public static final double distanceTo(Positionable pos) {
        return distanceTo(pos, Player.getPosition());
    }

    /**
     * Returns the distance between the two Positionables.
     * <p>
     * @param pos1 The first Positionable being tested.
     * @param pos2 The second Positionable being tested.
     * @return The distance between the two Positionables.
     */
    public static final double distanceTo(Positionable pos1, Positionable pos2) {
        if (pos1 == null || pos2 == null) {
            return -1;
        }
        return pos1.getPosition().distanceToDouble(pos2.getPosition());
    }
//</editor-fold>
}
