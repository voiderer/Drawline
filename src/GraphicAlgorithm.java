import javafx.util.Pair;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by autoy on 2015/9/17.
 */
public class GraphicAlgorithm {
    private static void drawDot(Graphics graphics, int x, int y) {
        graphics.fillRect(x, y, 1, 1);
        try {
            Thread.sleep(2);
        } catch (InterruptedException inter) {
            inter.printStackTrace();
        }
    }

    private static void fillLine(Graphics graphics, int y, int x1, int x2) {
        x2++;
        for (int i = x1; i < x2; i++) {
            graphics.fillRect(i, y, 1, 1);
        }
    }

    public static void drawDDALine(Graphics graphics, Color color, Point begin, Point end) {
        graphics.setColor(color);
        float x, y, length, dx, dy;
        int i;
        if (Math.abs(end.x - begin.x) >= Math.abs(end.y - begin.y)) {
            length = Math.abs(end.x - begin.x);
        } else {
            length = Math.abs(end.y - begin.y);
        }
        dx = (end.x - begin.x) / length;
        dy = (end.y - begin.y) / length;
        x = begin.x + 0.5f;
        y = begin.y + 0.5f;
        i = 1;
        while (i <= (int) length) {
            drawDot(graphics, (int) x, (int) y);
            x = x + dx;
            y = y + dy;
            i++;
        }
    }

    public static void drawMidPointLine(Graphics graphics, Color color, Point begin, Point end) {
        graphics.setColor(color);
        int a, b, d1, d2, d, x, y, sign1, sign2, length, interchange, temp;
        a = begin.y - end.y;
        b = end.x - begin.x;
        sign1 = (int) Math.signum(end.x - begin.x);
        sign2 = (int) Math.signum(end.y - begin.y);

        if (Math.abs(b) >= Math.abs(a)) {
            length = Math.abs(b);
            interchange = 0;
        } else {
            length = Math.abs(a);
            interchange = 1;
            temp = a;
            a = b;
            b = temp;
            temp = sign1;
            sign1 = sign2;
            sign2 = temp;
        }
        d = 2 * a * sign1 + b * sign2;
        d1 = 2 * a * sign1;
        d2 = 2 * (a * sign1 + b * sign2);
        x = begin.x;
        y = begin.y;
        drawDot(graphics, x, y);
        for (int i = 0; i < length; i++) {
            if (sign1 * sign2 * d < 0) {
                if (interchange == 1) {
                    y = y + sign1;
                    d = d + d1;
                } else {
                    x = x + sign1;
                    y = y + sign2;
                    d = d + d2;
                }
            } else {
                if (interchange == 1) {
                    x = x + sign2;
                    y = y + sign1;
                    d = d + d2;
                } else {
                    x = x + sign1;
                    d = d + d1;
                }

            }
            drawDot(graphics, x, y);
        }
    }

    public static void drawBresenhamLine(Graphics graphics, Color color, Point begin, Point end) {
        graphics.setColor(color);
        float x, y, dx, dy, edger, temper;
        int s1, s2, interchange;
        x = begin.x;
        y = begin.y;
        dx = Math.abs(end.x - begin.x);
        dy = Math.abs(end.y - begin.y);
        s1 = (int) Math.signum((end.x - begin.x));
        s2 = (int) Math.signum(end.y - begin.y);
        if (dy > dx) {
            temper = dx;
            dx = dy;
            dy = temper;
            interchange = 1;
        } else interchange = 0;
        edger = 2 * dy - dx;
        for (int i = 1; i <= dx; i++) {
            drawDot(graphics, (int) x, (int) y);
            if (edger > 0) {
                if (interchange == 1) {
                    x = x + s1;
                } else {
                    y = y + s2;
                }
                edger = edger - 2 * dx;
            }
            if (interchange == 1) {
                y = y + s2;
            } else {
                x = x + s1;
            }
            edger = edger + 2 * dy;
        }
    }

    private static void drawCirclePoint(Graphics graphics, Point center, int x, int y) {
        graphics.fillOval(x + center.x, y + center.y, 2, 2);
        graphics.fillOval(-x + center.x, y + center.y, 2, 2);
        graphics.fillOval(x + center.x, -y + center.y, 2, 2);
        graphics.fillOval(-x + center.x, -y + center.y, 2, 2);
        graphics.fillOval(y + center.x, x + center.y, 2, 2);
        graphics.fillOval(-y + center.x, x + center.y, 2, 2);
        graphics.fillOval(y + center.x, -x + center.y, 2, 2);
        graphics.fillOval(-y + center.x, -x + center.y, 2, 2);
    }

