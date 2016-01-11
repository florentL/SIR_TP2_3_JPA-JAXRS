package domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ElectronicDevice {

	private Long id;
	private int consumption;
	private Person person;
	
	public ElectronicDevice(int consumption) {
		super();
		this.consumption = consumption;
	}
	@Id
    @GeneratedValue
    @Column(name="ED_ID")
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
	@JoinColumn(name="PERSON_ID")
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	
}
