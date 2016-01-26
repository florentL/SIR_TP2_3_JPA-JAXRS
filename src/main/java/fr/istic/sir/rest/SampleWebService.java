package fr.istic.sir.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import domain.Heater;
import domain.Home;
import domain.Person;
import jpa.EntityManagerHelper;

@Path ( "/home" )
public class SampleWebService {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Home> getHome() {
		List<Home> homes = EntityManagerHelper.getEntityManager().createNamedQuery("findAllHomes",Home.class).getResultList();
		
		return homes ;
	}
}