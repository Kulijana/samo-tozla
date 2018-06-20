package rs.manhut.beans;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import rs.manhut.entities.Bid;
import rs.manhut.entities.Listing;
import rs.manhut.entities.Party;
//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;

@Stateless
@Remote(ListingDAOI.class)
public class ListingDAO implements ListingDAOI {

	
	@PersistenceContext(name = "OOP2_SAMO_TOZLA")
	private EntityManager em;
	
	@EJB BidDAOI bidDAO;
	
	@Override
	public Listing createListing(@NotNull Party owner,
								@NotBlank String name,
								@NotBlank String material,
								@NotBlank String type,
								@NotBlank String color,
								@NotNull Double startingPrice,
								@NotBlank String description,
								String image) {
		Listing l = getListingByName(name, owner);
		
		if(l != null)
			return null;
		
		l = new Listing();
		l.setActive(true);
		l.setColor(color);
		l.setDescription(description);
		l.setStartPrice(startingPrice);
		l.setMaterial(material);
		l.setOwner(owner);
		l.setName(name);
		l.setType(type);
		l.setImage(image);
		
		em.persist(l);
		
		return l;
	}


	@Override
	public Listing getListingById(@NotNull Long id) {
		List<Listing> listings = em.createNamedQuery("Listing.getById", Listing.class)
									.setParameter("id", id)
									.getResultList();
		if(!listings.isEmpty())
			return listings.get(0);
		
		return null;
	}

	@Override
	public Listing getListingByName(@NotBlank String name, @NotNull Party party) {
		List<Listing> listings = em.createNamedQuery("Listing.getByName", Listing.class)
									.setParameter("name", name)
									.setParameter("ownerId", party.getId())
									.getResultList();
		
		if(!listings.isEmpty())
			return listings.get(0);
		
		return null;
	}

	@Override
	public List<Listing> getAllListings(String name,
										String material,
										String color,
										Boolean active) {
		String nameExp;
		if(name == null)
			nameExp = name;
		else
			nameExp = "%" + name + "%";
		
		return em.createNamedQuery("Listing.getAll", Listing.class)
									.setParameter("material", material)
									.setParameter("active", active)
									.setParameter("color", color)
									.setParameter("name", nameExp)
									.getResultList();
	}

	@Override
	public List<Listing> getPartyListings(@NotNull Party party) {
		return em.createNamedQuery("Listing.getByParty", Listing.class)
				.setParameter("ownerId", party.getId())
				.getResultList();
	}


	@Override
	public Bid stopAuction(Listing listing, Party party) {
		Listing l = getListingById(listing.getId());
		
		if(l.getOwner().getId().equals(party.getId())) {
			l.setActive(false);
			em.merge(l);
			
			Bid bid = bidDAO.setWinningBid(l);
			return bid;
		}
		
		return null;
	}
}
