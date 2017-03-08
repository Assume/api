package scripts.api.scriptapi.generic;

import java.awt.Point;
import java.awt.Polygon;
import org.tribot.api.ListenerManager;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Game;
import org.tribot.api2007.Player;
import org.tribot.api2007.Projection;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.interfaces.MouseActions;

/**
 * The Mouse07 class provides utility methods related to the mouse.
 * <p>
 * @author Nolan
 */
public class Mouse07
        implements MouseActions {

    /**
     * The instance of this class.
     */
    private static Mouse07 mouse07;

    /**
     * A constant representing the left mouse button.
     */
    public static final int LEFT_BUTTON = 1;

    /**
     * A constant representing the middle mouse button.
     */
    public static final int MIDDLE_BUTTON = 2;

    /**
     * A constant representing the right mouse button.
     */
    public static final int RIGHT_BUTTON = 3;

    /**
     * The location of the mouse.
     */
    private Point location;

    /**
     * Constructs a new Mouse07.
     */
    private Mouse07() {
        this.location = Mouse.getPos();
    }

    /**
     * Gets the instance of this class.
     * <p>
     * @return The instance.
     */
    private static Mouse07 get() {
        if (mouse07 == null) {
            mouse07 = new Mouse07();
            ListenerManager.add(mouse07);
        }
        return mouse07;
    }

    /**
     * Gets the location of the mouse.
     * <p>
     * @return The location of the mouse.
     */
    public static Point getLocation() {
        return get().location;
    }

    /**
     * Fixes the mouse if there is something selected.
     * <p>
     * @return True if it was fixed, false otherwise.
     */
    public static final boolean fixSelected() {
        if (Game.isUptext("->")) {
            Mouse.click(1);
            return !Game.isUptext("->");
        }
        return true;
    }

    /**
     * Hops the mouse to the specified point and then clicks the specified button.
     * <p>
     * @param p      The point to hop to.
     * @param button The button to click.
     * @param delay  The delay between the hop and the click.
     */
    public static final void hopClick(Point p, int button, long delay) {
        if (p == null) {
            return;
        }
        Mouse.hop(p);
        Client.sleep(delay);
        Mouse.sendPress(p, button);
        Mouse.sendRelease(p, button);
        Mouse.sendClickEvent(p, button);
    }

    /**
     * Hops the mouse to the specified x and y coordinate and then clicks the specified button.
     * <p>
     * @param x      The x coordinate.
     * @param y      The y coordinate.
     * @param button The button to click.
     * @param delay  The delay between the hop and the click.
     */
    public static final void hopClick(int x, int y, int button, long delay) {
        hopClick(new Point(x, y), button, delay);
    }

    /**
     * Moves the mouse to the specified point and then clicks the specified button.
     * <p>
     * @param p      The point to move to.
     * @param button The button to click.
     * @param delay  The delay between the movement and the click.
     */
    public static final void moveClick(Point p, int button, long delay) {
        if (p == null) {
            return;
        }
        Mouse.move(p);
        Client.sleep(delay);
        Mouse.sendPress(p, button);
        Mouse.sendRelease(p, button);
        Mouse.sendClickEvent(p, button);
    }

    /**
     * Moves the mouse to the specified x and y coordinate and then clicks the specified button.
     * <p>
     * @param x      The x coordinate.
     * @param y      The y coordinate.
     * @param button The button to click.
     * @param delay  The delay between the movement and the click.
     */
    public static final void moveClick(int x, int y, int button, long delay) {
        moveClick(new Point(x, y), button, delay);
    }

    /**
     * Checks to see if the mouse is on the specified tile.
     * <p>
     * This method will also return the tile on the minimap.
     * <p>
     * @param tile The tile that is being tested.
     * @return True if the mouse is on the specified tile, false otherwise.
     */
    public static final boolean isMouseOnTile(RSTile tile) {
        if (tile != null) {
            if (tile.isOnScreen()) {
                Polygon polygon = Projection.getTileBoundsPoly(tile, 0);
                return polygon != null && polygon.contains(Mouse.getPos());
            } else {
                //return Calculations.isPointWithinRange(Mouse.getPos(), Projection.tileToMinimap(tile), 4);
            }
        }
        return false;
    }

    /**
     * Gets the tile that the mouse is currently hovering.
     * <p>
     * @return The tile that the mouse is currently hovering. Null if no tile is being hovered.
     */
    public static final RSTile getMouseTile() {
        for (RSTile tile : new RSArea(Player.getPosition(), 20).getAllTiles()) {
            if (isMouseOnTile(tile)) {
                return tile;
            }
        }
        return null;
    }

    @Override
    public void mouseClicked(Point arg0, int arg1, boolean arg2) {
    }

    @Override
    public void mouseMoved(Point point, boolean arg1) {
        get().location = new Point(point);
    }

    @Override
    public void mouseDragged(Point point, int arg1, boolean arg2) {
        get().location = new Point(point);
    }

    @Override
    public void mouseReleased(Point arg0, int arg1, boolean arg2) {
    }
}
