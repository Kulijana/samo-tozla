package rs.manhut;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.naming.InitialContext;
import javax.naming.NamingException;
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
	
	public ListingPanel(Party party, Listing l, InitialContext ctx) {
		listing = l;
		this.ctx = ctx;
		this.party = party;
		
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 6;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.2;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.CENTER;
		
		JPanel imagePanel = new JPanel();
		imagePanel.setBackground(Color.BLUE);
		this.add(imagePanel, c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 0.2;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.LINE_START;
		
		JLabel nameLabel = new JLabel();
		nameLabel.setText(listing.getName());
		
		this.add(nameLabel, c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 0.2;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.LINE_START;
		
		JLabel ownerLabel = new JLabel();
		ownerLabel.setText(listing.getOwner().getFirstName() + " " + listing.getOwner().getLastName());
		
		this.add(ownerLabel, c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 4;
		c.gridheight = 2;
		c.weightx = 0.2;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		
		JTextArea descTextArea = new JTextArea();
		descTextArea.setText("Description:\n" + listing.getDescription());
		
		this.add(descTextArea, c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 2;
		c.weightx = 0.2;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.BOTH;
		JPanel panel = new JPanel();
		panel.setBackground(Color.RED);
		this.add(panel, c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 3;
		c.weightx = 0.2;
		c.weighty = 0.1;
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.BOTH;
		panel = new JPanel();
		panel.setBackground(Color.YELLOW);
		this.add(panel, c);
		
		this.createCommentScrollPane();
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 2;
		c.weighty = 0.6;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.BOTH;
		panel = new JPanel();
		panel.setBackground(Color.BLACK);
		this.add(panel, c);
	}
	
	private CommentDAOI getCommentDAO() throws NamingException {
    	if(commentDAO == null) {
			String name = "ejb:/samo-tozla//CommentDAO!" + CommentDAOI.class.getName();
			commentDAO = (CommentDAOI) ctx.lookup(name);
    	}
    	return commentDAO;
    }
	
	private void createCommentScrollPane() {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 2;
		c.gridy = 0;
		c.gridheight = 7;
		c.weightx = 0.3;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.BOTH;
		
		
		try {
			JScrollPane scrollPane = new JScrollPane(new CommentPanel(party, this.getCommentDAO().getRootComments(this.listing), 0, ctx));
			scrollPane.getVerticalScrollBar().setUnitIncrement(16);
			this.add(scrollPane, c);
		} catch (NamingException ne) {
			ne.printStackTrace();
		}
	}
	
}
