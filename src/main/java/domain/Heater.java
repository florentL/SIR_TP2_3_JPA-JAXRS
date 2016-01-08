package domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Heater {
	private Long id;
	private int consumption;
	private Home home;
	
	public Heater(int consumption) {
		super();
		this.consumption = consumption;
	}
	@Id
    @GeneratedValue
    @Column(name="HEATER_ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getConsumption() {
		return consumption;
	}
	public void setConsumption(int consumption) {
		this.consumption = consumption;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="HOME_ID")
	public Home getHome() {
		return home;
	}
	public void setHome(Home home) {
		this.home = home;
	}
	
	
}
