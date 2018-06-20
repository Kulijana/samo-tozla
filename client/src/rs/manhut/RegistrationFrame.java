package rs.manhut;

import javax.imageio.ImageIO;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import rs.manhut.beans.ListingDAOI;
import rs.manhut.beans.PartyDAOI;
import rs.manhut.entities.Listing;
import rs.manhut.entities.Party;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Properties;
import java.util.List;

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
    private ListingDAOI listingDAO;
    
    private InitialContext ctx;

    public RegistrationFrame(InitialContext ctx) {
    	this.ctx = ctx;
    	
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
        gridPanel = new JPanel(new GridBagLayout());
        
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(10, 0, 0, 10);
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.3;
        c.weighty = 1;
        gridPanel.add(new JLabel("Email:"), c);
        
        c.gridy = 1;
        gridPanel.add(new JLabel("Password:"), c);
        
        c.gridy = 3;
        gridPanel.add(new JLabel("First Name:"), c);
        
        c.gridy = 4;
        gridPanel.add(new JLabel("Last Name:"), c);
        
        c.gridy = 5;
        gridPanel.add(new JLabel("Description:"), c);
        
        
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
			String name = "ejb:/samo-tozla//PartyDAO!" + PartyDAOI.class.getName();
			partyDAO = (PartyDAOI) ctx.lookup(name);
		}
		return partyDAO;
	}
    
    private ListingDAOI getListingDAO() throws NamingException {
    	if(listingDAO == null) {
			String name = "ejb:/samo-tozla//ListingDAO!" + ListingDAOI.class.getName();
			listingDAO = (ListingDAOI) ctx.lookup(name);
    	}
    	return listingDAO;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
    	String email = emailTextField.getText(),
    			firstName = firstNameTextField.getText(),
    			lastName = lastNameTextField.getText(),
    			password = String.copyValueOf(passwordField.getPassword()),
    			desc = descriptionTextField.getText();
    	try {
    		Party p = this.getPartyDAO().register(email, password, firstName, lastName, desc, "NotBlank");
			this.showSuccessDialog();
    	} catch (NamingException ne) {
    		ne.printStackTrace();
    	} catch (IllegalArgumentException iae) {
    		JOptionPane.showMessageDialog(this, iae.getMessage(), "Could not register", JOptionPane.ERROR_MESSAGE);
    	}
    }
    
    public void showSuccessDialog() {
    	SuccessDialog sd = new SuccessDialog(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LoginFrame lf = new LoginFrame(RegistrationFrame.this.ctx);
		    	RegistrationFrame.this.setVisible(false);
		    	RegistrationFrame.this.dispose();
			}
		});
    	
    	sd.setLocationRelativeTo(this);
    	sd.setVisible(true);
    }
    
    private class SuccessDialog extends JDialog implements ActionListener {
    	
    	private ActionListener listener;
    	private JButton button;
    	
    	public SuccessDialog(ActionListener al) {
    		this.listener = al;
            setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

            setLayout(new BorderLayout());

            JTextArea label = new JTextArea("You have successfully registered.\nYou can now log into the application.");
            label.setEditable(false);
            label.setBackground(new Color(0, 0, 0, 0));
            label.setBorder(new EmptyBorder(20, 20, 20, 20));
            add(label, BorderLayout.CENTER);
            
            button = new JButton("Go to login");
            button.addActionListener(this);
            add(button, BorderLayout.SOUTH);
            pack();
            setVisible(true);
    	}
    	
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		if(this.listener != null)
    			listener.actionPerformed(e);
    		
    		this.dispose();
    	}
    }

	
}
