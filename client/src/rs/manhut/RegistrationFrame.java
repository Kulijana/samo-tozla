package rs.manhut;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class RegistrationFrame extends JFrame{


    private BufferedImage avatar;

    private JPanel mainPanel;
    private JPanel gridPanel;
    private JTextField mailTextField;
    private JTextField nameSurnameTextField;
    private JTextField descriptionTextField;
    private JTextField usernameTextField;
    private JButton avatarButton;
    private JButton registerButton;

    public RegistrationFrame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600,300);
        this.setTitle("Registration");

        mainPanel = new JPanel();
        BorderLayout layout = new BorderLayout();
        layout.setHgap(20);
        mainPanel.setLayout(layout);

        mainPanel.add(new JLabel("Please fill out all the fields and press Register button to continue."), BorderLayout.NORTH);
        initGridPanel();
        mainPanel.add(gridPanel,BorderLayout.CENTER);

        registerButton = new JButton("Register");

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO add the data to DB, close this frame, open the next frame, and send the needed information to it(name,avatar, etc.)
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(registerButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        this.add(mainPanel);

        this.setVisible(true);
    }

    private void initGridPanel(){
        gridPanel = new JPanel(new GridLayout(5,2));
        gridPanel.add(new JLabel("Email:"));
        mailTextField = new JTextField();
        mailTextField.setPreferredSize(new Dimension(150, 40));
        gridPanel.add(mailTextField);
        gridPanel.add(new JLabel("Name and surname:"));
        nameSurnameTextField = new JTextField();
        gridPanel.add(nameSurnameTextField);
        gridPanel.add(new JLabel("Description:"));
        descriptionTextField = new JTextField();
        gridPanel.add(descriptionTextField);
        gridPanel.add(new JLabel("Username:"));
        usernameTextField = new JTextField();
        gridPanel.add(usernameTextField);

        avatarButton = new JButton();
        avatarButton.setText("Select your avatar");
        avatarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Pictures, jpg & png", "jpg", "png");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(null);
                if(returnVal == JFileChooser.APPROVE_OPTION){
                    try {
                        avatar = ImageIO.read(chooser.getSelectedFile());
                        JLabel picLabel = new JLabel(new ImageIcon(avatar));
                        gridPanel.add(picLabel);
                    }catch (IOException ex){
                        System.out.println("Error in converting the File to Image");
                    }
                    catch(Exception ex){
                        System.out.println("Something went wrong with the avatar.");
                    }
                }
            }
        });

        gridPanel.add(avatarButton);

    }

}
