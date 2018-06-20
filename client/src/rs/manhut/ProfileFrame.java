package rs.manhut;

import rs.manhut.MinimizedListingPanel.ListingClickListener;
import rs.manhut.beans.ListingDAO;
import rs.manhut.beans.ListingDAOI;
import rs.manhut.beans.PartyDAO;
import rs.manhut.entities.Listing;
import rs.manhut.entities.Party;

import javax.imageio.ImageIO;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProfileFrame extends JFrame implements ListingClickListener  {

    private Image avatar;


    private Party party;
    private InitialContext ctx;
    private List<Listing> listings;

    //TODO put the regular categories, or come up with a fancier solution
    private JTextField category1;
    private JTextField category2;

    private ListingDAOI listingDAO;

    public ProfileFrame(Party party, InitialContext ctx){
        this.party = party;
        this.ctx = ctx;
        
        this.setTitle("Your profile and listings");

        this.setSize(1600,900);

        this.setLayout(new BorderLayout());

        //TODO to be replaced with image generated for the party
        try{
            avatar = ImageIO.read(new File("./Testing.jpg"));
        }catch(Exception ex){
            ex.printStackTrace();
        }

        JScrollPane pane = new JScrollPane();
        pane.setViewportView(centralPanel());
        this.add(pane,BorderLayout.CENTER);
	

        this.add(northPanel(), BorderLayout.NORTH);
        this.add(southPanel(),BorderLayout.SOUTH);


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
        JButton backButton = new JButton("Back to search");
        backButton.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
                MainFrame mainFrame = new MainFrame(party,ctx);
                dispose();
            }
        });
        panel.add(backButton, BorderLayout.EAST);
        return panel;
    }


    private JPanel centralPanel(){
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
        
        try {
        	listings = getListingDAO().getPartyListings(party);
        	for(Listing l : listings) {
    			panel.add(new MinimizedListingPanel(l, party, ctx, this));
        	}
        } catch (NamingException ne) {
        	ne.printStackTrace();
        }
        
        panel.setVisible(true);
        return panel;
    }

    private JPanel southPanel(){
        JPanel panel = new JPanel();
        JButton button = new JButton("Add a new listing");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateListingFrame newListing = new CreateListingFrame(party, ctx);
            }
        });
        panel.add(button);
        return panel;
    }
    
    private ListingDAOI getListingDAO() throws NamingException {
    	if(listingDAO == null) {
			String name = "ejb:/samo-tozla//ListingDAO!" + ListingDAOI.class.getName();
			listingDAO = (ListingDAOI) ctx.lookup(name);
    	}
    	return listingDAO;
    }
    
    @Override
	public void listingClicked(Listing l) {
		JFrame frame = new JFrame();
		frame.setSize(1600, 900);
		
		frame.add(new ListingPanel(party, l, ctx));
		
		frame.setVisible(true);
	}
}
