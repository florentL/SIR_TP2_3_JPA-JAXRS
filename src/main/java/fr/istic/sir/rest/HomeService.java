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

import domain.Heater;
import domain.Home;
import domain.Person;
import jpa.EntityManagerHelper;

@Path("/home")
public class HomeService {
	@GET
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_HTML + ";charset=utf-8")
	public Response getHome(@CookieParam("modif") String modif){

		String ret = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /></head><body>";

		if (modif != null){
			ret += "<div>" + modif + "</div></br>";
		}

		List<Home> homes = EntityManagerHelper.getEntityManager().createNamedQuery("findAllHomes",Home.class).getResultList();

		ret += "<table style=\"border : 1px solid black; border-collapse: collapse;\"><thead><th>Address</th><th>Rooms</th><th>Surface</th><th>Inhabitant</th><th>Heaters</th><th>Update</th><th>Delete</th></thead><tbody>";

		for (Home home : homes) {
			ret += "<tr style=\"border : 1px solid black;\">";
			ret += "<td>" + home.getAddress() + "</td>";
			ret += "<td>" + home.getNbRooms()+ "</td>";
			ret += "<td>" + home.getSize() + "</td>";
			if (home.getInhabitant() != null){
				ret += "<td>" + home.getInhabitant().getFirstName() + " " + home.getInhabitant().getName() + "</td>";
			}
			else{
				ret += "<td>null</td>";
			}
			ret += "<td><ul>";
			List<Heater> heaters = home.getHeaters();
			for (Heater heater : heaters) {
				ret += "<li>" + heater.getConsumption() + "</li>";
			}
			ret += "<ul></td><td><input type=\"submit\" value=\"update\" onclick=\"window.location='home/" + home.getId() + "'\" /></td>"
					+ "<td><form method=\"post\" action=\"/rest/home/delete/" + home.getId() + "\"><input type=submit value=delete></form></td></tr>";
		}
		ret += "</tbody></table></br>";

		ret += "Add new home : </br><FORM Method=\"POST\" Action=\"/rest/home/add\">"
				+ "Address : <input type=text size=20 name=address></br>"
				+ "nbroom : <input type=text size=20 name=nbroom></br>"
				+ "surface : <input type=text size=20 name=surface></br>";

		ret += "inhabitant : <select name=\"inhabitant\">";
		List<Person> inhabitants = EntityManagerHelper.getEntityManager().createNamedQuery("findAllPerson",Person.class).getResultList();
		for (Person in : inhabitants){
			ret += "<option>"  + in.getId() + ", " + in.getFirstName() + " " + in.getName();
		}
		ret += "</select></br>";

		ret += "heaters : <button type=button id=addheater>add heater</button>"
				+ "<div id=heaters></div>";

		ret += "<input type=submit value=Send></FORM>"
				+ "<input type=\"submit\" value=\"back to main menu\" onclick=\"window.location='/rest'\">"
				+ "<script src=\"../../js/home.js\" type=\"text/javascript\"></script>"
				+ "</body></html>";		
		
		CacheControl cc = new CacheControl();
		cc.setNoCache(true);
		return Response.status(200).entity(ret).cacheControl(cc).build();
	}

	@GET
	@Produces(MediaType.TEXT_HTML + ";charset=utf-8")
	@Path("{id}")
	public Response getHomeById(@PathParam("id") String id, @CookieParam("modif") String modif) {
		Home home = EntityManagerHelper.getEntityManager().createNamedQuery("findHomeById",Home.class).setParameter("HomeId", new Long(id)).getSingleResult();
		String ret = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /></head><body>";
		if (modif != null){
			ret += "<div>" + modif + "</div></br>";

		}
		ret += "<FORM Method=\"POST\" Action=\"/rest/home/"+ id + "\">"
				+ "Address : <input type=text size=20 name=address value=\"" + home.getAddress() + "\"></br>"
				+ "nbroom : <input type=text size=20 name=nbroom value=\"" + home.getNbRooms() + "\"></br>"
				+ "surface : <input type=text size=20 name=surface value=\"" + home.getSize() + "\"></br>";

		ret += "inhabitant : <select name=\"inhabitant\">";
		List<Person> inhabitants = EntityManagerHelper.getEntityManager().createNamedQuery("findAllPerson",Person.class).getResultList();
		for (Person in : inhabitants){
			if (in.getId() == home.getInhabitant().getId()){
				ret += "<option selected>" + in.getId() + ", " + in.getFirstName() + " " + in.getName();
			}
			else{
				ret += "<option>"  + in.getId() + ", " + in.getFirstName() + " " + in.getName();
			}
		}
		ret += "</select></br>";

		ret += "heaters : <button type=button id=addheater>add heater</button>"
				+ "<div id=heaters></div>";
		int ind = 0;
		for (Heater h : home.getHeaters()) {
			ret += "<div class=\"heater\" id=\"exist" + ind + "\">consumption : <input type=number size=20 name=exist" + h.getId() + " value=\"" + h.getConsumption() + "\"><input type=button value=clear name=clear  onclick=cc(\"exist" + ind + "\")></br></div>";
			ind++;	
		}

		ret += "<input type=submit value=Send></FORM>"
				+ "<input type=\"submit\" value=\"back to homes\" onclick=\"window.location='/rest/home'\">"
				+ "<input type=\"submit\" value=\"back to main menu\" onclick=\"window.location='/rest'\">"
				+ "<script src=\"../../js/home.js\" type=\"text/javascript\"></script>"
				+ "</body></html>";

		CacheControl cc = new CacheControl();
		cc.setNoCache(true);
		return Response.status(200).entity(ret).cacheControl(cc).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("{id}")
	public Response updateHome(@PathParam("id") String id, MultivaluedMap<String, String> parameters) {
		Home home = EntityManagerHelper.getEntityManager().createNamedQuery("findHomeById",Home.class).setParameter("HomeId", new Long(id)).getSingleResult();

		for (int i = 0; i < home.getHeaters().size(); i++) {
			Heater h = home.getHeaters().get(i);
			if (!parameters.containsKey("exist" + h.getId())){
				EntityManagerHelper.beginTransaction();
				home.getHeaters().remove(i);
				EntityManagerHelper.commit();
			}

		}
		for (String key : parameters.keySet()) {
			if (key.contains("inhabitant") == true){
				EntityManagerHelper.beginTransaction();
				Person in = EntityManagerHelper.getEntityManager().createNamedQuery("findPersonById",Person.class).setParameter("PersonId", new Long(parameters.getFirst(key).split(",")[0])).getSingleResult();
				home.setInhabitant(in);
				EntityManagerHelper.commit();
			}
			if (key.contains("exist") == true){
				String hid = key.substring(5);
				for (Heater h : home.getHeaters()) {
					if (h.getId() == Integer.parseInt(hid)){
						EntityManagerHelper.beginTransaction();
						h.setConsumption(Integer.parseInt(parameters.getFirst(key)));
						EntityManagerHelper.getEntityManager().merge(h);
						EntityManagerHelper.commit();
					}
				}
			}
			if (key.contains("heater") == true){
				EntityManagerHelper.beginTransaction();
				home.addHeater(new Heater(Integer.parseInt(parameters.getFirst(key))));
				EntityManagerHelper.getEntityManager().persist(home);
				EntityManagerHelper.commit();
			}
			else{
				EntityManagerHelper.beginTransaction();
				if (key.contains("address") == true){
					home.setAddress(String.valueOf(parameters.getFirst(key)));
				}
				else if (key.contains("nbroom") == true){
					home.setNbRooms(Integer.parseInt(parameters.getFirst(key)));
				}
				else if (key.contains("surface") == true){
					home.setSize(Integer.parseInt(parameters.getFirst(key)));
				}
				EntityManagerHelper.commit();
			}
		}

		NewCookie c = new NewCookie("modif", "the home " + home.getAddress() + " has been modified", "/", null, null, 5, false);
		CacheControl cc = new CacheControl();
		cc.setNoCache(true);

		return Response.status(Response.Status.SEE_OTHER)
				.cookie(c)
				.cacheControl(cc)
				.header(HttpHeaders.LOCATION, "/rest/home/" + id)
				.build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/add")
	public Response addHome(MultivaluedMap<String, String> parameters) {
		EntityManagerHelper.beginTransaction();

		Home h = new Home();
		for (String key : parameters.keySet()) {
			if (key.contains("inhabitant") == true){
				Person in = EntityManagerHelper.getEntityManager().createNamedQuery("findPersonById",Person.class).setParameter("PersonId", new Long(parameters.getFirst(key).split(",")[0])).getSingleResult();
				h.setInhabitant(in);
			}
			if (key.contains("heater") == true){
				h.addHeater(new Heater(Integer.parseInt(parameters.getFirst(key))));
			}
			else if (key.contains("address") == true){
				h.setAddress(String.valueOf(parameters.getFirst(key)));
			}
			else if (key.contains("nbroom") == true){
				h.setNbRooms(Integer.parseInt(parameters.getFirst(key)));
			}
			else if (key.contains("surface") == true){
				h.setSize(Integer.parseInt(parameters.getFirst(key)));
			}
		}

		EntityManagerHelper.getEntityManager().persist(h);
		EntityManagerHelper.commit();

		NewCookie c = new NewCookie("modif", "the home " + h.getAddress() + " has been added", "/", null, null, 5, false);
		CacheControl cc = new CacheControl();
		cc.setNoCache(true);
		
		return Response.status(Response.Status.SEE_OTHER)
				.cookie(c)
				.cacheControl(cc)
				.header(HttpHeaders.LOCATION, "/rest/home")
				.build();
	}

	@POST
	@Path("/delete/{id}")
	public Response deleteHome(@PathParam("id") String id) {
		Home home = EntityManagerHelper.getEntityManager().createNamedQuery("findHomeById",Home.class).setParameter("HomeId", new Long(id)).getSingleResult();

		for (int i = 0; i < home.getHeaters().size(); i++) {
			EntityManagerHelper.beginTransaction();
			home.getHeaters().remove(i);
			EntityManagerHelper.commit();
		}
		EntityManagerHelper.beginTransaction();
		home.setInhabitant(null);
		EntityManagerHelper.getEntityManager().remove(home);
		EntityManagerHelper.commit();

		NewCookie c = new NewCookie("modif", "the home " + home.getAddress() + " has been deleted", "/", null, null, 5, false);
		CacheControl cc = new CacheControl();
		cc.setNoCache(true);
		
		return Response.status(Response.Status.SEE_OTHER)
				.cookie(c)
				.cacheControl(cc)
				.header(HttpHeaders.LOCATION, "/rest/home")
				.build();
	}
}