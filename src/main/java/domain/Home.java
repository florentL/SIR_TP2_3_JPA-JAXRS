package domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Cascade;



@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "findAllHomes", query = "SELECT h FROM Home h"),
    @NamedQuery(name = "findHomeById", query = "SELECT h FROM Home h WHERE h.id = :HomeId"),
    @NamedQuery(name = "findEmptyHome", query = "SELECT h FROM Home h WHERE h.inhabitant is null")
})
public class Home {
	private Long id;
	private String address;
	private int size;
	private int nbRooms;
	
	private Person inhabitant;
	
	private List<Heater> heaters;
	
	public Home() {
		super();
		this.heaters = new ArrayList<Heater>();
	}
	public Home(String address, int size, int nbRooms) {
		super();
		this.address = address;
		this.size = size;
		this.nbRooms = nbRooms;
		this.heaters = new ArrayList<Heater>();
	}
	
	@Id
    @GeneratedValue
    @Column(name="HOME_ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getNbRooms() {
		return nbRooms;
	}
	public void setNbRooms(int nbRooms) {
		this.nbRooms = nbRooms;
	}
	
	@ManyToOne(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="INHABITANT_ID")
	@XmlTransient
	public Person getInhabitant() {
		return inhabitant;
	}
	public void setInhabitant(Person inhabitant) {
		this.inhabitant = inhabitant;
	}
	
	public void removeInhabitant(){
		this.inhabitant.getHomes().remove(this);
		this.setInhabitant(null);
	}
	
	@OneToMany(mappedBy="home", orphanRemoval=true, cascade=CascadeType.ALL)
	public List<Heater> getHeaters() {
		return heaters;
	}
	public void setHeaters(List<Heater> heaters) {
		this.heaters = heaters;
	}
	
	public void addHeater(Heater h) {
		h.setHome(this);
		heaters.add(h);
	}
	
}
