package rs.manhut;

import javax.imageio.ImageIO;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import rs.manhut.beans.PartyDAOI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Hashtable;

public class RegistrationFrame extends JFrame implements ActionListener {


    private BufferedImage avatar;

    private JPanel mainPanel;
    private JPanel gridPanel;
    private JTextField emailTextField;
    private JTextField lastNameTextField;
    private JTextField descriptionTextField;
    private JTextField firstNameTextField;
    private JPasswordField passwordField;
    private JButton avatarButton;
    private JButton registerButton;
    
    private PartyDAOI partyDAO;

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

        registerButton.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(registerButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        this.add(mainPanel);

        this.setVisible(true);
    }

    private void initGridPanel(){
        gridPanel = new JPanel(new GridLayout(6,2));
        gridPanel.add(new JLabel("Email:"));
        emailTextField = new JTextField();
        emailTextField.setPreferredSize(new Dimension(150, 40));
        gridPanel.add(emailTextField);
        gridPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        passwordField.setEchoChar('*');
        gridPanel.add(passwordField);
        gridPanel.add(new JLabel("First Name:"));
        firstNameTextField = new JTextField();
        gridPanel.add(firstNameTextField);
        gridPanel.add(new JLabel("Last Name:"));
        lastNameTextField = new JTextField();
        gridPanel.add(lastNameTextField);
        gridPanel.add(new JLabel("Description:"));
        descriptionTextField = new JTextField();
        gridPanel.add(descriptionTextField);
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
    
    private PartyDAOI getPartyDAO() throws NamingException {
		if (partyDAO == null) {
			InitialContext ctx = new InitialContext();
			String name = "ejb:/OOP2_SAMO_TOZLA//PartyDAO!" + PartyDAOI.class.getName();
			partyDAO = (PartyDAOI) ctx.lookup(name);
		}
		return partyDAO;
	}
    
    @Override
    public void actionPerformed(ActionEvent e) {
    	String email = emailTextField.getText(),
    			firstName = firstNameTextField.getText(),
    			lastName = lastNameTextField.getText(),
    			password = passwordField.getPassword().toString(),
    			desc = descriptionTextField.getText();
    	try {
    		this.getPartyDAO().register(email, password, firstName, lastName, desc, "");
    	} catch (NamingException ne) {
    		ne.printStackTrace();
    	}
    }
}