    public static void drawMidPointCircle(Graphics graphics, Color color, Point center, Point end) {
        graphics.setColor(color);
        int x, y, r = 50;
        float d;
        x = 0;
        y = (int) Math.sqrt((center.y - end.y) * (center.y - end.y) + (center.x - end.x) * (center.x - end.x));
        d = 1.25f - r;
        drawCirclePoint(graphics, center, x, y);
        while (x <= y) {
            if (d < 0) d += 2 * x + 3;
            else {
                d += 2 * (x - y) + 5;
                y--;
            }
            x++;
            drawCirclePoint(graphics, center, x, y);
            try {
                Thread.sleep(10);
            } catch (InterruptedException inter) {
                inter.printStackTrace();
            }
        }
    }

    public static void fillPoly(Graphics graphics, LinkedList<Point> pointList, Color color) {
        int yMin = Integer.MAX_VALUE, yMax = Integer.MIN_VALUE;
        graphics.setColor(color);
        for (Point p : pointList) {
            if (p.y < yMin) {
                yMin = p.y;
            }
            if (p.y > yMax) {
                yMax = p.y;
            }
        }
        NETSet netSetList = new NETSet();
        AETSet aetSetList = new AETSet();
        pointList.addLast(pointList.getFirst());
        Iterator iterator = pointList.iterator();
        Point tempPoint2, tempPoint1 = (Point) iterator.next();
        while (iterator.hasNext()) {
            tempPoint2 = (Point) iterator.next();
            netSetList.addNewLine(tempPoint1, tempPoint2);
            tempPoint1 = tempPoint2;
        }
        yMax++;
        LineStructure struct, lastStruct;
        LinkedList<Object> deleteList = new LinkedList<>();
        LinkedList<Point> drawPointList = new LinkedList<>();
        LinkedList<Pair<Float, LinkedList<LineStructure>>> structureList = new LinkedList<>();
        for (int i = yMin; i < yMax; i++) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException inter) {
                inter.printStackTrace();
            }
            drawPointList.clear();
            structureList.clear();
            drawPointList.clear();
            if (netSetList.list.size() > 0 && netSetList.list.getFirst().line == i) {
                for (LineStructure structure : netSetList.list.getFirst().lineList) {
                    structure.isNewlyAdded = true;
                    aetSetList.orderInsert(structure);
                }
                netSetList.list.removeFirst();
            }
            aetSetList.checkOrder();
            iterator = aetSetList.activeList.iterator();

            while (iterator.hasNext()) {
                struct = (LineStructure) iterator.next();
                if (structureList.size() > 0 && Math.abs(structureList.getLast().getKey() - struct.x) < 10e-2) {
                    struct.isNewlyAdded = true;
                    structureList.getLast().getValue().addLast(struct);
                } else {
                    struct.isNewlyAdded = true;
                    structureList.addLast(new Pair<>(struct.x, new LinkedList<>()));
                    structureList.getLast().getValue().addLast(struct);
                }
            }
            for (Pair<Float, LinkedList<LineStructure>> pair : structureList) {
                switch (pair.getValue().size()) {
                    case 1:
                        struct = pair.getValue().getFirst();
                        drawPointList.addLast(new Point((int) struct.x, i));
                        break;
                    case 2:
                        struct = pair.getValue().getFirst();
                        lastStruct = pair.getValue().getLast();
                        if (struct.yMax == i && lastStruct.yMax > i || struct.yMax > i && lastStruct.yMax == i) {
                            drawPointList.addLast(new Point((int) struct.x, i));
                        }
                        break;
                    default:
                        System.out.print("!!!!!!!!!!!!");
                }
            }
            iterator = drawPointList.iterator();
            while (iterator.hasNext()) {
                tempPoint1 = (Point) iterator.next();
                if (iterator.hasNext()) {
                    tempPoint2 = (Point) iterator.next();
                    fillLine(graphics, i, tempPoint1.x, tempPoint2.x);
                }
            }

