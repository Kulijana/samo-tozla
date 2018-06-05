package rs.manhut.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Bid
 *
 */
@Entity

public class Bid implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name="ID")
	private Long id;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@PrimaryKeyJoinColumn
	private Listing listing;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@PrimaryKeyJoinColumn
	private Party bidder;
	
	@Column(name="AMOUNT")
	private BigDecimal amount;
	
	@Column(name="WINNING")
	private boolean winning;

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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
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
   
}
