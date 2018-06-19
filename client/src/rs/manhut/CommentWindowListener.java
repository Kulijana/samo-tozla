package rs.manhut;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class CommentWindowListener implements WindowListener {

	private ListingPanel listingPanel;
	private CommentComponent commentComponent;
	
	public CommentWindowListener(ListingPanel lp) {
		super();
		listingPanel = lp;
	}
	
	public CommentWindowListener(CommentComponent commentComponent) {
		super();
		this.commentComponent = commentComponent;
	}
	
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		if(this.listingPanel != null)
			listingPanel.refreshComments();
		if(this.commentComponent != null)
			commentComponent.refreshChildComments();
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

}