            iterator = aetSetList.activeList.iterator();
            while (iterator.hasNext()) {
                struct = (LineStructure) iterator.next();
                if (i == struct.yMax) {
                    deleteList.addLast(struct);
                } else {
                    struct.x += struct.dx;
                }
            }
            for (Object o : deleteList) {
                aetSetList.activeList.remove(o);
            }
            deleteList.clear();
        }


    }

    private static void setMatrixFLag(boolean matrix[][], Point begin, Point end) {
        float x, y, dx, dy, edger, temper;
        int s1, s2, interchange;
        x = begin.x;
        y = begin.y;
        dx = Math.abs(end.x - begin.x);
        dy = Math.abs(end.y - begin.y);
        s1 = (int) Math.signum((end.x - begin.x));
        s2 = (int) Math.signum(end.y - begin.y);
        if (dy > dx) {
            temper = dx;
            dx = dy;
            dy = temper;
            interchange = 1;
        } else interchange = 0;
        edger = 2 * dy - dx;
        for (int i = 1; i <= dx; i++) {
            matrix[(int) x][(int) y] = true;
            if (edger > 0) {
                if (interchange == 1) {
                    x = x + s1;
                } else {
                    y = y + s2;
                }
                edger = edger - 2 * dx;
            }
            if (interchange == 1) {
                y = y + s2;
            } else {
                x = x + s1;
            }
            edger = edger + 2 * dy;
        }
    }

    public static void fillEdgeMark(Graphics graphics, LinkedList<Point> pointList, Color color) {
        int yMin = Integer.MAX_VALUE, yMax = Integer.MIN_VALUE, xMin = Integer.MAX_VALUE, xMax = Integer.MIN_VALUE;
        graphics.setColor(color);
        for (Point p : pointList) {
            if (p.y < yMin) {
                yMin = p.y;
            }
            if (p.y > yMax) {
                yMax = p.y;
            }
            if (p.x < xMin) {
                xMin = p.x;
            }
            if (p.x > xMax) {
                xMax = p.x;
            }
        }
        int x = xMax - xMin + 1, y = yMax - yMin + 1;
        boolean matrix[][];
        matrix = new boolean[y][x];

        for (Point point : pointList) {
            point.x -= xMin;
            point.y -= yMin;
        }
        pointList.addLast(pointList.getFirst());
        Iterator iterator = pointList.iterator();
        Point tempPoint2, tempPoint1 = (Point) iterator.next();
        while (iterator.hasNext()) {
            tempPoint2 = (Point) iterator.next();
            setMatrixFLag(matrix, tempPoint1, tempPoint2);
            tempPoint1 = tempPoint2;
        }
        boolean flag;
        for (int j = 0; j < y; j++) {
            flag = false;
            for (int i = 0; i < x; i++) {
                if (matrix[i][j]) {
                    flag = !flag;
                }
                if (flag) {
                    drawDot(graphics, i + xMin, j + yMin);
                }
            }
        }
        pointList.clear();
    }

    static byte LEFT = 1;
    static byte RIGHT = 2;
    static byte BOTTOM = 4;
    static byte TOP = 8;

    private static byte encode(int x, int y, int XL, int XR, int YB, int YT) {
        byte temp = 0;
        if (x < XL)
            temp |= LEFT;
        if (x > XR)
            temp |= RIGHT;
        if (y < YB)
            temp |= BOTTOM;
        if (y > YT)
            temp |= TOP;
        return temp;
    }

    public static boolean CohenSutherland(MyLine line, LinkedList<Point> area) {
        int XL, XR, YB, YT, temp, x1, y1, x2, y2, x = 0, y = 0;
        XL = area.getFirst().x;
        XR = area.getLast().x;
        YB = area.getFirst().y;
        YT = area.getLast().y;
        x1 = line.pointList.getFirst().x;
        y1 = line.pointList.getFirst().y;
        x2 = line.pointList.getLast().x;
        y2 = line.pointList.getLast().y;
        if (XL > XR) {
            temp = XL;
            XL = XR;
            XR = temp;
        }
        if (YB > YT) {
            temp = YB;
            YB = YT;
            YT = temp;
        }
        byte code1, code2, code;
        code1 = encode(x1, y1, XL, XR, YB, YT);
        code2 = encode(x2, y2, XL, XR, YB, YT);
        while (code1 != 0 || code2 != 0) {
            if ((code1 & code2) != 0) {
                return false;
            }
            if (code1 != 0) {
                code = code1;
            } else {
                code = code2;
            }
            if ((LEFT & code) != 0) {
                x = XL;
                y = y1 + (y2 - y1) * (XL - x1) / (x2 - x1);
            } else if ((RIGHT & code) != 0) {
                x = XR;
                y = y1 + (y2 - y1) * (XR - x1) / (x2 - x1);
            } else if ((TOP & code) != 0) {
                y = YT;
                x = x1 + (x2 - x1) * (YT - y1) / (y2 - y1);
            } else if ((BOTTOM & code) != 0) {
                y = YB;
                x = x1 + (x2 - x1) * (YB - y1) / (y2 - y1);
            }
            if (code == code1) {
                x1 = x;
                y1 = y;
                code1 = encode(x1, y1, XL, XR, YB, YT);
            } else {
                x2 = x;
                y2 = y;
                code2 = encode(x2, y2, XL, XR, YB, YT);
            }
        }
        line.pointList.clear();
        line.pointList.add(new Point(x1, y1));
        line.pointList.add(new Point(x2, y2));
        return true;
    }

    private static boolean Clip(int p, int q, Float u1, Float u2) {
        float r;
        if (p < 0) {
            r = ((float) q) / ((float) p);
            if (r > u2) {
                return false;
            }
            if (r > u1) {
                u1 = r;
            }
        } else if (p > 0) {
            r = ((float) q) / ((float) p);
            if (r < u1) {
                return false;
            }
            if (r < u2) {
                u2 = r;
            }
        } else {
            return q >= 0;
        }
        return true;
    }

    public static boolean LB_LineClip(MyLine line, LinkedList<Point> area) {
        int XL, XR, YB, YT, temp, x1, y1, x2, y2, dx, dy;
        Float u1, u2;
        XL = area.getFirst().x;
        XR = area.getLast().x;
        YB = area.getFirst().y;
        YT = area.getLast().y;
        x1 = line.pointList.getFirst().x;
        y1 = line.pointList.getFirst().y;
        x2 = line.pointList.getLast().x;
        y2 = line.pointList.getLast().y;
        if (XL > XR) {
            temp = XL;
            XL = XR;
            XR = temp;
        }
        if (YB > YT) {
            temp = YB;
            YB = YT;
            YT = temp;
        }
        u1 = 0f;
        u2 = 1f;
        dx = x2 - x1;
        dy = y2 - y1;
        if (Clip(-dx, x1 - XL, u1, u2))
            if (Clip(dx, XR - x1, u1, u2))
                if (Clip(-dy, y1 - YB, u1, u2))
                    if (Clip(dx, YT - y1, u1, u2)) {
                        x1 = x1 + (int) (u1 * dx);
                        y1 = y1 + (int) (u1 * dy);
                        x2 = x1 + (int) (u2 * dx);
                        y2 = y1 + (int) (u2 * dy);
                        line.pointList.clear();
                        line.pointList.add(new Point(x1, y1));
                        line.pointList.add(new Point(x2, y2));
                        return true;
                    }
        return false;
    }
}
class NewLineStructure {
    int line;
    LinkedList<LineStructure> lineList;

