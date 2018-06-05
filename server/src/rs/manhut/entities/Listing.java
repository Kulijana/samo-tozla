package rs.manhut.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Listing
 *
 */
@Entity
public class Listing implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name="ID")
	private Long id;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@PrimaryKeyJoinColumn
	@Column(name="OWNER")
	private Party owner;
	
	@Column(name="ACTIVE")
	private Boolean active;
	
	@Column(name="MATERIAL")
	private String material;
	
	@Column(name="COLOR")
	private String color;
	
	@Column(name="START_PRICE")
	private BigDecimal startPrice;
	
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

	public BigDecimal getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(BigDecimal startPrice) {
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
	
	
   
}
