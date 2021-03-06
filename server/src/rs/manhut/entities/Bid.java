package rs.manhut.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Bid
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "Bid.getBidsByListing", query = "SELECT b FROM Bid b WHERE b.listing.id = :listingId ORDER BY b.amount DESC"),
	@NamedQuery(name = "Bid.getWinningBid", query = "SELECT b FROM Bid b WHERE b.listing.id = :listingId AND b.winning = true"),
	@NamedQuery(name = "Bid.getPartyBids", query = "SELECT b FROM Bid b WHERE b.bidder.id = :partyId")
})
public class Bid implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name="ID")
	private Long id;
	
	@OneToOne
    @JoinColumn(name = "listing_id")
	private Listing listing;
	
	@OneToOne
    @JoinColumn(name = "bidder_id")
	private Party bidder;
	
	@Column(name="AMOUNT")
	private Double amount;
	
	@Column(name="WINNING")
	private boolean winning;
	
	@Column(name="CREATED_ON")
	private Date createdOn; 

	public Bid() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Listing getListing() {
		return listing;
	}

	public void setListing(Listing listing) {
		this.listing = listing;
	}

	public Party getBidder() {
		return bidder;
	}

	public void setBidder(Party bidder) {
		this.bidder = bidder;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public boolean isWinning() {
		return winning;
	}

	public void setWinning(boolean winning) {
		this.winning = winning;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
	
   
}
