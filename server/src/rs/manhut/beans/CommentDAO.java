package rs.manhut.beans;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import rs.manhut.entities.Comment;
import rs.manhut.entities.Listing;
import rs.manhut.entities.Party;

@Stateless
@Remote(CommentDAOI.class)
public class CommentDAO implements CommentDAOI {
	
	@PersistenceContext(name = "OOP2_SAMO_TOZLA")
	private EntityManager em;
	
	@Override
	public Comment addRootComment(@NotNull Listing listing, @NotNull Party party, @NotBlank String commentStr) {
		Comment comment = new Comment();
		comment.setText(commentStr);
		comment.setListing(listing);
		comment.setParentComment(null);
		comment.setAuthor(party);
		comment.setRead(false);
		comment.setCreatedOn(new Date());
		
		em.persist(comment);
		
		return comment;
	}
	
	@Override
	public Comment respondToComment(@NotNull Comment parentComment, @NotNull Party party, @NotBlank String commentStr) {
		Comment comment = new Comment();
		comment.setText(commentStr);
		comment.setListing(parentComment.getListing());
		comment.setAuthor(party);
		comment.setParentComment(parentComment);
		comment.setRead(false);
		comment.setCreatedOn(new Date());
		
		em.persist(comment);
		
		return comment;
	}

	@Override
	public List<Comment> getListingComments(@NotNull Listing listing) {
		if(listing.getId() != null) {
			return em.createNamedQuery("Comment.getByListing", Comment.class)
						.setParameter("listingId", listing.getId())
						.getResultList();
		} else {
			return null;
		}
	}

	@Override
	public List<Comment> getPartyComments(@NotNull Party author) {
		if(author.getId() != null) {
			return em.createNamedQuery("Comment.getByAuthor", Comment.class)
					.setParameter("authorId", author.getId())
					.getResultList();
		} else {
			throw new IllegalArgumentException("Party id is null.");
		}
	}

	@Override
	public List<Comment> getRootComments(Listing listing) {
		if(listing.getId() != null) {
			return em.createNamedQuery("Comment.getRootComments", Comment.class)
					.setParameter("listingId", listing.getId())
					.getResultList();
		} else {
			return null;
		}
	}

	@Override
	public List<Comment> getChildComments(Comment parentComment) {
		if(parentComment.getId() != null) {
			return em.createNamedQuery("Comment.getChildComments", Comment.class)
					.setParameter("parentCommentId", parentComment.getId())
					.getResultList();
		} else {
			return null;
		}
	}

}
