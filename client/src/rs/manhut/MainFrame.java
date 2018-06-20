package rs.manhut;

import rs.manhut.MinimizedListingPanel.ListingClickListener;
import rs.manhut.beans.ListingDAO;
import rs.manhut.beans.ListingDAOI;
import rs.manhut.entities.Listing;
import rs.manhut.entities.Party;

import javax.imageio.ImageIO;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public class MainFrame extends JFrame implements ListingClickListener {

    private Party party;
    private InitialContext ctx;

    private JTextField nameField;
    private JComboBox materialBox;
    private JComboBox colorBox;
    private JComboBox typeBox;
    private List<Listing> listings;

    private JPanel centralPanel;
    private JScrollPane pane;
    
    private ListingDAOI listingDAO;

    public MainFrame(Party party, InitialContext ctx){
        this.party = party;
        this.ctx = ctx;
        this.setSize(1600, 900);
        this.setTitle("Search all listings");

        this.add(westPanel(), BorderLayout.WEST);
        this.add(northPanel(), BorderLayout.NORTH);
        pane = new JScrollPane();
        pane.getVerticalScrollBar().setUnitIncrement(16);
        
    	try {
			listings = getListingDAO().getAllListings(null, null, null, null, true);
	        centralPanel = generateCentralPanel();
	        pane.setViewportView(centralPanel);
	        this.add(pane, BorderLayout.CENTER);
		} catch (NamingException e) {
			e.printStackTrace();
		}
        this.setVisible(true);
    }

    private JPanel northPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        if(party.getProfilePicture() != null) {
	        Image image = ImageUtil.decodeToImage(party.getProfilePicture()).getScaledInstance(80, 80, Image.SCALE_DEFAULT);
	        JLabel avatarLabel = new JLabel(new ImageIcon(image));
	        avatarLabel.setPreferredSize(new Dimension(80, 80));
	        panel.add(avatarLabel, BorderLayout.WEST);
        }
        
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(new JLabel("  " + party.getFirstName() + " " + party.getLastName()));
        JButton backButton = new JButton("Go to profile");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProfileFrame profileFrame = new ProfileFrame(party, ctx);
                dispose();
            }
        });
        panel.add(backButton, BorderLayout.EAST);
        return panel;
    }

    private JPanel generateCentralPanel(){
        JPanel panel = new JPanel();
        ModifiedFlowLayout flowLayout= new ModifiedFlowLayout();
        flowLayout.setHgap(10);
        flowLayout.setVgap(10);
        panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        panel.setLayout(flowLayout);
        
        Image image = null;
        try {
            image = ImageIO.read(new File("./Testing.jpg"));
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        
    	for(Listing l : listings) {	
			panel.add(new MinimizedListingPanel(l, party, ctx, this));
    	}
        
        panel.setVisible(true);
        return panel;
    }

    private JPanel westPanel(){
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c;

        c = generateConstraints(0,0);
        panel.add(new JLabel("Name"),c);

        c = generateConstraints(1,0);
        nameField = new JTextField();
        panel.add(nameField,c);

        c = generateConstraints(0, 1);
        panel.add(new JLabel("Material"), c);

        materialBox = new JComboBox<>();
        materialBox.addItem("");
        for(Listing.MaterialType material : Listing.MaterialType.values())
            materialBox.addItem(material.name());
        c = generateConstraints(1,1);
        panel.add(materialBox,c);

        c = generateConstraints(0, 2);
        panel.add(new JLabel("Color"),c);

        colorBox = new JComboBox();
        colorBox.addItem("");
        for(Listing.JewelryColor color : Listing.JewelryColor.values()) {
            colorBox.addItem(color.name());
        }
        c = generateConstraints(1, 2);
        panel.add(colorBox,c);

        c = generateConstraints(0,3);
        panel.add(new JLabel("Type"),c);

        typeBox = new JComboBox();
        typeBox.addItem("");
        for(Listing.JewelryType type : Listing.JewelryType.values())
            typeBox.addItem(type.name());
        c = generateConstraints(1, 3);
        panel.add(typeBox,c);

        JButton searchButton = new JButton("Search");
        c = generateConstraints(0, 4);
        c.gridwidth = 2;
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
            		String material = materialBox.getSelectedItem().toString();
            		if(material.isEmpty())
            			material = null;
            		
            		String color = colorBox.getSelectedItem().toString();
            		if(color.isEmpty())
            			color = null;
            		
            		String type = typeBox.getSelectedItem().toString();
            		if(type.isEmpty())
            			type = null;
            		
            		
	                listings = getListingDAO().getAllListings(nameField.getText()
	                        , material
	                        , color
	                        , type
	                        , true);
	                centralPanel = generateCentralPanel();
	                pane.setViewportView(centralPanel);
	                pane.validate();
	                pane.repaint();
            	} catch (NamingException ne) {
            		ne.printStackTrace();
            	}
            }
        });
        panel.add(searchButton,c);

        panel.setBorder(BorderFactory.createLineBorder(Color.black));

        return panel;
    }
    
    private ListingDAOI getListingDAO() throws NamingException {
    	if(listingDAO == null) {
			String name = "ejb:/samo-tozla//ListingDAO!" + ListingDAOI.class.getName();
			listingDAO = (ListingDAOI) ctx.lookup(name);
    	}
    	return listingDAO;
    }

    private GridBagConstraints generateConstraints(int x, int y){
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = x;
        c.gridy = y;
        c.insets = new Insets(10,15,10,15);
        c.ipadx = 10;
        c.ipady = 10;
        c.fill = GridBagConstraints.HORIZONTAL;
        return c;
    }

	@Override
	public void listingClicked(Listing l) {
		JFrame frame = new JFrame();
		frame.setSize(1600, 900);
		
		frame.add(new ListingPanel(party, l, ctx));
		
		frame.setVisible(true);
	}
}
