package rs.manhut.beans;

import java.util.List;

import rs.manhut.entities.Comment;
import rs.manhut.entities.Listing;
import rs.manhut.entities.Party;

public interface CommentDAOI {
	
	public void addComment(Listing listing, Party party, String comment);
	
	public List<Comment> getListingComments(Listing listing);
	
	public List<Comment> getPartyComments(Party party);
}
