package rs.manhut.beans;

import rs.manhut.entities.Party;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Stateless
@Remote(PartyDAOI.class)
public class PartyDAO implements PartyDAOI {
	
	@javax.persistence.PersistenceContext(name = "OOP2_SAMO_TOZLA")
	private EntityManager em;
	
	public Party getParty(Long id) {
		if(id != null) {
			List<Party> pList = em.createNamedQuery("Party.getById", Party.class)
					.setParameter("id", id)
					.getResultList();
			
			if (!pList.isEmpty())
				return pList.get(0);
			else
				return null;
		}
		return null;
	}
	
	public Party getParty(String email) {
		if(email != null) {
			List<Party> pList = em.createNamedQuery("Party.getByEmail", Party.class)
					.setParameter("email", email)
					.getResultList();
			
			if (!pList.isEmpty())
				return pList.get(0);
			else
				return null;
		}
		return null;
	}
	
	public boolean login(String email, String password) {
		return false;
	}
	
	public boolean register(String email,
					String password, 
					String firstName, 
					String lastName, 
					String description, 
					String profilePicture) throws IllegalArgumentException {
		Party p = getParty(email);
		if(p != null)
			throw new IllegalArgumentException("User with email \"" + email + "\" already exists.");
		
		p.setEmail(email);
		p.setFirstName(firstName);
		p.setLastName(lastName);
		p.setPassword(sha1(password.getBytes()));
		p.setDescription(description);
		p.setProfilePicture(profilePicture);
		
		em.persist(p);
		return true;
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
