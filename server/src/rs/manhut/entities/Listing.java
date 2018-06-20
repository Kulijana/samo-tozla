package rs.manhut.entities;

import java.awt.Color;
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
												+ "(:type IS NULL OR l.type = :type) AND "
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
	@Enumerated(EnumType.STRING)
	private MaterialType material;
	
	@Column(name="COLOR")
	@Enumerated(EnumType.STRING)
	private JewelryColor color;
	
	@Column(name="JEWELRY_TYPE")
	@Enumerated(EnumType.STRING)
	private JewelryType type;
	
	@Column(name="START_PRICE")
	private Double startPrice;
	
	@Column(name="DESC")
	private String description;
	
	@Lob
	private String image;

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

	public MaterialType getMaterial() {
		return material;
	}

	public void setMaterial(MaterialType material) {
		this.material = material;
	}
	
	public void setMaterial(String material) {
		this.material = MaterialType.valueOf(material);
	}

	public JewelryColor getColor() {
		return color;
	}

	public void setColor(JewelryColor color) {
		this.color = color;
	}
	
	public void setColor(String color) {
		this.color = JewelryColor.valueOf(color);
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
	
	public JewelryType getType() {
		return type;
	}

	public void setType(JewelryType type) {
		this.type = type;
	}
	
	public void setType(String type) {
		this.type = JewelryType.valueOf(type);
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}



	public enum JewelryColor {
		YELLOW(255, 236, 94),
		RED(255, 75, 15),
		BLACK(40, 40, 40),
		GREY(160, 160, 160),
		GREEN(37, 221, 80),
		BLUE(94, 190, 255),
		PURPLE(155, 112, 255),
		PINK(255, 181, 250);
		
		private final int r;
	    private final int g;
	    private final int b;
	    private final String rgb;

	    private JewelryColor(final int r,final int g,final int b) {
	        this.r = r;
	        this.g = g;
	        this.b = b;
	        this.rgb = r + ", " + g + ", " + b;
	    }

	    public String getRGB() {
	        return rgb;
	    }

	    public Color getColor(){
	        return new Color(r,g,b);
	    }
	}
	
	public enum MaterialType {
		GOLD,
		SILVER,
		METAL,
		PLASTIC,
		PEARL,
		STONE
	}
   
	public enum JewelryType {
		RING,
		NECKLACE,
		EARRING,
		BRACELET,
		WATCH,
		BROOCH,
		ANKLET
	}
}
