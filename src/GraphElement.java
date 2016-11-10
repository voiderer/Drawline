import java.awt.*;
import java.util.LinkedList;

/**
 * Created by autoy on 2015/9/26.
 */
abstract public class GraphElement {
    LinkedList<Point> pointList;
    Color color;
    abstract void paint(Graphics g);
}
