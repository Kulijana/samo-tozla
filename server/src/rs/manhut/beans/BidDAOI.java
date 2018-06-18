package rs.manhut.beans;

import java.math.BigDecimal;
import java.util.List;

import rs.manhut.entities.Bid;
import rs.manhut.entities.Listing;
import rs.manhut.entities.Party;

public interface BidDAOI {
	
	public Bid getWinningBid(Listing listing);
	
	public List<Bid> getPartyBids(Party party);
	
	public List<Bid> getListingBids(Listing listing);
	
	public void makeBid(Listing listing, Party party, BigDecimal ammount);
	
}
