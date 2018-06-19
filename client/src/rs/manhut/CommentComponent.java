package rs.manhut;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.StrokeBorder;

import rs.manhut.beans.CommentDAOI;
import rs.manhut.entities.Comment;
import rs.manhut.entities.Party;

public class CommentComponent extends JPanel implements ActionListener {
	
	private Comment comment;
	private CommentDAOI commentDAO;
	private Party party;
	private InitialContext ctx;
	private JPanel childCommentPanel;
	
	private static Color color1 = new Color(253, 253, 253);
	private static Color color2 = new Color(237, 250, 255);
	
	private int depth;
	
	public CommentComponent(Party party, Comment comment, int depth, InitialContext ctx) {
		this.comment = comment;
		this.ctx = ctx;
		this.depth = depth;
		this.party = party;
		
		this.setMaximumSize(new Dimension(3000, 200));
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		if(this.depth % 2 == 0) {
			this.setBackground(color1);
		} else {
			this.setBackground(color2);
		}
		
		this.add(createCommentPane());
		
		try {
			List<Comment> childComments = this.getCommentDAO().getChildComments(this.comment);
			childCommentPanel = new CommentPanel(this.party, childComments, this.depth + 1, ctx);
			childCommentPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
			this.add(childCommentPanel);
		} catch (NamingException ne) {
			ne.printStackTrace();
		}
	}
	
	private JPanel createCommentPane() {
		JPanel pane = new JPanel();
		
		pane.setLayout(new GridBagLayout());
		pane.setBorder(new StrokeBorder(new BasicStroke(1), Color.LIGHT_GRAY));
		
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel authorLabel = new JLabel(this.comment.getAuthor().getFirstName() + " " + this.comment.getAuthor().getLastName());
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.LINE_START;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.weightx = 0.8;
		c.weighty = 0.1;
		
		pane.add(authorLabel, c);
		
		
		c = new GridBagConstraints();
		JTextArea commentLabel = new JTextArea(this.comment.getText());
		commentLabel.setEditable(false);
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.weightx = 0.8;
		c.weighty = 0.4;
		
		if(this.depth % 2 == 0) {
			commentLabel.setBackground(color1);
			pane.setBackground(color1);
		} else {
			commentLabel.setBackground(color2);
			pane.setBackground(color2);
		}
		
		pane.add(commentLabel, c);
		
		c = new GridBagConstraints();
		JLabel dateTimeLabel = new JLabel(this.getCommentDate());
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.weightx = 0.8;
		c.weighty = 0.1;
		
		pane.add(dateTimeLabel, c);
		
		if(!comment.getAuthor().getId().equals(this.party.getId())) {
			c = new GridBagConstraints();
			JButton button = new JButton("Reply");
			c.fill = GridBagConstraints.BOTH;
			c.anchor = GridBagConstraints.CENTER;
			c.gridx = 1;
			c.gridy = 2;
			c.weightx = 0.1;
			c.weighty = 0.1;
			
			button.addActionListener(this);
			
			pane.add(button, c);
		}
		
		return pane;
	}
	
	private String getCommentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		return sdf.format(this.comment.getCreatedOn());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		CreateCommentFrame ccf = new CreateCommentFrame(party, this.comment, ctx);
		ccf.addWindowListener(new CommentWindowListener(this));
	}
	
	private CommentDAOI getCommentDAO() throws NamingException {
    	if(commentDAO == null) {
			String name = "ejb:/samo-tozla//CommentDAO!" + CommentDAOI.class.getName();
			commentDAO = (CommentDAOI) ctx.lookup(name);
    	}
    	return commentDAO;
    }
	
	public void refreshChildComments() {
		System.out.println("TRIED TO REFRESH");
		try {
			List<Comment> childComments = this.getCommentDAO().getChildComments(this.comment);
			CommentPanel childCommentPanel = new CommentPanel(this.party, childComments, this.depth + 1, ctx);
			childCommentPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
			this.add(childCommentPanel);
			this.validate();
			this.repaint();
		} catch (NamingException ne) {
			ne.printStackTrace();
		}
	}
}
