package rs.manhut;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import rs.manhut.beans.ListingDAOI;
import rs.manhut.beans.PartyDAOI;
import rs.manhut.entities.Listing.JewelryColor;
import rs.manhut.entities.Listing;
import rs.manhut.entities.Party;

public class LoginFrame extends JFrame {

    private JPasswordField passwordField;
    private JTextField emailTextField;
    private JButton loginButton;
    private JButton registerButton;
    
    private PartyDAOI partyDAO;
    private InitialContext ctx;
    private ListingDAOI listingDAO;
    
    public LoginFrame(InitialContext ctx) {
    	this.ctx = ctx;
    	
    	this.setLayout(new GridBagLayout());
    	this.setTitle("Login");
    	
    	GridBagConstraints c = new GridBagConstraints();
    	c.anchor = GridBagConstraints.LINE_END;
    	c.insets = new Insets(10, 0, 0, 10);
    	c.gridx = 0;
    	c.gridy = 0;
    	c.weightx = 1;
    	c.weighty = 1;
    	this.add(new JLabel("Email:"), c);
    	
    	c.gridx = 0;
    	c.gridy = 1;
    	this.add(new JLabel("Password:"), c);
    	
    	c = new GridBagConstraints();
    	c.anchor = GridBagConstraints.LINE_START;
    	c.insets = new Insets(10, 0, 0, 10);
    	c.fill = GridBagConstraints.HORIZONTAL;
    	c.gridx = 1;
    	c.gridy = 0;
    	c.weightx = 1;
    	c.gridwidth = 2;
    	
    	emailTextField = new JTextField();
    	this.add(emailTextField, c);
    	
    	c.gridx = 1;
    	c.gridy = 1;
    	
    	passwordField = new JPasswordField();
        passwordField.setEchoChar('*');
        this.add(passwordField, c);
        
        this.createButtonComponents();
        
        this.setSize(400, 200);
        this.setVisible(true);
    }
    
    private void createButtonComponents() {
    	GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 1;
        c.gridy = 2;
        c.weighty = 1;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        
        loginButton = new JButton("Login");
        this.add(loginButton, c);
        
        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.LINE_END;
        c.insets = new Insets(5, 0, 5, 5);
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        c.weighty = 1;
        
        this.add(new JLabel("Don't have an account?"), c);
        
        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 0, 5, 5);
        c.gridx = 2;
        c.gridy = 3;
        c.weightx = 1;
        
        registerButton = new JButton("Register");
        this.add(registerButton, c);
        
        this.registerButtonListeners();
    }
    
    private void registerButtonListeners() {
    	loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LoginFrame.this.login();
			}
		});
    	
    	registerButton.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			LoginFrame.this.showRegisterFrame();
    		}
    	});
    }
    
    private ListingDAOI getListingDAO() throws NamingException {
    	if(listingDAO == null) {
			String name = "ejb:/samo-tozla//ListingDAO!" + ListingDAOI.class.getName();
			listingDAO = (ListingDAOI) ctx.lookup(name);
    	}
    	return listingDAO;
    }
    
    private void login() {
    	String email = emailTextField.getText().trim();
		String password = String.copyValueOf(passwordField.getPassword());
		
		if(email.isEmpty()) {
			JOptionPane.showMessageDialog(LoginFrame.this, "Email field cannot be empty!", "Error", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		if(password.isEmpty()) {
			JOptionPane.showMessageDialog(LoginFrame.this, "Password field cannot be empty!", "Error", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		try {
			Party party = this.getPartyDAO().login(email, password);
			if(party != null) {
				// TODO open main frame
				List<Listing> l = this.getListingDAO().getAllListings(null, null, null, null);
				
				if(!l.isEmpty()) {
					JFrame frame = new JFrame();
					frame.setSize(1600, 900);
					frame.add(new ListingPanel(party, l.get(0), ctx));
					frame.setVisible(true);
				}
			} else {
				JOptionPane.showMessageDialog(LoginFrame.this, "The email or password you entered is incorrect.", "Error", JOptionPane.WARNING_MESSAGE);
			}
		} catch (NamingException ne) {
			ne.printStackTrace();
		}
    }
    
    private void showRegisterFrame() {
    	RegistrationFrame rf = new RegistrationFrame(this.ctx);
    	this.setVisible(false);
    	this.dispose();
    }
    
    private PartyDAOI getPartyDAO() throws NamingException {
		if (partyDAO == null) {
			String name = "ejb:/samo-tozla//PartyDAO!" + PartyDAOI.class.getName();
			partyDAO = (PartyDAOI) ctx.lookup(name);
		}
		return partyDAO;
	}
}
