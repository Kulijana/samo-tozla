package rs.manhut;

import javax.imageio.ImageIO;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import rs.manhut.beans.PartyDAOI;
import rs.manhut.entities.Party;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class RegistrationFrame extends JFrame implements ActionListener {


    private BufferedImage avatar;

    private JPanel mainPanel;
    private JPanel gridPanel;
    private JTextField emailTextField;
    private JTextField lastNameTextField;
    private JTextArea descriptionTextArea;
    private JTextField firstNameTextField;
    private JPasswordField passwordField;
    private JButton avatarButton;
    private JButton registerButton;
    
    private PartyDAOI partyDAO;
    
    private InitialContext ctx;

    public RegistrationFrame(InitialContext ctx) {
    	this.ctx = ctx;
    	
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700,400);
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

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginFrame loginFrame = new LoginFrame(ctx);
                dispose();
            }
        });
        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        this.add(mainPanel);

        this.setVisible(true);
    }

    private void initGridPanel(){
        gridPanel = new JPanel(new GridBagLayout());


        GridBagConstraints cs = new GridBagConstraints();
        cs.insets = new Insets(0, 10, 0, 20);
        cs.fill = GridBagConstraints.HORIZONTAL;

        Border border = BorderFactory.createLineBorder(Color.BLACK);

        cs.insets = new Insets(10,0,10,0);
        cs.gridx = 1;
        cs.gridy = 3;
        cs.gridwidth = 1;
        gridPanel.add(new JLabel("First name: "), cs);

        firstNameTextField = new JTextField(20);
        firstNameTextField.setBorder(BorderFactory.createCompoundBorder(border,BorderFactory.createEmptyBorder(3, 3, 3, 3)));
        cs.insets = new Insets(10,0,10,15);
        cs.gridx = 2;
        cs.gridy = 3;
        cs.gridwidth = 2;
        gridPanel.add(firstNameTextField, cs);


        cs.insets = new Insets(10,0,10,0);
        cs.gridx = 1;
        cs.gridy = 5;
        cs.gridwidth = 1;
        gridPanel.add(new JLabel("Last name: "), cs);

        lastNameTextField = new JTextField(20);
        lastNameTextField.setBorder(BorderFactory.createCompoundBorder(border,BorderFactory.createEmptyBorder(3, 3, 3, 3)));
        cs.insets = new Insets(10,0,10,15);
        cs.gridx = 2;
        cs.gridy = 5;
        cs.gridwidth = 2;
        gridPanel.add(lastNameTextField, cs);

        cs.insets = new Insets(10,0,10,0);
        cs.gridx = 1;
        cs.gridy = 7;
        cs.gridwidth = 1;
        gridPanel.add(new JLabel("E-mail address: "), cs);

        emailTextField = new JTextField(20);
        emailTextField.setBorder(BorderFactory.createCompoundBorder(border,BorderFactory.createEmptyBorder(3, 3, 3, 3)));
        cs.insets = new Insets(10,0,10,15);
        cs.gridx = 2;
        cs.gridy = 7;
        cs.gridwidth = 2;
        gridPanel.add(emailTextField, cs);

        cs.insets = new Insets(10,0,10,0);
        cs.gridx = 1;
        cs.gridy = 11;
        cs.gridwidth = 1;

        gridPanel.add(new JLabel("Password: "), cs);

        passwordField = new JPasswordField(20);
        passwordField.setBorder(BorderFactory.createCompoundBorder(border,BorderFactory.createEmptyBorder(3, 3, 3, 3)));
        cs.insets = new Insets(10,0,10,15);
        cs.gridx = 2;
        cs.gridy = 11;
        cs.gridwidth = 2;

        gridPanel.add(passwordField, cs);

        cs.insets = new Insets(10,0,10,0);
        cs.gridx = 1;
        cs.gridy = 13;
        cs.gridwidth = 1;
        gridPanel.add(new JLabel("Short description: "), cs);


        descriptionTextArea = new JTextArea(4,20);
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setBorder(BorderFactory.createCompoundBorder(border,BorderFactory.createEmptyBorder(3, 3, 3, 3)));
        cs.insets = new Insets(10,0,10,15);
        cs.gridx = 2;
        cs.gridy = 13;
        cs.gridwidth = 2;
        gridPanel.add(descriptionTextArea, cs);


        cs.gridx = 1;
        cs.gridy = 15;
        cs.gridwidth = 1;
        gridPanel.add(new JLabel("Select your avatar"), cs);

        avatarButton = new JButton("Choose your avatar");
        cs.gridx = 2;
        cs.gridy = 15;
        cs.gridwidth = 1;
        gridPanel.add(avatarButton, cs);




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

//        gridPanel.add(avatarButton);
    }
    
    private PartyDAOI getPartyDAO() throws NamingException {
		if (partyDAO == null) {
			String name = "ejb:/samo-tozla//PartyDAO!" + PartyDAOI.class.getName();
			partyDAO = (PartyDAOI) ctx.lookup(name);
		}
		return partyDAO;
	}
    
    @Override
    public void actionPerformed(ActionEvent e) {
    	String email = emailTextField.getText(),
    			firstName = firstNameTextField.getText(),
    			lastName = lastNameTextField.getText(),
    			password = String.copyValueOf(passwordField.getPassword()),
    			desc = descriptionTextArea.getText();
    	try {
    		Party p = this.getPartyDAO().register(email, password, firstName, lastName, desc, ImageUtil.encodeToString(avatar, "jpg"));
    		if(p != null)
    			this.showSuccessDialog();
    		else
        		JOptionPane.showMessageDialog(this, "A user with the same email exists.", "Could not register", JOptionPane.ERROR_MESSAGE);
    	} catch (NamingException ne) {
    		ne.printStackTrace();
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
