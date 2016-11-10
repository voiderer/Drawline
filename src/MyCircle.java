import java.awt.*;
import java.util.LinkedList;

/**
 * Created by autoy on 2015/10/4.
 */
public class MyCircle extends GraphElement {
    Point center;
     int r;
    Color color;
    MyCircle(LinkedList<Point> list,Color color)
    {
        this.color=color;
        center=list.getLast();
        Point temp=list.getFirst();
        r=(int) Math.sqrt((center.y - temp.y) * (center.y - temp.y) + (center.x - temp.x) * (center.x - temp.x));
    }
    @Override
    void paint(Graphics g) {
        g.setColor(this.color);
        g.drawOval(center.x-r,center.y-r,2*r,2*r);
    }
}
