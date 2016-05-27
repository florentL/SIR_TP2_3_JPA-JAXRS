package domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class MyCommand {

	private Long id;
	
	private String customId;
	
	public MyCommand () {
	}
	
	public MyCommand(String nom) {
		this.customId = nom;

	}
	
	@Id
	@GeneratedValue
	@Column(name="AUTOGENERATE_ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@Column(unique=true)
	public String getCustomId() {
		return customId;
	}

	public void setCustomId(String customId) {
		this.customId = customId;
	}

	public String toString() {
		return "Order : "+ customId ;
	}
}
