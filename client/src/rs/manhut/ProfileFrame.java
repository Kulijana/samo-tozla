package rs.manhut;

import com.sun.tools.javac.Main;
import rs.manhut.beans.ListingDAO;
import rs.manhut.beans.ListingDAOI;
import rs.manhut.beans.PartyDAO;
import rs.manhut.entities.Listing;
import rs.manhut.entities.Party;

import javax.imageio.ImageIO;
import javax.naming.InitialContext;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProfileFrame extends JFrame {

    private Image avatar;


    private Party party;
    private InitialContext ctx;
    private List<Listing> listings;

    //TODO put the regular categories, or come up with a fancier solution
    private JTextField category1;
    private JTextField category2;

    public ProfileFrame(Party party, InitialContext ctx){
        this.party = party;
        this.ctx = ctx;

        this.setSize(1920,1080);

        this.setLayout(new BorderLayout());

        //TODO to be replaced with image generated for the party
        try{
            avatar = ImageIO.read(new File("./Testing.jpg"));
        }catch(Exception ex){
            ex.printStackTrace();
        }

        //TODO replace this with an actual list of listings from the party
        ListingDAOI listingDAOI = new ListingDAO();
        listings = listingDAOI.getPartyListings(party);

        this.add(northPanel(), BorderLayout.NORTH);

        JScrollPane pane = new JScrollPane();
        pane.setViewportView(centralPanel());
        this.add(pane,BorderLayout.CENTER);

        this.add(southPanel(),BorderLayout.SOUTH);


        this.setVisible(true);
    }
//for testing purposes
//    public ProfileFrame(String username, String nameSurname, Image avatar, List<Listing> listings){
//        this.username = username;
//        this.nameSurname = nameSurname;
//        this.avatar = avatar;
//        this.listings = listings;
//
//        this.setSize(1920,1080);
//
//        this.setLayout(new BorderLayout());
//
//        this.add(northPanel(), BorderLayout.NORTH);
//
//        JScrollPane pane = new JScrollPane();
//        pane.setViewportView(centralPanel());
//        this.add(pane,BorderLayout.CENTER);
//
//        this.add(southPanel(),BorderLayout.SOUTH);
//
//
//        this.setVisible(true);
//    }

    private JPanel northPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JLabel avatarLabel = new JLabel(new ImageIcon(avatar));
        panel.add(avatarLabel, BorderLayout.WEST);
        panel.add(new JLabel(party.getFirstName() + " " + party.getLastName()));
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame mainFrame = new MainFrame(party,ctx);
                dispose();
            }
        });
        panel.add(backButton, BorderLayout.EAST);
        panel.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 3), party.getEmail()));
        return panel;
    }


    private JPanel centralPanel(){

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        Image image = null;
        try {
            image = ImageIO.read(new File("./Testing.jpg"));

        }catch(Exception ex){
            ex.printStackTrace();
        }
        //panel.add(new JLabel(new ImageIcon(image)));
        for(Listing listing : listings){
            panel.add(new MinimizedListingPanel(listing));
        }
        for(int i = 0;i < 14;i++){
            GridBagConstraints c =new GridBagConstraints();
            c.gridx = i%6;
            c.gridy = i/6;
            c.anchor = GridBagConstraints.NORTH;
            c.insets = new Insets(10,10,10,10);
            panel.add(new MinimizedListingPanel(image, 404.00),c);
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
                CreateListingFrame newListing = new CreateListingFrame(party,ctx);
            }
        });
        panel.add(button);
        return panel;
    }
}
