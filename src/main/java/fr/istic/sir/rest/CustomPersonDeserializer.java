package fr.istic.sir.rest;

import java.io.IOException;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import domain.Person;
import jpa.EntityManagerHelper;

public class CustomPersonDeserializer extends JsonDeserializer<Person> {

	@Override
	public Person deserialize(JsonParser jsonparser, DeserializationContext context) throws IOException, JsonProcessingException {
		Long id = jsonparser.readValueAsTree().get("id").asLong();
		Person p = null;
		try {
			p = EntityManagerHelper.getEntityManager().createNamedQuery("findPersonById", Person.class).setParameter("PersonId", id).getSingleResult();
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return p;
	}
}
