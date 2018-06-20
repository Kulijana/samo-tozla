package rs.manhut;

import rs.manhut.beans.BidDAOI;
import rs.manhut.entities.Bid;
import rs.manhut.entities.Listing;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.*;
import javax.swing.border.EtchedBorder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class MinimizedListingPanel extends JPanel {

    private Image itemImage;
    private BidDAOI bidDAO;
    private InitialContext ctx;
    private Listing listing;
    
    public MinimizedListingPanel(Listing l, InitialContext ctx){
    	this.listing = l;
    	this.ctx = ctx;
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.WHITE);
        
        if(l.getImage() != null)
        	this.itemImage = ImageUtil.decodeToImage(l.getImage()).getScaledInstance(150, 150, Image.SCALE_DEFAULT);
        this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        
        GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 0, 5, 0), 0, 0);
        JLabel nameLabel = new JLabel(l.getName());
        this.add(nameLabel, gbc);
        
        try {
	        gbc = new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(155, 0, 5, 0), 0, 0);
	        
	       List<Bid> bids = this.getBidDAO().getListingBids(l);
	       
	       if(bids.isEmpty())
	    	   this.add(new JLabel(String.format("Starting price: %.2f", l.getStartPrice())), gbc);
	       else
	    	   this.add(new JLabel(String.format("Current bid: %.2f", bids.get(0).getAmount())), gbc);
        } catch (NamingException e) {
        	e.printStackTrace();
		}
        
        this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO go to listing
				System.out.println("Clicked image");
			}
		});
        
        this.setPreferredSize(new Dimension(220, 220));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(itemImage, 35, 30, this);           
    }
    
    private BidDAOI getBidDAO() throws NamingException {
    	if(bidDAO == null) {
			String name = "ejb:/samo-tozla//BidDAO!" + BidDAOI.class.getName();
			bidDAO = (BidDAOI) ctx.lookup(name);
    	}
    	return bidDAO;
    }
}
