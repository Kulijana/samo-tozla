package rs.manhut;

import rs.manhut.entities.Listing;
import rs.manhut.entities.Party;

import javax.imageio.ImageIO;
import javax.naming.InitialContext;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class MinimizedListingPanel extends JPanel {


    //TODO will most likely need an ID of some sort, for easier manipulation of DB when requesting to see the actual auction
    private Image itemImage;
    private Double currentBid;

    //this one is mostly for testing, the one under that takes in just a Listing as a parameter will be the one used
//    public MinimizedListingPanel(Image itemImage, Double currentBid){
//        this.setLayout(new BorderLayout());
//        this.itemImage = itemImage;
//        this.currentBid = currentBid;
//        this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
//
//        JLabel imageLabel = new JLabel(new ImageIcon(itemImage));
//        imageLabel.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//            }
//        });
//
//        this.add(imageLabel, BorderLayout.NORTH);
//        JPanel bidPanel = new JPanel();
//        bidPanel.add(new JLabel("Current bid:"), BorderLayout.WEST);
//        bidPanel.add(new JLabel(currentBid.toString()), BorderLayout.EAST);
//        this.add(bidPanel);
//    }

    public MinimizedListingPanel(Listing listing, Party party, InitialContext ctx){
        //TODO change from getStartPrice() to current price and get itemImage from listing
        this.currentBid = listing.getStartPrice();
        try{
            itemImage = ImageIO.read(new File("./Testing.jpg"));
        }catch(Exception ex){
            ex.printStackTrace();
        }
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

        JLabel imageLabel = new JLabel(new ImageIcon(itemImage));
        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ListingPanel listingPanel = new ListingPanel(party, listing, ctx);
            }
        });

        this.add(imageLabel, BorderLayout.NORTH);
        JPanel bidPanel = new JPanel();
        bidPanel.add(new JLabel("Current bid:"), BorderLayout.WEST);
        bidPanel.add(new JLabel(currentBid.toString()), BorderLayout.EAST);
        bidPanel.add(new JLabel(listing.getOwner().toString()), BorderLayout.SOUTH);
        this.add(bidPanel);

    }
}
