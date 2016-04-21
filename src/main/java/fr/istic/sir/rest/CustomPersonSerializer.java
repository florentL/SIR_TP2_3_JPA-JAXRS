package fr.istic.sir.rest;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import domain.Person;

public class CustomPersonSerializer extends JsonSerializer<Person>{

	@Override
	public void serialize(Person p, JsonGenerator generator, SerializerProvider provider) throws IOException, JsonProcessingException {
		if (p != null){
			generator.writeObject(p.getName() + ", " + p.getFirstName());
		}
	}

}
