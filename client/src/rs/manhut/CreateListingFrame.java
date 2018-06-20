package rs.manhut;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.NumberFormat;

import javax.imageio.ImageIO;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import rs.manhut.beans.ListingDAOI;
import rs.manhut.entities.Listing;
import rs.manhut.entities.Listing.JewelryColor;
import rs.manhut.entities.Listing.JewelryType;
import rs.manhut.entities.Listing.MaterialType;
import rs.manhut.entities.Party;

public class CreateListingFrame extends JFrame implements ActionListener {
	
	private JTextField nameTextField;
	private JComboBox<String> materialComboBox;
	private JComboBox<String> colorComboBox;
	private JFormattedTextField startPriceTextField;
	private JComboBox<String> typeComboBox;
	private JTextArea descriptionTextArea;
	
	private Party party;
	private BufferedImage avatar;
	
	private ListingDAOI listingDAO;
    private InitialContext ctx;
	
	public CreateListingFrame(Party party, InitialContext ctx) {
		this.party = party;
		this.ctx = ctx;
		
		this.setSize(400, 600);
		this.setTitle("Create a new listing");
		
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
		materialComboBox = new JComboBox<>();
		for(MaterialType material : MaterialType.values()) {
			materialComboBox.addItem(material.toString());
		}
		this.add(materialComboBox,c);
		
		
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 2;
		c.ipady = 10;
		c.insets = new Insets(10, 10,10,15);
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(new JLabel("Color:"), c);
		
		colorComboBox = new JComboBox<>();
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 2;
		c.ipady = 10;
		c.insets = new Insets(10, 0,10,15);
		c.fill = GridBagConstraints.HORIZONTAL;
		colorComboBox = new JComboBox<>();
		for(JewelryColor color : JewelryColor.values()) {
			colorComboBox.addItem(color.toString());
		}
		this.add(colorComboBox,c);
		
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 3;
		c.weightx = 0.3;
		c.ipady = 10;
		c.insets = new Insets(10, 10,10,15);
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(new JLabel("Start price:"),c);
		
		NumberFormat amountFormat = NumberFormat.getCurrencyInstance();
		startPriceTextField = new JFormattedTextField(amountFormat);
		startPriceTextField.setValue(100);

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
		c.gridy = 4;
		c.ipady = 60;
		c.ipady = 10;
		c.weightx = 0.3;
		c.insets = new Insets(10, 10,10,15);
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(new JLabel("Jewelry type:"),c);
		
		typeComboBox = new JComboBox<>();
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 4;
		c.ipady = 10;
		c.insets = new Insets(10, 0,10,15);
		c.fill = GridBagConstraints.HORIZONTAL;
		typeComboBox = new JComboBox<>();
		for(JewelryType type : JewelryType.values()) {
			typeComboBox.addItem(type.toString());
		}
		this.add(typeComboBox,c);
		
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
		c.weighty = 0.3;
		c.insets = new Insets(10, 0,10,15);
		c.fill = GridBagConstraints.BOTH;
		this.add(descriptionTextArea,c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 8;
		c.ipady = 60;
		c.ipady = 10;
		c.weightx = 0.3;
		c.insets = new Insets(10, 10,10,15);
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(new JLabel("Jewelry image:"), c);
		
		c.gridx = 1;
		c.gridy = 8;
		c.weightx = 0.7;
		c.insets = new Insets(10, 0,10,15);
		c.fill = GridBagConstraints.HORIZONTAL;
		
		JButton avatarButton = new JButton();
        avatarButton.setText("Select an image");
        
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
                    }catch (IOException ex){
                        System.out.println("Error in converting the File to Image");
                    }
                    catch(Exception ex){
                        System.out.println("Something went wrong with the avatar.");
                    }
                }
            }
        });
        
        this.add(avatarButton, c);

		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 9;
		c.gridwidth= 2;
		c.insets = new Insets(0, 0, 10, 0);
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
		String material = (String) this.materialComboBox.getSelectedItem();
		String color = (String) this.colorComboBox.getSelectedItem();
		Object startPrice = this.startPriceTextField.getValue();
		String description = this.descriptionTextArea.getText();
		String type = (String) this.typeComboBox.getSelectedItem();
		Double price = 0.0;
		
		if(startPrice instanceof Double)
			price = (Double) startPrice;
		if(startPrice instanceof Long)
			price = Double.parseDouble(startPrice.toString());
		
		System.out.println(name + " " + material + " " + color + " " + startPrice.toString() + " " + description + " " + type + " " + avatar.toString());
		
		try {
			Listing listing = this.getListingDAO().createListing(this.party, name, material, type, color, price, description, ImageUtil.encodeToString(avatar, "jpg"));
			if(listing != null)
				dispose();
			else
				JOptionPane.showMessageDialog(this, "Could not create listing, make sure you don't have a listing with the same name and that you've entered all the information.", "Could not create listing", JOptionPane.ERROR_MESSAGE);
		} catch (NamingException ex) {
			ex.printStackTrace();
		}
	}
}
