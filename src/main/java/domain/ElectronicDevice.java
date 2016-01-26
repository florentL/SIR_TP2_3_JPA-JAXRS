package domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
public class ElectronicDevice extends SmartDevice{
	private Person person;
	
	
	public ElectronicDevice() {
		// TODO Auto-generated constructor stub
	}
	
	public ElectronicDevice(int consumption) {
		super(consumption);
	}
	
	@ManyToOne(fetch=FetchType.LAZY, cascade={CascadeType.PERSIST})
	@JoinColumn(name="PERSON_ID")
	@XmlTransient

	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	
}
