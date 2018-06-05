package rs.manhut.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Comment
 *
 */
@Entity
public class Comment implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name="ID")
	private Long id;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@PrimaryKeyJoinColumn
	private Party author;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@PrimaryKeyJoinColumn
	private Listing listing;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@PrimaryKeyJoinColumn
	private Comment parentComment;
	
	@Column(name="TEXT")
	private String text;
	
	@Column(name="READ")
	private boolean read;
	
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
   
	
}
