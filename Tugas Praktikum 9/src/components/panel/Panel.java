package components.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;



public class Panel extends javax.swing.JPanel {

    public Panel(int h){
        setOpaque(false); // Ensure panel is opaque so it is visible
        // setBackground(new Color(0, 0, 0, 0)); // Set background color with transparency
        setMaximumSize(new Dimension(420,h)); // Prevent BoxLayout from stretching the panel


    }


  

}
