package rs.manhut;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;

import rs.manhut.entities.Bid;

public class BidComponent extends JPanel {
	private Bid bid;
	
	public BidComponent(Bid bid) {
		this.bid = bid;
		this.setMaximumSize(new Dimension(3000, 50));
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.LINE_START;
		c.gridx = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = 0;
		c.weightx = 0.4;
		c.weighty = 1;
		//this.add(new JLabel(bid.getBidder().getFirstName() + " " + bid.getBidder().getLastName()), c);
		this.add(new JLabel("John Doe"), c);
		
		c.anchor = GridBagConstraints.LINE_START;
		c.gridx = 1;
		c.insets = new Insets(0, 0, 0, 0);
		c.weightx = 0.4;
		this.add(new JLabel(this.getBidDate()), c);
		
		c.weightx = 0.2;
		c.insets = new Insets(0, 0, 0, 0);
		c.anchor = GridBagConstraints.LINE_START;
		c.gridx = 3;
		this.add(new JLabel(String.format("%9.2f", bid.getAmount())), c);
	}
	
	private String getBidDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		return sdf.format(new Date());
	}
}
