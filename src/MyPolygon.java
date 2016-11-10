import java.awt.*;
import java.util.LinkedList;

/**
 * Created by autoy on 2015/10/4.
 */
public class MyPolygon extends GraphElement{
    Polygon polygon;
    MyPolygon(LinkedList<Point> list, Color color) {
        int[] x = new int[list.size()];
        int[] y = new int[list.size()];
        this.pointList = new LinkedList<>();
        for (int i = 0; i < list.size(); i++) {
            x[i]=list.get(i).x;
            y[i]=list.get(i).y;
        }
        this.polygon=new Polygon(x,y,list.size());
        this.color = color;
    }
    @Override
    void paint(Graphics g)
    {
        g.setColor(this.color);
        g.fillPolygon(this.polygon);
    }
}
