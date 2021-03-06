package rs.manhut.beans;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.validation.constraints.NotNull;


import org.hibernate.validator.constraints.NotBlank;

import rs.manhut.entities.Bid;
import rs.manhut.entities.Listing;
import rs.manhut.entities.Party;

public interface ListingDAOI {
	
	public Listing createListing(@NotNull Party owner,
								@NotBlank String name,
								@NotBlank String material,
								@NotBlank String type,
								@NotBlank String color,
								@NotNull Double startingPrice,
								@NotBlank String description,
								String image);
	
	public Listing getListingById(@NotNull Long id);
	
	public Listing getListingByName(@NotBlank String name, @NotNull Party party);
	
	public List<Listing> getAllListings(String name,
										String material,
										String color,
										String type,
										Boolean active);
	
	public List<Listing> getPartyListings(@NotNull Party party);
	
	public Bid stopAuction(Listing listing, Party party);
}
