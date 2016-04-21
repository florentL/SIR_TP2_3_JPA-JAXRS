package domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "findAllHeaters", query = "SELECT h FROM Heater h"),
    @NamedQuery(name = "findHeaterById", query = "SELECT h FROM Heater h WHERE h.id = :HeaterId")
})
public class Heater extends SmartDevice{
	private Home home;
	
	public Heater() {
		super();
	}

	public Heater(int consumption) {
		super(consumption);
	}
	
	@ManyToOne(fetch=FetchType.EAGER, cascade={CascadeType.PERSIST})
	@JoinColumn(name="HOME_ID")
	@XmlTransient
	public Home getHome() {
		return home;
	}
	public void setHome(Home home) {
		this.home = home;
	}
	
	
}
