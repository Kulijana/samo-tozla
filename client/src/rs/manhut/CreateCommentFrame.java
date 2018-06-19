package rs.manhut;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import java.awt.Window;
//import com.sun.glass.ui.Window;

import rs.manhut.beans.CommentDAOI;
import rs.manhut.entities.Comment;
import rs.manhut.entities.Listing;
import rs.manhut.entities.Party;

public class CreateCommentFrame extends JFrame implements ActionListener {
	
	private Comment parentComment;
	private Listing listing;
	private Party party;
	private InitialContext ctx;
	
	private JTextArea commentTextArea;
	private CommentDAOI commentDAO;
	
	public CreateCommentFrame(Party party, Comment parentComment, InitialContext ctx) {
		this.ctx = ctx;
		this.parentComment = parentComment;
		this.listing = parentComment.getListing();
		this.party = party;
		
		this.setTitle("Reply to " + parentComment.getAuthor().getFirstName() + " " + parentComment.getAuthor().getLastName());
		
		createComponents();
	}
	
	public CreateCommentFrame(Party party, Listing listing, InitialContext ctx) {
		this.ctx = ctx;
		this.parentComment = null;
		this.listing = listing;
		this.party = party;
		
		this.setTitle("Comment on \"" + listing.getName() + "\" listing");
		
		createComponents();
	}
	
	private void createComponents() {
		this.setSize(400, 300);
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 10, 10, 10);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.weightx = 1;
		c.weighty = 1;
		
		commentTextArea = new JTextArea();
		commentTextArea.setText("Your comment...");
		this.add(commentTextArea, c);
		
		c = new GridBagConstraints();
		c.insets = new Insets(0, 10, 10, 0);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.2;
		c.weighty = 0.2;
		c.gridx = 0;
		c.gridy = 1;
		
		JButton cancelButton = new JButton("Cancel");
		
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				CreateCommentFrame.this.setVisible(false);
				CreateCommentFrame.this.dispose();
			}
		});
		this.add(cancelButton, c);
		
		c = new GridBagConstraints();
		c.insets = new Insets(0, 0, 10, 10);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.2;
		c.weighty = 0.2;
		c.gridx = 1;
		c.gridy = 1;
		
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(this);
		this.add(submitButton, c);
		
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(!this.commentTextArea.getText().isEmpty()) {
			try {
				if(this.parentComment != null)
					this.getCommentDAO().respondToComment(this.parentComment, this.party, this.commentTextArea.getText());
				else
					this.getCommentDAO().addRootComment(this.listing, this.party, this.commentTextArea.getText());
				
				this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
			} catch (NamingException ne) {
				ne.printStackTrace();
			}
		}
	}
	
	private CommentDAOI getCommentDAO() throws NamingException {
    	if(commentDAO == null) {
			String name = "ejb:/samo-tozla//CommentDAO!" + CommentDAOI.class.getName();
			commentDAO = (CommentDAOI) ctx.lookup(name);
    	}
    	return commentDAO;
    }
}
