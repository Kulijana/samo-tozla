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
