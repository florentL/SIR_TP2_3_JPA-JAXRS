package domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Person {

	private Long id;
	private String name;
	private String firstName;
	
	private String mail;
	private List<Home> homes;
	
	public Person() {
		super();
		this.homes = new ArrayList<Home>();
	}

	public Person(String name, String firstName, String mail, List<Home> homes) {
		super();
		this.name = name;
		this.firstName = firstName;
		this.mail = mail;
		this.homes = homes;
	}
	
	@Id
    @GeneratedValue
	@Column(name="PERSON_ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@OneToMany(mappedBy="inhabitant")
	public List<Home> getHomes() {
		return homes;
	}


	public void setHomes(List<Home> homes) {
		this.homes = homes;
	}


	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	

	@Override
    public String toString() {
        return "Person [id=" + id + ", name=" + name + ", first name=" + firstName + "]";
    }

	
}
