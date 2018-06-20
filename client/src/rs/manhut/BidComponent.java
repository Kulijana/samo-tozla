package rs.manhut;

import java.awt.Color;
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
		c.gridy = 0;
		c.weightx = 0.4;
		c.weighty = 1;
		c.insets = new Insets(0, 10, 0, 0);
		JLabel nameLabel = new JLabel(bid.getBidder().getFirstName() + " " + bid.getBidder().getLastName());
		nameLabel.setPreferredSize(new Dimension(200, 30));
		this.add(nameLabel, c);
		
		c.anchor = GridBagConstraints.LINE_START;
		c.gridx = 1;
		c.weightx = 0.4;
		c.insets = new Insets(0, 0, 0, 0);
		JLabel datelabel = new JLabel(this.getBidDate());
		datelabel.setPreferredSize(new Dimension(200, 30));
		this.add(datelabel, c);
		
		c.weightx = 0.2;
		c.anchor = GridBagConstraints.LINE_END;
		c.gridx = 2;
		c.insets = new Insets(0, 0, 0, 10);
		JLabel amountLabel = new JLabel(String.format("%.2f", bid.getAmount()));
		amountLabel.setPreferredSize(new Dimension(100, 30));
		this.add(amountLabel, c);
		
		if(bid.isWinning())
			this.setBackground(Color.CYAN);
	}
	
	private String getBidDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		return sdf.format(bid.getCreatedOn());
	}
}
