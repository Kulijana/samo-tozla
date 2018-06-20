package rs.manhut.beans;

import java.util.List;

import javax.validation.constraints.NotNull;

import rs.manhut.entities.Bid;
import rs.manhut.entities.Listing;
import rs.manhut.entities.Party;

public interface BidDAOI {
	
	public Bid getWinningBid(@NotNull Listing listing);
	
	public List<Bid> getPartyBids(@NotNull Party party);
	
	public List<Bid> getListingBids(@NotNull Listing listing);
	
	public Bid makeBid(@NotNull Listing listing, @NotNull Party party, @NotNull Double amount);
}
