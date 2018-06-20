package rs.manhut;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import rs.manhut.beans.BidDAOI;
import rs.manhut.beans.ListingDAOI;
import rs.manhut.entities.Bid;
import rs.manhut.entities.Listing;
import rs.manhut.entities.Party;

public class BidPanel extends JPanel {
	
	private Listing listing;
	private Party party;
	private InitialContext ctx;
	
	private BidDAOI bidDAO;
	private ListingDAOI listingDAO;
	private JScrollPane scrollPane;
	
	private List<Bid> bids;
	
	public BidPanel(Listing listing, Party party, InitialContext ctx) {
		this.listing = listing;
		this.party = party;
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
		c.weightx = 0.8;
		c.weighty = 1;
		controlPanel.add(new JLabel("Bidding Auction"), c);
		
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(0, 0, 0, 0);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.weightx = 0.1;
		if(listing.getOwner().getId().equals(party.getId())) {
			JButton stopAuctionButton = new JButton("Stop auction");
			stopAuctionButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					stopAuction();
					stopAuctionButton.setEnabled(false);
				}
			});
			controlPanel.add(stopAuctionButton, c);
		} else {
			JButton bidButton = new JButton("New Bid");
			bidButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					showBidDialog();
				}
			});
			bidButton.setEnabled(listing.getActive());
			controlPanel.add(bidButton, c);
		}
		
		c.gridx = 2;
		JButton refreshButton = new JButton("Refresh");
		refreshButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				refreshBids();
			}
		});
		controlPanel.add(refreshButton, c);
		
		return controlPanel;
	}
	
	private void stopAuction() {
		try {
			Bid bid = this.getListingDAO().stopAuction(listing, party);
			if(bid != null) {
				JOptionPane.showMessageDialog(this, bid.getBidder().getFirstName() + " " + bid.getBidder().getLastName() +
												" won the auction with a " + "$" + String.format("%.2f", bid.getAmount()) + " bid.", "Auction stopped", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this, "Nobody won the auction as there were no bids.", "Auction stopped", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (NamingException ne) {
			ne.printStackTrace();
		}
		refreshBids();
	}
	
	private ListingDAOI getListingDAO() throws NamingException {
    	if(listingDAO == null) {
			String name = "ejb:/samo-tozla//ListingDAO!" + ListingDAOI.class.getName();
			listingDAO = (ListingDAOI) ctx.lookup(name);
    	}
    	return listingDAO;
    }
	
	private void showBidDialog() {
		refreshBids();
		
		Double maxBid = 0.0;
		if(!bids.isEmpty())
			maxBid = bids.get(0).getAmount();
			
		BidDialog bd = new BidDialog(maxBid, new BidSubmittedListener() {
			
			@Override
			public void bidSubmitted(Double amount) {
				try {
					Bid bid = getBidDAO().makeBid(listing, party, amount);
					refreshBids();
					if(bid == null)
						JOptionPane.showMessageDialog(BidPanel.this, "Your bid might be too low or you are bidding on your own listing.", "Could not submit bid", JOptionPane.ERROR_MESSAGE);
				} catch (NamingException e) {
					e.printStackTrace();
				}
			}
		});
		
		bd.setLocationRelativeTo(this);
		bd.setVisible(true);
	}
	
	private JScrollPane createBidList() {
		scrollPane = new JScrollPane();
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		
		scrollPane.setViewportView(getBidListPanel());
		return scrollPane;
	}
	
	private void refreshBids() {
		scrollPane.setViewportView(getBidListPanel());
		scrollPane.validate();
		scrollPane.repaint();
	}
	
	private JPanel getBidListPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		try {
			bids = this.getBidDAO().getListingBids(listing);
			for(Bid bid : bids) {
				panel.add(new BidComponent(bid));
			}
		} catch (NamingException ne) {
			ne.printStackTrace();
		}
		
		return panel;
	}
	
	private class BidDialog extends JDialog implements ActionListener {

		private JFormattedTextField amountField;
		private BidSubmittedListener listener;
		
		public BidDialog(Double maxBid, BidSubmittedListener listener) {
			this.listener = listener;
			this.setTitle("Submit new bid");
			this.setLayout(new GridBagLayout());
			this.setSize(300, 200);
			
			GridBagConstraints c = new GridBagConstraints();
			c.anchor = GridBagConstraints.LINE_END;
			c.gridx = 0;
			c.gridy = 0;
			c.weightx = 1;
			c.weighty = 1;
			c.insets = new Insets(0, 0, 0, 5);
			this.add(new JLabel("Amount:"), c);
			
			c.gridx = 1;
			c.insets = new Insets(0, 5, 0, 0);
			c.anchor = GridBagConstraints.LINE_START;
			NumberFormat amountFormat = NumberFormat.getCurrencyInstance();
			amountField = new JFormattedTextField(amountFormat);
			amountField.setColumns(12);
			amountField.setValue(maxBid + 100);
			this.add(amountField, c);
			
			c.gridx = 0;
			c.gridy = 1;
			c.anchor = GridBagConstraints.CENTER;
			JButton cancelButton = new JButton("Cancel");
			cancelButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					BidDialog.this.setVisible(false);
					BidDialog.this.dispose();
				}
			});
			this.add(cancelButton, c);
			
			c.gridx = 1;
			c.gridy = 1;
			c.anchor = GridBagConstraints.CENTER;
			JButton submitButton = new JButton("Submit bid");
			submitButton.addActionListener(this);
			this.add(submitButton, c);
			
			this.setVisible(true);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			this.setVisible(false);
			this.dispose();
			if(this.amountField.getValue() instanceof Double)
				this.listener.bidSubmitted((Double) this.amountField.getValue());
			if(this.amountField.getValue() instanceof Long)
				this.listener.bidSubmitted(Double.parseDouble(this.amountField.getValue().toString()));
		}
		
	}
	
	public interface BidSubmittedListener {
		void bidSubmitted(Double amount);
	}
}
