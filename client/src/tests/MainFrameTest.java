package tests;

import rs.manhut.MainFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class MainFrameTest {
    public static void main(String[] args){
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        Image image = null;
        try{
            image = ImageIO.read(chooser.getSelectedFile());
            image = image.getScaledInstance(200,200,0);
        }catch (Exception e){
            e.printStackTrace();
        }
        MainFrame frame = new MainFrame("admin", "Admin Adminovic", image);
    }
}