    NewLineStructure(int line, LineStructure structure) {
        this.line=line;
        lineList=new LinkedList<>();
        lineList.addLast(structure);
    }
    void insert(LineStructure structure)
    {
        this.lineList.addLast(structure);
    }
}
class LineStructure {
    float x;
    float dx;
    int yMax;
    boolean isNewlyAdded;
    LineStructure(int x, float dx, int yMax) {
        this.x = x;
        this.dx = dx;
        this.yMax = yMax;
    }
}
class AETSet {
    LinkedList<LineStructure> activeList;

    AETSet() {
        activeList = new LinkedList<>();
    }

    void orderInsert(LineStructure structure) {
        int i;
        if (activeList.size() == 0) {
            activeList.add(0, structure);
            return;
        }
        for (i = 0; i < activeList.size(); i++) {
            if (activeList.get(i).x == structure.x) {
                if (structure.dx < activeList.get(i).dx) {
                    activeList.add(i, structure);
                } else {
                    activeList.add(i + 1, structure);
                }
                break;
            } else if (activeList.get(i).x > structure.x) {
                activeList.add(i, structure);
                break;
            }
        }
        if (i == activeList.size()) {
            activeList.addLast(structure);
        }
    }

    void checkOrder() {
        LineStructure object;
        if (activeList.size() < 2) {
            return;
        }
        for (int j = 1; j < this.activeList.size(); j++) {
            object = activeList.get(j - 1);
            if (object.x > activeList.get(j).x) {
                activeList.remove(object);
                this.orderInsert(object);
            }
        }
    }

}
class NETSet {
    LinkedList<NewLineStructure> list;

    NETSet() {
        list = new LinkedList<>();
    }

    void addNewLine(Point p1, Point p2) {
        Point temp;
        if (p1.y != p2.y) {
            if (p1.y > p2.y) {
                temp = p1;
                p1 = p2;
                p2 = temp;
            }
            orderInsert(p1.y, new LineStructure(p1.x, ((float) (p2.x - p1.x)) / ((float) (p2.y - p1.y)), p2.y));
        }
    }

    private void orderInsert(int y, LineStructure structure) {
        int i;
        if (list.size() == 0) {
            list.add(0, new NewLineStructure(y, structure));
            return;
        }
        for (i = 0; i < list.size(); i++) {
            if (list.get(i).line == y) {
                list.get(i).insert(structure);
                break;
            } else if (list.get(i).line > y) {
                list.add(i, new NewLineStructure(y, structure));
                break;
            }
        }
        if (i == list.size()) {
            list.addLast(new NewLineStructure(y, structure));
        }
    }
}