package rs.manhut.beans;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;

import rs.manhut.entities.Bid;
import rs.manhut.entities.Listing;
import rs.manhut.entities.Party;

@Stateless
@Remote(BidDAOI.class)
public class BidDAO implements BidDAOI {

	@PersistenceContext(name = "OOP2_SAMO_TOZLA")
	private EntityManager em;
	
	@EJB ListingDAOI listingDAO; 
	
	@Override
	public Bid getWinningBid(@NotNull Listing listing) {
		if(listing.getId() == null)
			throw new IllegalArgumentException("Listing id cannot be null!");
		
		List<Bid> bids = em.createNamedQuery("Bid.getWinningBid", Bid.class)
							.setParameter("listingId", listing.getId())
							.getResultList();
		
		if(!bids.isEmpty())
			return bids.get(0);
		
		return null;
	}

	@Override
	public List<Bid> getPartyBids(@NotNull Party party) {
		if(party.getId() == null)
			throw new IllegalArgumentException("Party id cannot be null!");
		
		return em.createNamedQuery("Bid.getPartyBids", Bid.class)
							.setParameter("partyId", party.getId())
							.getResultList();
	}

	@Override
	public List<Bid> getListingBids(@NotNull Listing listing) {
		if(listing.getId() == null)
			throw new IllegalArgumentException("Listing id cannot be null");
		
		return em.createNamedQuery("Bid.getBidsByListing", Bid.class)
				.setParameter("listingId", listing.getId())
				.getResultList();
	}

	@Override
	public Bid makeBid(@NotNull Listing listing, @NotNull  Party party, @NotNull Double amount) {
		if(listing.getId() == null || party.getId() == null) {
			throw new IllegalArgumentException("Listing and party ids cannot be null");
		}
		
		Listing l = listingDAO.getListingById(listing.getId());
		
		if(!l.getActive())
			return null;
	
		if(l.getOwner().getId().equals(party.getId()))
			return null;
		
		List<Bid> bidList = this.getListingBids(listing);
		if(!bidList.isEmpty() && bidList.get(0).getAmount() >= amount)
			return null;
		
		Bid bid = new Bid();
		bid.setAmount(amount);
		bid.setListing(l);
		bid.setBidder(party);
		bid.setWinning(false);
		bid.setCreatedOn(new Date());
		
		em.persist(bid);
		return bid;
	}

	@Override
	public Bid setWinningBid(Listing listing) {
		List<Bid> bids = this.getListingBids(listing);
		
		if(!bids.isEmpty()) {
			Bid bid = bids.get(0);
			bid.setWinning(true);
			em.merge(bid);
			
			return bid;
		}
		return null;
	}
}
