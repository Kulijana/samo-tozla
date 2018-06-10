package rs.manhut;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BidPanel extends JPanel {


    //TODO will most likely need an ID of some sort, for easier manipulation of DB when requesting to see the actual auction
    private Image itemImage;
    private Double currentBid;

    public BidPanel(Image itemImage, Double currentBid){
        this.setLayout(new BorderLayout());
        this.itemImage = itemImage;
        this.currentBid = currentBid;

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
}
