package rs.manhut;

import rs.manhut.entities.Listing;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MinimizedListingPanel extends JPanel {


    //TODO will most likely need an ID of some sort, for easier manipulation of DB when requesting to see the actual auction
    private Image itemImage;
    private Double currentBid;

    public MinimizedListingPanel(Image itemImage, Double currentBid){
        this.setLayout(new BorderLayout());
        this.itemImage = itemImage;
        this.currentBid = currentBid;
        this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

        JLabel imageLabel = new JLabel(new ImageIcon(itemImage));
        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //TODO open the frame containing details about the mentioned auction
            }
        });

        this.add(imageLabel, BorderLayout.NORTH);
        JPanel bidPanel = new JPanel();
        bidPanel.add(new JLabel("Current bid:"), BorderLayout.WEST);
        bidPanel.add(new JLabel(currentBid.toString()), BorderLayout.EAST);
        this.add(bidPanel);
    }

    public MinimizedListingPanel(Listing listing){
        //TODO change from getStartPrice() to current price and get itemImage from listing
        this.currentBid = listing.getStartPrice();
        //this.itemImage = listing.getImage();
    }
}
