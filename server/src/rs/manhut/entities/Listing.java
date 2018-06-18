package rs.manhut.entities;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Listing
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "Listing.getById", query = "SELECT l FROM Listing l WHERE l.id = :id"),
	@NamedQuery(name = "Listing.getByName", query = "SELECT l FROM Listing l WHERE l.name LIKE :name AND l.owner.id = :ownerId"),
	@NamedQuery(name = "Listing.getByParty", query = "SELECT l FROM Listing l WHERE l.owner.id = :ownerId"),
	@NamedQuery(name = "Listing.getAll", query = "SELECT l FROM Listing l WHERE (:material IS NULL OR l.material LIKE :material) AND "
												+ "(:color IS NULL OR l.color LIKE :color) AND "
												+ "(:active IS NULL OR l.active = :active) AND "
												+ "(:name IS NULL OR (l.name LIKE :name))")
})
public class Listing implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name="ID")
	private Long id;
	
	@Column(name="NAME")
	private String name;
	
	@OneToOne
    @JoinColumn(name = "owner_id")
	private Party owner;
	
	@Column(name="ACTIVE")
	private Boolean active;
	
	@Column(name="MATERIAL")
	private String material;
	
	@Column(name="COLOR")
	private String color;
	
	@Column(name="START_PRICE")
	private Double startPrice;
	
	@Column(name="DESC")
	private String description;

	public Listing() {
		super();
	}

	public Party getOwner() {
		return owner;
	}

	public void setOwner(Party owner) {
		this.owner = owner;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Double getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(Double startPrice) {
		this.startPrice = startPrice;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
   
}
