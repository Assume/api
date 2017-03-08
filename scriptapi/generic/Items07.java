package scripts.api.scriptapi.generic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSItemDefinition;
import org.tribot.util.Util;

/**
 * The Items07 class is a utility class that provides methods related to RSItem's.
 *
 * @author Nolan
 */
public class Items07 {

    /**
     * Gets all of the item definitions that exist.
     *
     * @return An array of all of the item definitions.
     */
    public static final RSItemDefinition[] getAllDefinitions() {
        List<RSItemDefinition> definitions = new ArrayList<>();
        for (int i = 0; i < 25000; i++) {
            RSItemDefinition def = RSItemDefinition.get(i);
            if (def != null) {
                definitions.add(def);
            }
        }
        return definitions.toArray(new RSItemDefinition[definitions.size()]);
    }
    
    public static String[] getActions(RSItem item) {
        if (item != null) {
            RSItemDefinition def = item.getDefinition();
            if (def != null) {
                String[] actions = def.getActions();
                if (actions != null) {
                    return actions;
                }
            }
        }
        return new String[0];
    }
    
    public static String getName(int id) {
        RSItemDefinition def = RSItemDefinition.get(id);
        if (def != null) {
            return def.getName();
        }
        return null;
    }

    /**
     * Generates a new empty item array with a length of 0.
     *
     * @return A new empty item array.
     */
    public static final RSItem[] empty() {
        return new RSItem[0];
    }

  
    /**
     * Returns the unnoted id of the specified RSItem, regardless of its state. Returns -1 if the item or item definition is null.
     *
     * @param item The RSItem.
     * @return The unnoted id of the specified RSItem, regardless of its state. Returns -1 if the item or item definition is null.
     */
    public static int getIdUnnoted(RSItem item) {
        if (item != null) {
            RSItemDefinition definition = item.getDefinition();
            if (definition != null) {
                return definition.isNoted() ? item.getID() - 1 : item.getID();
            }
        }
        return -1;
    }

    /**
     * Returns the noted id of the specified RSItem, regardless of its state. Returns -1 if the item or item definition is null.
     *
     * @param item The RSItem.
     * @return The noted id of the specified RSItem, regardless of its state. Returns -1 if the item or item definition is null.
     */
    public static int getIdNoted(RSItem item) {
        if (item != null) {
            RSItemDefinition definition = item.getDefinition();
            if (definition != null && !definition.isStackable()) {
                return definition.isNoted() ? item.getID() : item.getID() + 1;
            }
        }
        return -1;
    }

    /**
     * Returns the current id of the specified RSItem.
     *
     * @param item The RSItem.
     * @return The current id of the specified RSItem.
     */
    public static int getId(RSItem item) {
        return item.getID();
    }

    /**
     * Checks to see if the specified RSItem matches any of the specified ids.
     *
     * @param item              The item being checked.
     * @param checkCurrentState True if the id must match the current state id of the item (noted/unnoted), false if the id can match any state id of the item.
     * @param ids               The ids being checked.
     * @return True if the specified RSItem matches any of the specified ids, false otherwise.
     */
    public static boolean matches(RSItem item, boolean checkCurrentState, int... ids) {
        if (item != null && ids != null) {
            for (int id : ids) {
                if (getId(item) != -1 && (checkCurrentState ? getId(item) == id : (getIdNoted(item) == id || getIdUnnoted(item) == id))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks to see if the specified RSItem matches any of the specified names.
     *
     * The name matching is NOT case sensitive.
     *
     * @param item  The item being checked.
     * @param names The names being checked.
     * @return True if the specified RSItem matches any of the specified names, false otherwise.
     */
    public static boolean matches(RSItem item, String... names) {
        if (item != null && names != null) {
            RSItemDefinition definition = item.getDefinition();
            if (definition != null) {
                String itemName = definition.getName();
                if (itemName != null) {
                    for (String name : names) {
                        if (name != null && itemName.equalsIgnoreCase(name)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
