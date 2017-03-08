package scripts.api.scriptapi.generic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import org.tribot.api.General;
import org.tribot.api.Screen;
import org.tribot.api.input.Mouse;
import org.tribot.api.util.Screenshots;

/**
 * The Screen07 class provides methods related to the game screen.
 * <p>
 * @author Nolan
 */
public class Screen07 {

    /**
     * Gets all of the points on the screen.
     * <p>
     * @return All of the points on the screen.
     */
    public static final Point[] getAllPoints() {
        List<Point> points = new ArrayList<>();
        Dimension dim = Screen.getDimension();
        for (int x = 0; x < dim.width; x++) {
            for (int y = 0; y < dim.height; y++) {
                points.add(new Point(x, y));
            }
        }
        return points.toArray(new Point[points.size()]);
    }

    /**
     * Gets all of the points contained within the specified shape.
     * <p>
     * @param shape The shape.
     * @return The points in the shape.
     */
    public static final Point[] getPoints(Shape shape) {
        if (shape == null) {
            return new Point[0];
        }
        List<Point> points = new ArrayList<>();
        for (Point p : getAllPoints()) {
            if (shape.contains(p)) {
                points.add(p);
            }
        }
        return points.toArray(new Point[points.size()]);
    }

    /**
     * Gets a random point inside the specified shape.
     * <p>
     * @param shape The shape.
     * @return A random point.
     */
    public static final Point getRandomPoint(Shape shape) {
        if (shape == null) {
            return new Point();
        }
        Point[] points = getPoints(shape);
        if (points.length  == 0) {
            return new Point();
        }
        return points[General.random(0, points.length - 1)];
    }

    /**
     * Gets the amount pixels in the specified shape that match the specified color.
     * <p>
     * @param color The color.
     * @param shape The shape.
     * @return The number of matching pixels.
     */
    public static final int getMatchingPixelCount(Color color, Shape shape) {
        int matchingPixelCount = 0;
        for (Point p : getPoints(shape)) {
            if (Screen.getColourAt(p).equals(color)) {
                matchingPixelCount++;
            }
        }
        return matchingPixelCount;
    }

    /**
     * Gets the an image of the screen in its current state.
     * <p>
     * @return An image of the screen.
     */
    public static final BufferedImage getGameImage() {
        return getGameImage(null);
    }

    /**
     * Gets the game image.
     * <p>
     * @param mouseImage The mouse paint. Provide null if you want the default (small white square) mouse.
     * @return A BufferedImage representing the screen.
     */
    public static final BufferedImage getGameImage(BufferedImage mouseImage) {
        return getGameImage(null, mouseImage, null);
    }

    /**
     * Gets the game image.
     * <p>
     * @param gameImage     The game image (null for current image).
     * @param mouseImage    The mouse image (null for default).
     * @param mousePosition The mouse position (null for default).
     * @return The game image.
     */
    public static final BufferedImage getGameImage(BufferedImage gameImage, BufferedImage mouseImage, Point mousePosition) {
        BufferedImage image1 = gameImage == null ? Screenshots.getScreenshotImage() : gameImage;
        BufferedImage image2 = new BufferedImage(image1.getWidth(), image1.getHeight(), image1.getType());
        Point mousePos = mousePosition == null ? Mouse.getPos() : mousePosition;
        Graphics2D g2 = image2.createGraphics();
        g2.drawImage(image1, 0, 0, null);
        if (mouseImage == null) {
            g2.drawRect(mousePos.x - 3, mousePos.y - 3, 6, 6);
        } else {
            g2.drawImage(mouseImage, 0, 0, null);
        }
        return image2;
    }
}
