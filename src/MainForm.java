import javax.swing.*;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Random;


/**
 * Created by autoy on 2015/9/16.
 */
public class MainForm extends JFrame implements  ActionListener {
    JRadioButton button1, button2, button3, button4, button5, button6, button7;
    JButton buttonClear, buttonColor;
    ButtonGroup group;

    PaintPanel paintPanel;
    public static void main(String agra[]) {
        new MainForm();
    }

    MainForm() {
        super("ComputerGraphic");
        paintPanel=new PaintPanel(this);
        JPanel optionPanel = new JPanel();
        optionPanel.setLayout(new FlowLayout());
        group = new ButtonGroup();

        button1 = new JRadioButton("DDA");
        optionPanel.add(button1);
        button1.addActionListener(this);
        group.add(button1);
        button1.setSelected(true);

        button2 = new JRadioButton("MidPoint");
        optionPanel.add(button2);
        button2.addActionListener(this);
        group.add(button2);

        button3 = new JRadioButton("Bresenham");
        optionPanel.add(button3);
        button3.addActionListener(this);
        group.add(button3);

        button4 = new JRadioButton("ARC");
        optionPanel.add(button4);
        button4.addActionListener(this);
        group.add(button4);

        button5 = new JRadioButton("polygon");
        optionPanel.add(button5);
        button5.addActionListener(this);
        group.add(button5);

        button6 = new JRadioButton("Barskey");
        optionPanel.add(button6);
        button6.addActionListener(this);
        group.add(button6);

        button7 = new JRadioButton("Cohen-Sutherland");
        optionPanel.add(button7);
        button7.addActionListener(this);
        group.add(button7);

        buttonClear = new JButton("Clear");
        buttonClear.addActionListener(this);
        optionPanel.add(buttonClear);

        buttonColor = new JButton("Choose Color");
        buttonColor.addActionListener(this);
        optionPanel.add(buttonColor);


        this.getContentPane().add(optionPanel, BorderLayout.NORTH);
        this.getContentPane().add(paintPanel, BorderLayout.CENTER);




        this.setExtendedState(MAXIMIZED_BOTH);
        this.setSize(800, 800);
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == button1 || o == button2 || o == button3 || o == button4 || o == button5) {
            paintPanel.pointList.clear();
        } else if (o == buttonClear) {
            Rectangle rectangle = this.getBounds();
            paintPanel.pointList.clear();
            paintPanel.elements.clear();
            this.repaint(0, 0, 0, rectangle.width, rectangle.height);
        } else if (o == buttonColor) {
            paintPanel.colorSaved = JColorChooser.showDialog(this,
                    "Choose Background Color", paintPanel.colorSaved);
        }
    }
}
