package scripts.api.scriptapi.generic;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;



/**
 * The Inventory07 class is an extension of the TRiBot Inventory class.
 * <p>
 * @author Nolan
 */
public class Inventory07 extends Inventory {

    /**
     * A constant representing the total amount of inventory space.
     */
    private static final int INVENTORY_SPACE = 28;

    /**
     * Checks to see if the inventory contains any items with the specified ID(s).
     * <p>
     * @param ids The ID(s).
     * @return True if the inventory contains any items with the specified ID(s), false otherwise.
     */
    public static boolean contains(int... ids) {
        return find(ids).length > 0;
    }

    /**
     * Checks to see if the inventory contains any items with the specified name(s).
     * <p>
     * @param names The name(s).
     * @return True if the inventory contains any items with the specified name(s), false otherwise.
     */
    public static boolean contains(String... names) {
        return find(names).length > 0;
    }

    /**
     * Checks to see if the inventory contains at least one of each item with the specified ID(s).
     * <p>
     * @param ids The ID(s)
     * @return True if the inventory contains at least one of each item with the specified ID(s), false otherwise.
     */
    public static boolean containsAll(int... ids) {
        for (int id : ids) {
            if (!contains(id)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks to see if the inventory contains at least one of each item with the specified name(s).
     * <p>
     * @param names The name(s)
     * @return True if the inventory contains at least one of each item with the specified name(s), false otherwise.
     */
    public static boolean containsAll(String... names) {
        for (String name : names) {
            if (!contains(name)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks to see if the inventory contains any items with the specified ID(s).
     * <p>
     * @param time The maximum amount of time (in milliseconds) to wait for the inventory to contain the specified ID before returning.
     * @param ids  The ID(s).
     * @return True if the inventory contains any items with the specified ID(s), false otherwise.
     */
    public static boolean waitContains(long time, int... ids) {
        Timer timer = new Timer(time);
        timer.start();
        while (!timer.timedOut()) {
            if (contains(ids)) {
                return true;
            }
            Client.sleep(25);
        }
        return false;
    }

    /**
     * Checks to see if the inventory contains any items with the specified name(s).
     * <p>
     * @param time  The maximum amount of time (in milliseconds) to wait for the inventory to contain the specified name before returning.
     * @param names The name(s).
     * @return True if the inventory contains any items with the specified name(s), false otherwise.
     */
    public static boolean waitContains(long time, String... names) {
        Timer timer = new Timer(time);
        timer.start();
        while (!timer.timedOut()) {
            if (contains(names)) {
                return true;
            }
            Client.sleep(25);
        }
        return false;
    }

    /**
     * Checks to see if the inventory contains only items with the specified ID(s).
     * <p>
     * If the inventory does not contain some or all of the ID's but also contains no other items, this method will return true.
     * <p>
     * @param ids The ID's.
     * @return True if the inventory contains only items with the specified ID(s), false otherwise.
     */
    public static boolean containsOnly(int... ids) {
        return getAll().length <= find(ids).length;
    }

    /**
     * Checks to see if the inventory contains only items with the specified name(s).
     * <p>
     * If the inventory does not contain some or all of the names but also contains no other items, this method will return true.
     * <p>
     * @param names The names.
     * @return True if the inventory contains only items with the specified name(s), false otherwise.
     */
    public static boolean containsOnly(String... names) {
        return getAll().length <= find(names).length;
    }

    /**
     * Gets the count of items in the inventory whose IDs match any of the specified IDs.
     * <p>
     * @param time The maximum amount of time (in milliseconds) to wait for the inventory to contain any items with the specifies IDs.
     * @param ids  The IDs.
     * @return The count of items.
     */
    public static int waitCount(long time, int... ids) {
        Timer timer = new Timer(time);
        timer.start();
        while (!timer.timedOut()) {
            int count = getCount(ids);
            if (count > 0) {
                return count;
            }
            Client.sleep(25);
        }
        return 0;
    }

    /**
     * Gets the count of items in the inventory whose names match any of the specified names.
     * <p>
     * @param time  The maximum amount of time (in milliseconds) to wait for the inventory to contain any items with the specifies names.
     * @param names The IDs.
     * @return The count of items.
     */
    public static int waitCount(long time, String... names) {
        Timer timer = new Timer(time);
        timer.start();
        while (!timer.timedOut()) {
            int count = getCount(names);
            if (count > 0) {
                return count;
            }
            Client.sleep(25);
        }
        return 0;
    }

    /**
     * Converts the specified array of items to a vertical (top-down) array of items.
     * <p>
     * @param items The array to convert.
     * @return The converted array.
     */
    public static RSItem[] getAllVertical(RSItem[] items) {
        if (items.length == 0) {
            return items;
        }
        List<RSItem> itemList = new ArrayList<>(items.length);
        for (int i = 0; i < 4; i++) {
            for (RSItem item : items) {
                if (item.getIndex() == i || item.getIndex() % 4 == i) {
                    itemList.add(item);
                }
            }
        }
        return itemList.toArray(new RSItem[itemList.size()]);
    }

    /**
     * Gets an item whose ID matches the specified ID.
     * <p>
     * @param id The ID.
     * @return An item.
     */
    public static RSItem getItem(int id) {
        RSItem[] items = find(id);
        return items.length > 0 ? items[0] : null;
    }

    /**
     * Gets an item whose name matches the specified name.
     * <p>
     * @param name The name.
     * @return An item.
     */
    public static RSItem getItem(String name) {
        RSItem[] items = find(name);
        return items.length > 0 ? items[0] : null;
    }

    /**
     * Gets the area of the specified item.
     * <p>
     * @param item The item.
     * @return The area of the item. An empty rectangle is returned if the area could not be found.
     */
    public static Rectangle getArea(RSItem item) {
        Rectangle empty = new Rectangle();
        if (item == null) {
            return empty;
        }
        Rectangle rect = item.getArea();
        return rect == null ? empty : rect;
    }

    /**
     * Gets the amount of free space in the inventory.
     * <p>
     * @return The amount of free space in the inventory.
     */
    public static int getFreeSpace() {
        return INVENTORY_SPACE - getAll().length;
    }

    /**
     * Returns true if the inventory has no items in it, false otherwise.
     *
     * @return True if the inventory has no items in it, false otherwise.
     */
    public static boolean isEmpty() {
        return getAll().length == 0;
    }

    /**
     * Drops any items whose IDs match any of the specified IDs.
     * <p>
     * This method drops items as if the player was using mouse-keys to drop each item.
     * <p>
     * @param vertical True to drop items vertically, false to drop items horizontally.
     * @param ids      The IDs of the items to drop.
     * @return True if dropping was successful, false otherwise.
     */
    public static boolean dropQuick(boolean vertical, final int... ids) {
        if (!contains(ids)) {
            return false;
        }
        for (RSItem item : (vertical ? getAllVertical(find(ids)) : find(ids))) {
            dropQuick(item);
        }
        return Timing.waitCondition(new Condition() {
            @Override
            public boolean active() {
                return !contains(ids);
            }
        }, 1500);
    }

    /**
     * Drops any items whose names match any of the specified names.
     * <p>
     * This method drops items as if the player was using mouse-keys to drop each item.
     * <p>
     * @param vertical True to drop items vertically, false to drop items horizontally.
     * @param names    The names of the items to drop.
     * @return True if dropping was successful, false otherwise.
     */
    public static boolean dropQuick(boolean vertical, final String... names) {
        if (!contains(names)) {
            return false;
        }
        for (RSItem item : (vertical ? getAllVertical(find(names)) : find(names))) {
            dropQuick(item);
        }
        return Timing.waitCondition(new Condition() {
            @Override
            public boolean active() {
                return !contains(names);
            }
        }, 1500);
    }

    /**
     * Drops the specified item in the inventory.
     * <p>
     * This method drops items as if the player was using mouse-keys to drop each item.
     * <p>
     * @param item The item to drop.
     * @return True if dropping was successful, false otherwise.
     */
    public static boolean dropQuick(RSItem item) {
        if (item == null) {
            return false;
        }
        open();
        Rectangle itemArea = item.getArea();
        if (itemArea == null) {
            return false;
        }
        Client.sleep(General.random(50, 60));
        if (!itemArea.contains(Mouse.getPos())) {
            Mouse07.moveClick(Screen07.getRandomPoint(item.getArea()), 3, General.random(50, 60));
        } else {
            Mouse.click(3);
        }
        if (Timing.waitMenuOpen(500)) {
            Rectangle r = Menu07.getArea("Drop " + item.getDefinition().getName());
            if (r == null) {
                return false;
            }
            Point centre = new Point((int) r.getCenterX(), (int) r.getCenterY());
            centre.translate(0, -4);
            Mouse07.hopClick(centre, 1, General.random(50, 60));
            return true;
        }
        return false;
    }
}
