package rs.manhut.beans;

import rs.manhut.entities.Party;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;


@Stateless
@Remote(PartyDAOI.class)
public class PartyDAO implements PartyDAOI {
	
	@PersistenceContext(name = "OOP2_SAMO_TOZLA")
	private EntityManager em;
	
	public Party getParty(@NotNull Long id) {
		List<Party> pList = em.createNamedQuery("Party.getById", Party.class)
				.setParameter("id", id)
				.getResultList();
		
		if (!pList.isEmpty())
			return pList.get(0);
		else
			return null;
	}
	
	public Party getParty(@NotBlank String email) {
		List<Party> pList = em.createNamedQuery("Party.getByEmail", Party.class)
				.setParameter("email", email)
				.getResultList();
		
		if (!pList.isEmpty())
			return pList.get(0);
		else
			return null;
	}
	
	public Party login(@NotBlank String email, @NotBlank String password) {
		List<Party> results = em.createNamedQuery("Party.login", Party.class)
													.setParameter("email", email)
													.setParameter("password", sha1(password.getBytes()))
													.getResultList();
		
		if(!results.isEmpty())
			return results.get(0);
		
		return null;
	}
	
	public Party register(@NotBlank String email,
					@NotBlank String password, 
					@NotBlank String firstName, 
					@NotBlank String lastName, 
					@NotBlank String description, 
					@NotBlank String profilePicture) throws IllegalArgumentException {
		Party p = getParty(email);
		if(p != null)
			throw new IllegalArgumentException("User with email \"" + email + "\" already exists.");
		
		p = new Party();	
		p.setEmail(email);
		p.setFirstName(firstName);
		p.setLastName(lastName);
		p.setPassword(sha1(password.getBytes()));
		p.setDescription(description);
		p.setProfilePicture(profilePicture);
		
		em.persist(p);
		return p;
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
