package rs.manhut;

import java.util.List;

import javax.naming.InitialContext;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import rs.manhut.entities.Comment;
import rs.manhut.entities.Party;

public class CommentPanel extends JPanel {
	
	public CommentPanel(Party party, List<Comment> comments, int depth, InitialContext ctx) {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
	
		for(Comment c : comments) {
			CommentComponent cc = new CommentComponent(party, c, depth, ctx);
			this.add(cc);
		}
	}
}
