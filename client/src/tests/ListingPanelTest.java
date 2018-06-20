package tests;

import rs.manhut.MinimizedListingPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class ListingPanelTest {
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setSize(640,480);
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        Image image = null;
        try{
            image = ImageIO.read(chooser.getSelectedFile());
            image = image.getScaledInstance(384,216,0);
        }catch(Exception e){
            e.printStackTrace();
        }
//        MinimizedListingPanel bid = new MinimizedListingPanel(image, 500.00);
//        frame.add(bid);

        frame.setVisible(true);
    }
}
