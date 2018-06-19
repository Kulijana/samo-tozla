package rs.manhut.beans;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import rs.manhut.entities.Comment;
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
		
		em.persist(p);
		
		Listing l = createListing(p, "Listing1", "zlato", "zuto", 200.00, "nekakva descripcija");
		createListing(p, "Listing2", "zlato", "zuto", 400.00, "nekakva descripcija");
		createListing(p, "Listing3", "srebro", "zeleno", 300.00, "nekakva descripcija");
		createListing(p, "Listing4", "srebro", "sivo", 500.00, "nekakva descripcija");
		
		Comment c1 = addRootComment(l, p, "Somee comment \n number 1 \n whatever");
		addRootComment(l, p, "Somee comment \n number 2 \n whatever");
		Comment c3 = addRootComment(l, p, "Somee comment \n number 3 \n whatever");
		addRootComment(l, p, "Somee comment \n number 4 \n whatever");
		
		Comment c12 = respondToComment(c1, p, "This is a first response");
		Comment c13 = respondToComment(c1, p, "This is a first response 2");
		Comment c32 = respondToComment(c3, p, "This is a first response");

		respondToComment(c12, p, "LETS HOPE THIS WORKS GOOD");
	}
	
	public Comment addRootComment(@NotNull Listing listing, @NotNull Party party, @NotBlank String commentStr) {
		Comment comment = new Comment();
		comment.setText(commentStr);
		comment.setListing(listing);
		comment.setParentComment(null);
		comment.setAuthor(party);
		comment.setRead(false);
		comment.setCreatedOn(new Date());
		
		em.persist(comment);
		
		return comment;
	}
	
	
	public Comment respondToComment(@NotNull Comment parentComment, @NotNull Party party, @NotBlank String commentStr) {
		Comment comment = new Comment();
		comment.setText(commentStr);
		comment.setListing(parentComment.getListing());
		comment.setAuthor(party);
		comment.setParentComment(parentComment);
		comment.setRead(false);
		comment.setCreatedOn(new Date());
		
		em.persist(comment);
		
		return comment;
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
			return l;
		
		l = new Listing();
		l.setActive(true);
		l.setColor(color);
		l.setDescription(description);
		l.setStartPrice(startingPrice);
		l.setMaterial(material);
		l.setOwner(owner);
		l.setName(name);
		
		System.out.println(l.getOwner().getId());
		
		em.merge(l);
		
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
