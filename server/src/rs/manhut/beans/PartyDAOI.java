package rs.manhut.beans;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import rs.manhut.entities.Party;

public interface PartyDAOI {
	

	Party getParty(@NotNull Long id);
	
	Party getParty(@NotBlank String email);
	
	Party login(String email, String password);
	
	Party register(@NotBlank String email,
					@NotBlank String password, 
					@NotBlank String firstname, 
					@NotBlank String lastname, 
					@NotBlank String description, 
					@NotBlank String avatar);
}
