package rs.manhut;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import rs.manhut.beans.BidDAOI;
import rs.manhut.entities.Bid;
import rs.manhut.entities.Listing;

public class BidPanel extends JPanel {
	
	private Listing listing;
	private InitialContext ctx;
	
	private BidDAOI bidDAO;
	
	public BidPanel(Listing listing, InitialContext ctx) {
		this.listing = listing;
		this.ctx = ctx;
		
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0.05;
		c.anchor = GridBagConstraints.CENTER;
		
		this.add(this.createBidControls(), c);
		c.gridy = 1;
		c.weighty = 0.95;
		this.add(this.createBidList(), c);
	}
	
	private BidDAOI getBidDAO() throws NamingException {
    	if(bidDAO == null) {
			String name = "ejb:/samo-tozla//BidDAO!" + BidDAOI.class.getName();
			bidDAO = (BidDAOI) ctx.lookup(name);
    	}
    	return bidDAO;
    }
	
	private JPanel createBidControls() {
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new GridBagLayout());
		controlPanel.setBackground(Color.WHITE);
		
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(0, 10, 0, 0);
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.6;
		c.weighty = 1;
		controlPanel.add(new JLabel("Bidding Auction"), c);
		
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(0, 10, 0, 10);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.weightx = 0.2;
		JButton bidButton = new JButton("New Bid");
		controlPanel.add(bidButton, c);
		
		c.gridx = 2;
		JButton refreshButton = new JButton("Refresh");
		controlPanel.add(refreshButton, c);
		
		return controlPanel;
	}
	
	private JScrollPane createBidList() {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		
		scrollPane.setViewportView(getBidListPanel());
		return scrollPane;
	}
	
	private JPanel getBidListPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		try {
			List<Bid> bids = this.getBidDAO().getListingBids(listing);
			bids = new ArrayList<>();
			Bid b1 = new Bid();
			b1.setAmount(50.00);
			b1.setBidder(null);
			b1.setId(5L);
			b1.setListing(listing);
			bids.add(b1);
			
			b1 = new Bid();
			b1.setAmount(100.00);
			b1.setBidder(null);
			b1.setId(5L);
			b1.setListing(listing);
			bids.add(b1);
			
			b1 = new Bid();
			b1.setAmount(10000.00);
			b1.setBidder(null);
			b1.setId(5L);
			b1.setListing(listing);
			bids.add(b1);
			
			
			for(Bid bid : bids) {
				panel.add(new BidComponent(bid));
			}
		} catch (NamingException ne) {
			ne.printStackTrace();
		}
		
		return panel;
	}
}
