package domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlRootElement;



@Entity
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
