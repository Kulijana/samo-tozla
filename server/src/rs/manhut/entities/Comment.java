package rs.manhut.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Comment
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "Comment.getByAuthor", query = "SELECT c FROM Comment c WHERE c.author.id = :authorId"),
	@NamedQuery(name = "Comment.getByListing", query = "SELECT c FROM Comment c WHERE c.listing.id = :listingId"),
	@NamedQuery(name = "Comment.getRootComments", query = "SELECT c FROM Comment c WHERE c.listing.id = :listingId AND c.parentComment IS NULL"),
	@NamedQuery(name = "Comment.getChildComments", query = "SELECT c FROM Comment c WHERE c.parentComment.id = :parentCommentId")
})
public class Comment implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name="ID")
	private Long id;
	
	@OneToOne
    @JoinColumn(name = "author_id")
	private Party author;
	
	@OneToOne
    @JoinColumn(name = "listing_id")
	private Listing listing;
	
	@OneToOne
    @JoinColumn(name = "parent_id")
	private Comment parentComment;
	
	@Column(name="TEXT")
	private String text;
	
	@Column(name="READ")
	private boolean read;
	
	@Column(name="CREATED_ON")
	private Date createdOn;
	
	public Comment() {
		super();
	}

	public Party getAuthor() {
		return author;
	}

	public void setAuthor(Party author) {
		this.author = author;
	}

	public Listing getListing() {
		return listing;
	}

	public void setListing(Listing listing) {
		this.listing = listing;
	}

	public Comment getParentComment() {
		return parentComment;
	}

	public void setParentComment(Comment parentComment) {
		this.parentComment = parentComment;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
}
