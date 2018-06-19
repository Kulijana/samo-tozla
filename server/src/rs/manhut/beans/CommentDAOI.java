package rs.manhut.beans;

import java.util.List;

import javax.validation.constraints.NotNull;


import org.hibernate.validator.constraints.NotBlank;

import rs.manhut.entities.Comment;
import rs.manhut.entities.Listing;
import rs.manhut.entities.Party;

public interface CommentDAOI {
	
	// Add a root comment on a listing
	public Comment addRootComment(@NotNull Listing listing, @NotNull Party party, @NotBlank String commentStr);
	
	// Respond to an existing comment
	public Comment respondToComment(@NotNull Comment parentComment, @NotNull Party party, @NotBlank String commentStr);
	
	// Get all comments on a listing
	public List<Comment> getListingComments(@NotNull Listing listing);
	
	// Get all comments made by a single party
	public List<Comment> getPartyComments(@NotNull Party party);
	
	// Get only root comments (no parent) on a listing
	public List<Comment> getRootComments(@NotNull Listing listing);
	
	// Get child comments for a comment
	public List<Comment> getChildComments(@NotNull Comment comment);
}
