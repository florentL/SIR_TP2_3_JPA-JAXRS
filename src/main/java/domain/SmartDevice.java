package domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Cascade;



//@Entity // Crée une seule table SmartDevice avec des ElectronicDevice et des Heater (classes fille)
@MappedSuperclass // Ne crée pas de table pour SmartDevice mais crée 2 tables ED et Heater (classes fille)
public abstract class SmartDevice {
	private Long id;
	private int consumption;
	
	public SmartDevice(){
	}
	
	public SmartDevice(int consumption) {
		this.consumption = consumption;
	}
	
	@Id
    @GeneratedValue
    @Column(name="DEVICE_ID")
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
	
}
