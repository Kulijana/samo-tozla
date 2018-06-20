package tests;

import rs.manhut.ProfileFrame;
import rs.manhut.entities.Listing;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class ProfileFrameTest {
    public static void main(String[] args){
//        JFileChooser chooser = new JFileChooser();
//        chooser.showOpenDialog(null);
        Image image = null;
        try{
            image = ImageIO.read(new File("./Testing.jpg"));
            image = image.getScaledInstance(200,200,0);
        }catch (Exception e){
            e.printStackTrace();
        }
        ProfileFrame frame = new ProfileFrame(null, null);
    }
}
