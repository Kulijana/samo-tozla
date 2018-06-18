package rs.manhut.beans;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import rs.manhut.entities.Listing;
import rs.manhut.entities.Party;

public interface ListingDAOI {
	
	public Listing createListing(@NotNull Party owner,
								@NotBlank String name,
								@NotBlank String material,
								@NotBlank String color,
								@NotNull Double startingPrice,
								@NotBlank String description);
	
	public Listing getListingById(@NotNull Long id);
	
	public Listing getListingByName(@NotBlank String name, @NotNull Party party);
	
	public List<Listing> getAllListings(String name,
										String material,
										String color,
										Boolean active);
	
	public List<Listing> getPartyListings(@NotNull Party party);
}
