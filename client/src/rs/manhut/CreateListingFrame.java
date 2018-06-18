package rs.manhut;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import rs.manhut.beans.ListingDAOI;
import rs.manhut.entities.Listing;
import rs.manhut.entities.Party;

public class CreateListingFrame extends JFrame implements ActionListener {
	
	private JTextField nameTextField;
	private JTextField materialTextField;
	private JTextField colorTextField;
	private JTextField startPriceTextField;
	private JTextArea descriptionTextArea;
	
	private Party party;
	
	private ListingDAOI listingDAO;
    private InitialContext ctx;
	
	public CreateListingFrame(Party party, InitialContext ctx) {
		this.party = party;
		this.ctx = ctx;
		
		this.setSize(600, 800);
		
		GridLayout layout = new GridLayout(6, 2);
		this.setLayout(layout);
		
		createComponents();
		
		this.setVisible(true);
	}
	
	public void createComponents() {
		this.add(new JLabel("Name:"));
		nameTextField = new JTextField();
		this.add(nameTextField);
		this.add(new JLabel("Material:"));
		materialTextField = new JTextField();
		this.add(materialTextField);
		this.add(new JLabel("Color:"));
		colorTextField = new JTextField();
		this.add(colorTextField);
		this.add(new JLabel("Start price:"));
		startPriceTextField = new JTextField();
		this.add(startPriceTextField);
		this.add(new JLabel("Description:"));
		descriptionTextArea = new JTextArea();
		this.add(descriptionTextArea);
		this.add(new JPanel());
		
		JButton submitButton = new JButton("Create listing");
		
		submitButton.addActionListener(this);
		
		this.add(submitButton);
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
		
		try {
			Listing listing = this.getListingDAO().createListing(this.party, name, material, color, startPrice, description);
			// TODO ZATVORI OVAJ JFRAME ILI VRATI NAZAD ILI IZBACI DIALOG KAO EEEJ CREATED JE...
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
	}
}
