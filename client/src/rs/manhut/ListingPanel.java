package rs.manhut;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import rs.manhut.beans.CommentDAOI;
import rs.manhut.entities.Listing;
import rs.manhut.entities.Party;

public class ListingPanel extends JPanel {
	
	private Listing listing;
	private Party party;
	
	private CommentDAOI commentDAO;
	private InitialContext ctx;
	
	private JScrollPane scrollPane;
	
	public ListingPanel(Party party, Listing listing, InitialContext ctx) {
		this.listing = listing;
		this.ctx = ctx;
		this.party = party;
		
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.WHITE);
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 7;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.2;
		c.weighty = 0.05;
		c.anchor = GridBagConstraints.CENTER;
		
		JPanel imagePanel = new JPanel();
		imagePanel.setBackground(Color.BLUE);
		this.add(imagePanel, c);
		
		this.createDetailsPane();
		this.createCommentScrollPane();
		this.createBidPane();
		
		
	}
	
	private CommentDAOI getCommentDAO() throws NamingException {
    	if(commentDAO == null) {
			String name = "ejb:/samo-tozla//CommentDAO!" + CommentDAOI.class.getName();
			commentDAO = (CommentDAOI) ctx.lookup(name);
    	}
    	return commentDAO;
    }
	
	private void createBidPane() {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 7;
		c.gridwidth = 2;
		c.weighty = 1;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.BOTH;
		BidPanel panel = new BidPanel(this.listing, this.ctx);
		this.add(panel, c);
	}
	
	private void createDetailsPane() {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 0.2;
		c.weighty = 0.025;
		c.insets = new Insets(10, 10, 10, 10);
		c.anchor = GridBagConstraints.LINE_START;
		
		JLabel nameLabel = new JLabel();
		nameLabel.setText("Name: " + listing.getName());
		
		this.add(nameLabel, c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 0.2;
		c.weighty = 0.025;
		c.insets = new Insets(10, 10, 10, 10);
		c.anchor = GridBagConstraints.LINE_START;
		
		JLabel ownerLabel = new JLabel();
		ownerLabel.setText("Listing owner: " + listing.getOwner().getFirstName() + " " + listing.getOwner().getLastName());
		this.add(ownerLabel, c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 2;
		c.weightx = 0.2;
		c.weighty = 0.025;
		c.insets = new Insets(10, 10, 10, 10);
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.BOTH;
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
		panel.add(new JLabel("Color: "));
		JPanel colorPanel = new JPanel();
		colorPanel.setBackground(this.listing.getColor().getColor());
		colorPanel.setMaximumSize(new Dimension(30, 30));
		colorPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
		panel.add(colorPanel);
		panel.setBackground(Color.WHITE);
		panel.add(new JLabel("  " + this.listing.getColor().toString()));
		this.add(panel, c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 3;
		c.weightx = 0.2;
		c.weighty = 0.025;
		c.insets = new Insets(10, 10, 10, 10);
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.BOTH;
		this.add(new JLabel("Material: " + this.listing.getMaterial().toString()), c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 4;
		c.weightx = 0.2;
		c.weighty = 0.025;
		c.insets = new Insets(10, 10, 10, 10);
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.BOTH;
		this.add(new JLabel("Jewelry type: " + this.listing.getType().toString()), c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 5;
		c.gridheight = 2;
		c.weightx = 0.2;
		c.weighty = 0.05;
		c.insets = new Insets(10, 10, 10, 10);
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		
		JTextArea descTextArea = new JTextArea();
		descTextArea.setText("Description:\n" + listing.getDescription());
		descTextArea.setEditable(false);
		descTextArea.setBackground(Color.WHITE);
		
		this.add(descTextArea, c);
	}
	
	private void createCommentScrollPane() {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 1;
		c.gridheight = 8;
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.BOTH;
		
		
		try {
			scrollPane = new JScrollPane(new CommentPanel(party, this.getCommentDAO().getRootComments(this.listing), 0, ctx));
			scrollPane.getVerticalScrollBar().setUnitIncrement(16);
			this.add(scrollPane, c);
		} catch (NamingException ne) {
			ne.printStackTrace();
		}
		
		c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 0;
		c.weightx = 0.25;
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.HORIZONTAL;
		
		this.add(new JLabel("Comments"), c);
		
		c = new GridBagConstraints();
		c.gridx = 3;
		c.gridy = 0;
		c.weightx = 0.025;
		c.anchor = GridBagConstraints.LINE_END;
		c.fill = GridBagConstraints.HORIZONTAL;
		
		JButton refreshCommentsButton = new JButton("Refresh");
		refreshCommentsButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				refreshComments();
			}
		});
		this.add(refreshCommentsButton, c);
		
		
		c = new GridBagConstraints();
		c.gridx = 4;
		c.gridy = 0;
		c.weightx = 0.025;
		c.anchor = GridBagConstraints.LINE_END;
		c.fill = GridBagConstraints.HORIZONTAL;
		
		JButton addCommentButton = new JButton("Add comment");
		addCommentButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				CreateCommentFrame ccf = new CreateCommentFrame(party, listing, ctx);
				ccf.addWindowListener(new CommentWindowListener(ListingPanel.this));
			}
		});
		this.add(addCommentButton, c);
	}
	
	public void refreshComments() {
		try {
			this.scrollPane.setViewportView(new CommentPanel(party, this.getCommentDAO().getRootComments(this.listing), 0, ctx));
		} catch (NamingException ne) {
			ne.printStackTrace();
		}
	}
}
