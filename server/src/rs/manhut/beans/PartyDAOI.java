package rs.manhut.beans;

import rs.manhut.entities.Party;

public interface PartyDAOI {
	

	Party getParty(Long id);
	
	Party getParty(String email);
	
	boolean login(String email, String password);
	
	boolean register(String email,
					String password, 
					String firstname, 
					String lastname, 
					String description, 
					String avatar);
}
