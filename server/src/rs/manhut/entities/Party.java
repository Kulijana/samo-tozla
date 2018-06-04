package rs.manhut.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;

@Entity
@NamedQueries({
	
})
public class Party {
	@Id
	int id;
	String username;
	
}
