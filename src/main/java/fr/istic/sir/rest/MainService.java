package fr.istic.sir.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import domain.ElectronicDevice;
import domain.Home;
import domain.Person;
import jpa.EntityManagerHelper;

@Path("/")
public class MainService {
	@GET
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_HTML + ";charset=utf-8")
	public Response getHome(){

		String ret = "<html><body>"
				+ "<input type=\"submit\" value=\"go to persons\" onclick=\"window.location='/rest/person'\">"
				+ "<input type=\"submit\" value=\"go to homes\" onclick=\"window.location='/rest/home'\">"
				+ "</body></html>";		

		CacheControl cc = new CacheControl();
		cc.setNoCache(true);
		return Response.status(200).entity(ret).cacheControl(cc).build();
	}
}