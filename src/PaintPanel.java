import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

/**
 * Created by autoy on 2015/10/9.
 */
public class PaintPanel extends JPanel implements MouseListener {
    MainForm mainForm;
    Color colorSaved;
    LinkedList<Point> pointList;
    LinkedList<GraphElement> elements;
    PaintPanel(MainForm parent)
    {
        mainForm=parent;
        pointList = new LinkedList<>();
        elements = new LinkedList<>();
        addMouseListener(this);
        setBackground(Color.white);
        setForeground(Color.blue);
    }
    public void update(Graphics g,LinkedList<GraphElement> elements) {
        super.update(g);
        for(GraphElement element: elements)
        {
            element.paint(g);
        }
    }
    void drawDot(Point point, Color color) {
        Graphics g = this.getGraphics();
        g.setColor(color);
        g.fillOval(point.x - 4, point.y - 4, 8, 8);
    }

    void drawLine(Point begin, Point end, Color color) {
        Graphics g = this.getGraphics();
        g.setColor(color);
        g.drawLine(begin.x, begin.y, end.x, end.y);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (mainForm.button1.isSelected()) {

            if (pointList.size() != 0) {
                drawDot(e.getPoint(),colorSaved);
                GraphicAlgorithm.drawDDALine(this.getGraphics(),colorSaved, pointList.getFirst(), e.getPoint());
                pointList.push(e.getPoint());
                elements.add(new MyLine(pointList, colorSaved));
                update(getGraphics(), elements);
                pointList.clear();
            } else {
                pointList.push(e.getPoint());
                drawDot(e.getPoint(), colorSaved);
            }
        } else if (mainForm.button2.isSelected()) {
            if (pointList.size() != 0) {
                drawDot(e.getPoint(), colorSaved);
                GraphicAlgorithm.drawMidPointLine(this.getGraphics(), colorSaved,pointList.getFirst(), e.getPoint());
                pointList.push(e.getPoint());
                elements.add(new MyLine(pointList, colorSaved));
                pointList.clear();
                update(mainForm.paintPanel.getGraphics(), elements);
            } else {
                pointList.push(e.getPoint());
                drawDot(e.getPoint(),colorSaved);
            }
        } else if (mainForm.button3.isSelected()) {
            if (pointList.size() != 0) {

                drawDot(e.getPoint(), colorSaved);
                GraphicAlgorithm.drawBresenhamLine(this.getGraphics(), colorSaved, pointList.getFirst(), e.getPoint());
                pointList.push(e.getPoint());
                elements.add(new MyLine(pointList,colorSaved));
                pointList.clear();
                this.update(this.getGraphics(), elements);
            } else {
                pointList.push(e.getPoint());
                drawDot(e.getPoint(), colorSaved);
            }
        } else if (mainForm.button4.isSelected()) {
            if (pointList.size() != 0) {
                GraphicAlgorithm.drawMidPointCircle(getGraphics(), colorSaved, pointList.getFirst(), e.getPoint());
                pointList.push(e.getPoint());
                elements.add(new MyCircle(pointList, this.colorSaved));
                update(getGraphics(), elements);
                pointList.clear();
            } else {
                pointList.push(e.getPoint());
                drawDot(e.getPoint(), colorSaved);
            }
        } else if (mainForm.button5.isSelected()) {
            int mask = e.getModifiers();
            if ((mask & InputEvent.BUTTON1_MASK) != 0) {
                if (pointList.size() != 0) {
                    drawDot(e.getPoint(), colorSaved);
                    drawLine(pointList.getLast(), e.getPoint(), colorSaved);
                    pointList.addLast(e.getPoint());
                } else {
                    pointList.addLast(e.getPoint());
                    drawDot(e.getPoint(), colorSaved);
                }
            } else if ((mask & InputEvent.BUTTON3_MASK) != 0) {
                if (pointList.size() > 1) {
                    drawDot(e.getPoint(), colorSaved);
                    drawLine(pointList.getLast(), e.getPoint(), colorSaved);
                    pointList.addLast(e.getPoint());
                    elements.add(new MyPolygon(pointList, colorSaved));
                    drawLine(pointList.getLast(), pointList.getFirst(), colorSaved);
                    GraphicAlgorithm.fillPoly(getGraphics(), pointList, colorSaved);
                    pointList.clear();
                    update(getGraphics(), elements);
                }
            }
        } else if (mainForm.button6.isSelected()) {
            if (pointList.size() != 0) {
                drawDot(e.getPoint(), colorSaved);
                pointList.push(e.getPoint());
                LinkedList<Object> deleted=new LinkedList<>();
                for(GraphElement element: elements)
                {
                    if(element instanceof MyLine)
                    {
                        if(!GraphicAlgorithm.CohenSutherland((MyLine)element,pointList))
                        {
                            deleted.addLast(element);
                        }
                    }
                }
                for(Object o:deleted)
                {
                    elements.remove(o);
                }
                update(getGraphics(), elements);
                Graphics g=getGraphics();
                g.setColor(Color.MAGENTA);
                Point point1,point2;
                point1=pointList.getFirst();
                point2=pointList.getLast();
                g.drawLine(point1.x,point1.y,point1.x,point2.y);
                g.drawLine(point1.x,point1.y,point2.x,point1.y);
                g.drawLine(point1.x,point2.y,point2.x,point2.y);
                g.drawLine(point2.x,point1.y,point2.x,point2.y);
                pointList.clear();
            } else {
                pointList.push(e.getPoint());
                drawDot(e.getPoint(), colorSaved);
            }
        }else if(mainForm.button7.isSelected()){
            if (pointList.size() != 0) {
                drawDot(e.getPoint(), colorSaved);
                pointList.push(e.getPoint());
                LinkedList<Object> deleted=new LinkedList<>();
                for(GraphElement element: elements)
                {
                    if(element instanceof MyLine)
                    {
                        if(!GraphicAlgorithm.CohenSutherland((MyLine)element,pointList))
                        {
                            deleted.addLast(element);
                        }
                    }
                }
                for(Object o:deleted)
                {
                    elements.remove(o);
                }
                update(getGraphics(), elements);
                Graphics g=getGraphics();
                g.setColor(Color.MAGENTA);
                Point point1,point2;
                point1=pointList.getFirst();
                point2=pointList.getLast();
                g.drawLine(point1.x,point1.y,point1.x,point2.y);
                g.drawLine(point1.x,point1.y,point2.x,point1.y);
                g.drawLine(point1.x,point2.y,point2.x,point2.y);
                g.drawLine(point2.x,point1.y,point2.x,point2.y);
                pointList.clear();
            } else {
                pointList.push(e.getPoint());
                drawDot(e.getPoint(), colorSaved);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
