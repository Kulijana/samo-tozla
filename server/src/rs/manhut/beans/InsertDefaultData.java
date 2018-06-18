package rs.manhut.beans;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import rs.manhut.entities.Listing;
import rs.manhut.entities.Party;

@Singleton
@LocalBean
@Startup
public class InsertDefaultData {
	
	@PersistenceContext(name = "OOP2_SAMO_TOZLA")
	private EntityManager em;
	
	@PostConstruct
	public void postConstruct() {
		Party p = addParty("example@gmail.com", "example", "John", "Doe", "I am an example John Doe", "");
		
		createListing(p, "Listing1", "zlato", "zuto", 200.00, "nekakva descripcija");
		createListing(p, "Listing2", "zlato", "zuto", 400.00, "nekakva descripcija");
		createListing(p, "Listing3", "srebro", "zeleno", 300.00, "nekakva descripcija");
		createListing(p, "Listing4", "srebro", "sivo", 500.00, "nekakva descripcija");
		
		em.persist(p);
	}
	
	private Party addParty(String email, String pwd, String firstName, String lastName, String desc, String avatar)
	{
		List<Party> pList = em.createNamedQuery("Party.getByEmail", Party.class)
				.setParameter("email", email)
				.getResultList();
		
		if (!pList.isEmpty())
			return pList.get(0);
		
		Party p = new Party();
		p.setEmail(email);
		p.setFirstName(firstName);
		p.setLastName(lastName);
		p.setProfilePicture(avatar);
		p.setDescription(desc);
		p.setPassword(sha1(pwd.getBytes()));
		return p;
	}
	
	
	public Listing createListing(@NotNull Party owner,
								@NotBlank String name,
								@NotBlank String material,
								@NotBlank String color,
								@NotNull Double startingPrice,
								@NotBlank String description) throws IllegalArgumentException {
		Listing l = getListingByName(name, owner);
		
		if(l != null)
			throw new IllegalArgumentException("You already have a listing with the name: " + name);
		
		l = new Listing();
		l.setActive(true);
		l.setColor(color);
		l.setDescription(description);
		l.setStartPrice(startingPrice);
		l.setMaterial(material);
		l.setOwner(owner);
		l.setName(name);
		
		em.persist(l);
		
		return l;
	}
	
	public Listing getListingByName(@NotBlank String name, @NotNull Party party) {
		List<Listing> listings = em.createNamedQuery("Listing.getByName", Listing.class)
									.setParameter("name", name)
									.setParameter("ownerId", party.getId())
									.getResultList();
		
		if(!listings.isEmpty())
			return listings.get(0);
		
		return null;
	}
	
	public static String sha1(byte[] bytes) {
	    MessageDigest md = null;
	    try {
	        md = MessageDigest.getInstance("SHA-1");
	    } catch(NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    } 
	    return new String(md.digest(bytes));
	}
}
