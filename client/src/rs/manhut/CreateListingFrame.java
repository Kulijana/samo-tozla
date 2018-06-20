package rs.manhut;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.*;

import rs.manhut.beans.ListingDAOI;
import rs.manhut.entities.Listing;
import rs.manhut.entities.Party;

public class CreateListingFrame extends JFrame implements ActionListener {
	
	private JTextField nameTextField;
	private JTextField materialTextField;
	private JTextField colorTextField;
	private JTextField startPriceTextField;
	private JTextField typeTextField;
	private JTextArea descriptionTextArea;
	
	private Party party;
	
	private ListingDAOI listingDAO;
    private InitialContext ctx;
	
	public CreateListingFrame(Party party, InitialContext ctx) {
		this.party = party;
		this.ctx = ctx;
		
		this.setSize(400, 400);
		
		GridBagLayout layout = new GridBagLayout();
		this.setLayout(layout);
		
		createComponents();
		
		this.setVisible(true);
	}
	
	public void createComponents() {

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 10;
		c.insets = new Insets(10, 10,10,15);
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(new JLabel("Name:"), c);
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.ipady = 10;
		c.insets = new Insets(10, 0,10,15);
		c.fill = GridBagConstraints.HORIZONTAL;
		nameTextField = new JTextField();
		this.add(nameTextField, c);
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.ipady = 10;
		c.insets = new Insets(10, 10,10,15);
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(new JLabel("Material:"), c);
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		c.ipady = 10;
		c.insets = new Insets(10, 0,10,15);
		c.fill = GridBagConstraints.HORIZONTAL;
		materialTextField = new JTextField();
		this.add(materialTextField,c);
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 2;
		c.ipady = 10;
		c.insets = new Insets(10, 10,10,15);
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(new JLabel("Color:"), c);
		colorTextField = new JTextField();
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 2;
		c.ipady = 10;
		c.insets = new Insets(10, 0,10,15);
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(colorTextField, c);
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 3;
		c.weightx = 0.3;
		c.ipady = 10;
		c.insets = new Insets(10, 10,10,15);
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(new JLabel("Start price:"),c);
		startPriceTextField = new JTextField();

		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 3;
		c.ipady = 10;
		c.insets = new Insets(10, 0,10,15);
		c.weightx = 0.7;
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(startPriceTextField,c);
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 5;
		c.ipady = 60;
		c.ipady = 10;
		c.insets = new Insets(10, 10,10,15);
		c.gridheight = 3;
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(new JLabel("Description:"),c);


		descriptionTextArea = new JTextArea();
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 5;
		c.ipady = 10;
		c.gridheight = 3;
		c.insets = new Insets(10, 0,10,15);
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(descriptionTextArea,c);
		//this.add(new JPanel());

//		this.add(startPriceTextField);
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 4;
		c.ipady = 60;
		c.ipady = 10;
		c.weightx = 0.3;
		c.insets = new Insets(10, 10,10,15);
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(new JLabel("Jewelry type:"),c);
		typeTextField = new JTextField();
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 4;
		c.ipady = 10;
		c.insets = new Insets(10, 0,10,15);
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(typeTextField,c);
//		this.add(new JLabel("Description:"));




		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 8;
		c.gridwidth= 2;
		JButton submitButton = new JButton("Create listing");
		
		submitButton.addActionListener(this);
		
		this.add(submitButton,c);
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
		String name = this.nameTextField.getText();
		String material = this.materialTextField.getText();
		String color = this.colorTextField.getText();
		double startPrice = Double.valueOf(this.startPriceTextField.getText());
		String description = this.descriptionTextArea.getText();
		String type = this.typeTextField.getText();
		
		try {
			Listing listing = this.getListingDAO().createListing(this.party, name, material, type, color, startPrice, description);
			dispose();
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
	}
}
