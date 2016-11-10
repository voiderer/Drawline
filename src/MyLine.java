import java.awt.*;
import java.util.LinkedList;

/**
 * Created by autoy on 2015/9/26.
 */
public class MyLine extends GraphElement {
    MyLine(LinkedList<Point> list, Color color)
    {
        this.pointList=new LinkedList<>();
        for(Point point: list)
        {
            this.pointList.add(new Point(point));
        }
        this.color=color;
    }
    @Override
    void paint(Graphics g) {
        if(this.pointList.size()!=2)
            return;
        g.setColor(this.color);
        g.drawLine(pointList.getFirst().x,pointList.getFirst().y,pointList.getLast().x,pointList.getLast().y);
    }
}
