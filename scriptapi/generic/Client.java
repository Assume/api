package scripts.api.scriptapi.generic;

import org.tribot.api.General;
/**
 * The Client class keeps track of information about the client and contains useful methods related to the client.
 * <p>
 * @author Nolan
 */
public class Client {

   

    /**
     * Puts the current thread to sleep for the specified amount of milliseconds.
     * <p>
     * @param milliseconds The amount of milliseconds.
     */
    public static final void sleep(long milliseconds) {
        General.sleep(milliseconds);
    }

    /**
     * Puts the current thread to sleep for a random amount of milliseconds between the specified minimum and maximum (inclusive).
     * <p>
     * @param min The minimum amount of time.
     * @param max The maximum amount of time.
     */
    public static final void sleep(int min, int max) {
        General.sleep(min, max);
    }

    /**
     * Prints the specified object to the client debug.
     * <p>
     * @param object The object to print.
     */
    public static final void println(Object object) {
        General.println(object);
    }

  

  
}
