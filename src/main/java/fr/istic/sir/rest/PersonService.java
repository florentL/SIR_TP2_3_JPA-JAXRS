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

@Path("/person")
public class PersonService {
	@GET
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_HTML + ";charset=utf-8")
	public Response getHome(@CookieParam("modif") String modif){

		String ret = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /></head><body>";

		if (modif != null){
			ret += "<div>" + modif + "</div></br>";
		}

		List<Person> persons = EntityManagerHelper.getEntityManager().createNamedQuery("findAllPerson",Person.class).getResultList();

		ret += "<table style=\"border : 1px solid black; border-collapse: collapse;\"><thead><th>Firstname</th><th>Name</th><th>Mail</th><th>Homes</th><th>Devices</th><th>Friends</th><th>Update</th><th>Delete</th></thead><tbody>";

		for (Person p : persons) {
			ret += "<tr style=\"border : 1px solid black;\"><td>" + p.getFirstName() + "</td><td>" + p.getName() + "</td><td>" + p.getMail() + "</td><td><ul>"; 
			for (Home h : p.getHomes()){
				ret += "<li>" + h.getAddress() + "</li>";
			}
			ret += "</ul></td><td><ul>";
			for (ElectronicDevice ed: p.getEds()) {
				ret += "<li>" + ed.getConsumption() + "</li>";
			}
			ret += "</ul></td><td><ul>";
			for (Person pers : p.getFriends()) {
				ret += "<li>" + pers.getFirstName() + " " + pers.getName() + "</li>";
			}
			ret += "</ul></td>";
			ret += "<td><input type=\"submit\" value=\"update\" onclick=\"window.location='person/" + p.getId() + "'\" /></td>"
					+ "<td><form method=\"post\" action=\"/rest/person/delete/" + p.getId() + "\"><input type=submit value=delete></form></td></tr>";
		}
		ret += "</tbody></table></br>";

		ret += "Add a new person : </br><FORM Method=\"POST\" Action=\"/rest/person/add\">"
				+ "Firstname : <input type=text size=20 name=firstname></br>"
				+ "name : <input type=text size=20 name=name></br>"
				+ "mail : <input type=text size=20 name=mail></br>";

		ret += "<select id=\"hiddenhomes\" style=\"display:none;\">";
		List<Home> homes = EntityManagerHelper.getEntityManager().createNamedQuery("findEmptyHome",Home.class).getResultList();
		for (Home h : homes){
			ret += "<option>"  + h.getId() + ", " + h.getAddress();
		}
		ret += "</select>";

		ret += "<select id=\"hiddenfriends\" style=\"display: none;\">";
		List<Person> friends = EntityManagerHelper.getEntityManager().createNamedQuery("findAllPerson",Person.class).getResultList();
		for (Person in : friends){
			ret += "<option>"  + in.getId() + ", " + in.getFirstName() + " " + in.getName();
		}
		ret += "</select>";

		ret += "homes : <button type=button id=addhomes>add homes</button>"
				+ "<div id=homes></div>";

		ret += "friends : <button type=button id=addfriends>add friends</button>"
				+ "<div id=friends></div>";

		ret += "electronic devices : <button type=button id=addeds>add device</button>"
				+ "<div id=eds></div>";

		ret += "<input type=submit value=Send></FORM>"
				+ "<input type=\"submit\" value=\"back to main menu\" onclick=\"window.location='/rest'\">"
				+ "<script src=\"../../js/person.js\" type=\"text/javascript\"></script>"
				+ "</body></html>";		
		
		CacheControl cc = new CacheControl();
		cc.setNoCache(true);
		return Response.status(200).entity(ret).cacheControl(cc).build();
	}

	@GET
	@Produces(MediaType.TEXT_HTML + ";charset=utf-8")
	@Path("{id}")
	public Response getHomeById(@PathParam("id") String id, @CookieParam("modif") String modif) {
		Person p = EntityManagerHelper.getEntityManager().createNamedQuery("findPersonById",Person.class).setParameter("PersonId", new Long(id)).getSingleResult();
		String ret = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /></head><body>";
		if (modif != null){
			ret += "<div>" + modif + "</div></br>";

		}
		ret += "<FORM Method=\"POST\" Action=\"/rest/person/"+ id + "\">"
				+ "Firstname : <input type=text size=20 name=firstname value=\"" + p.getFirstName() + "\"></br>"
				+ "name : <input type=text size=20 name=name value=\"" + p.getName() + "\"></br>"
				+ "mail : <input type=text size=20 name=mail value=\"" + p.getMail() + "\"></br>";

		ret += "<select id=\"hiddenhomes\" style=\"display:none;\">";
		List<Home> homes = EntityManagerHelper.getEntityManager().createNamedQuery("findEmptyHome",Home.class).getResultList();
		for (Home h : homes){
			ret += "<option>"  + h.getId() + ", " + h.getAddress();
		}
		ret += "</select>";

		ret += "<select id=\"hiddenfriends\" style=\"display: none;\">";
		List<Person> friends = EntityManagerHelper.getEntityManager().createNamedQuery("findAllPerson",Person.class).getResultList();
		for (Person in : friends){
			if (p.getId() != in.getId()){
				ret += "<option>"  + in.getId() + ", " + in.getFirstName() + " " + in.getName();
			}
		}
		ret += "</select>";

		ret += "homes : <button type=button id=addhomes>add homes</button>"
				+ "<div id=homes></div>";

		int ind = 0;
		for (Home h : p.getHomes()) {
			ret += "<div id=\"existh" + ind + "\">address : " + h.getAddress() + "<input style=\"display:none;\" name=existh" + h.getId() + "><input type=button value=clear name=clear  onclick=cc(\"existh" + ind + "\")></br></div>";
			ind++;	
		}

		ret += "friends : <button type=button id=addfriends>add friends</button>"
				+ "<div id=friends></div>";

		ind = 0;
		for (Person fr : p.getFriends()) {
			ret += "<div id=\"existf" + ind + "\">name : " + fr.getFirstName() + " " + fr.getName() + "<input style=\"display:none;\" name=existf" + fr.getId() + "><input type=button value=clear name=clear  onclick=cc(\"existf" + ind + "\")></br></div>";
			ind++;	
		}

		ret += "electronic devices : <button type=button id=addeds>add device</button>"
				+ "<div id=eds></div>";

		ind = 0;
		for (ElectronicDevice ed : p.getEds()) {
			ret += "<div id=\"existe" + ind + "\">consumption : <input type=number size=20 name=existe" + ed.getId() + " value=\"" + ed.getConsumption() + "\"><input type=button value=clear name=clear  onclick=cc(\"existe" + ind + "\")></br></div>";
			ind++;	
		}

		ret += "<input type=submit value=Send></FORM>"
				+ "<input type=\"submit\" value=\"back to persons\" onclick=\"window.location='/rest/person'\">"
				+ "<input type=\"submit\" value=\"back to main menu\" onclick=\"window.location='/rest'\">"
				+ "<script src=\"../../js/person.js\" type=\"text/javascript\"></script>"
				+ "</body></html>";
		
//	    cc.setMaxAge(86400);
//	    cc.setPrivate(true);
		CacheControl cc = new CacheControl();
		cc.setNoCache(true);
		return Response.status(200).entity(ret).cacheControl(cc).build();

	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("{id}")
	public Response updateHome(@PathParam("id") String id, MultivaluedMap<String, String> parameters) {
		Person p = EntityManagerHelper.getEntityManager().createNamedQuery("findPersonById",Person.class).setParameter("PersonId", new Long(id)).getSingleResult();

		for (int i = 0; i < p.getEds().size(); i++) {
			ElectronicDevice ed = p.getEds().get(i);
			if (!parameters.containsKey("existe" + ed.getId())){
				EntityManagerHelper.beginTransaction();
				p.getEds().remove(i);
				EntityManagerHelper.commit();
			}
		}

		for (int i = 0; i < p.getFriends().size(); i++) {
			Person fr = p.getFriends().get(i);
			if (!parameters.containsKey("existf" + fr.getId())){
				EntityManagerHelper.beginTransaction();
				p.getFriends().get(i).removeFriends(p);
				EntityManagerHelper.commit();
			}
		}

		for (int i = 0; i < p.getHomes().size(); i++) {
			Home h = p.getHomes().get(i);
			if (!parameters.containsKey("existh" + h.getId())){
				EntityManagerHelper.beginTransaction();
				p.getHomes().get(i).removeInhabitant();
				EntityManagerHelper.commit();
			}
		}

		for (String key : parameters.keySet()) {
			System.out.println(key + " ; " + parameters.getFirst(key));
			if (key.contains("existe") == true){
				String hid = key.substring(6);
				for (ElectronicDevice ed : p.getEds()) {
					if (ed.getId() == Integer.parseInt(hid)){
						EntityManagerHelper.beginTransaction();
						ed.setConsumption(Integer.parseInt(parameters.getFirst(key)));
						EntityManagerHelper.getEntityManager().merge(ed);
						EntityManagerHelper.commit();
					}
				}
			}
			if (key.contains("ed") == true){
				EntityManagerHelper.beginTransaction();
				p.addEd(new ElectronicDevice(Integer.parseInt(parameters.getFirst(key))));
				EntityManagerHelper.getEntityManager().persist(p);
				EntityManagerHelper.commit();
			}
			if (key.contains("friend") == true){
				Person friend = EntityManagerHelper.getEntityManager().createNamedQuery("findPersonById",Person.class).setParameter("PersonId", new Long(parameters.getFirst(key).split(",")[0])).getSingleResult();
				p.addFriends(friend);
			}
			if (key.contains("home") == true){
				Home sweethome = EntityManagerHelper.getEntityManager().createNamedQuery("findHomeById", Home.class).setParameter("HomeId", new Long(parameters.getFirst(key).split(",")[0])).getSingleResult();
				p.addHome(sweethome);
			}
			else{
				EntityManagerHelper.beginTransaction();
				if (key.contains("firstname") == true){
					p.setFirstName(String.valueOf(parameters.getFirst(key)));
				}
				else if (key.contains("name") == true){
					p.setName(String.valueOf(parameters.getFirst(key)));
				}
				else if (key.contains("mail") == true){
					p.setMail(String.valueOf(parameters.getFirst(key)));
				}
				EntityManagerHelper.commit();
			}
		}

		NewCookie c = new NewCookie("modif", p.getFirstName() + " has been modified", "/", null, null, 5, false);
		CacheControl cc = new CacheControl();
		cc.setNoCache(true);

		return Response.status(Response.Status.SEE_OTHER)
				.cookie(c)
				.cacheControl(cc)
				.header(HttpHeaders.LOCATION, "/rest/person/" + id)
				.build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/add")
	public Response addHome(MultivaluedMap<String, String> parameters) {
		EntityManagerHelper.beginTransaction();

		Person p = new Person();
		for (String key : parameters.keySet()) {
			System.out.println(key + " : " + parameters.getFirst(key));
			if (key.contains("ed") == true){
				p.addEd(new ElectronicDevice(Integer.parseInt(parameters.getFirst(key))));
			}
			if (key.contains("friend") == true){
				Person friend = EntityManagerHelper.getEntityManager().createNamedQuery("findPersonById",Person.class).setParameter("PersonId", new Long(parameters.getFirst(key).split(",")[0])).getSingleResult();
				p.addFriends(friend);
			}
			if (key.contains("home") == true){
				Home sweethome = EntityManagerHelper.getEntityManager().createNamedQuery("findHomeById", Home.class).setParameter("HomeId", new Long(parameters.getFirst(key).split(",")[0])).getSingleResult();
				p.addHome(sweethome);
			}
			if (key.contains("firstname") == true){
				p.setFirstName(String.valueOf(parameters.getFirst(key)));
			}
			else if (key.contains("name") == true){
				p.setName(String.valueOf(parameters.getFirst(key)));
			}
			else if (key.contains("mail") == true){
				p.setMail(String.valueOf(parameters.getFirst(key)));
			}
		}

		EntityManagerHelper.getEntityManager().persist(p);
		EntityManagerHelper.commit();

		NewCookie c = new NewCookie("modif", p.getFirstName() + " has been added", "/", null, null, 5, false);
		CacheControl cc = new CacheControl();
		cc.setNoCache(true);

		return Response.status(Response.Status.SEE_OTHER)
				.cookie(c)
				.cacheControl(cc)
				.header(HttpHeaders.LOCATION, "/rest/person")
				.build();
	}

	@POST
	@Path("/delete/{id}")
	public Response deleteHome(@PathParam("id") String id) {
		Person p = EntityManagerHelper.getEntityManager().createNamedQuery("findPersonById",Person.class).setParameter("PersonId", new Long(id)).getSingleResult();

		for (int i = 0; i < p.getEds().size(); i++) {
			EntityManagerHelper.beginTransaction();
			p.getEds().remove(i);
			EntityManagerHelper.commit();
		}
		for (int i = 0; i < p.getHomes().size(); i++) {
			EntityManagerHelper.beginTransaction();
			p.getHomes().get(i).setInhabitant(null);
			p.getHomes().remove(i);
			EntityManagerHelper.commit();
		}
		for (int i = 0; i < p.getFriends().size(); i++) {
			EntityManagerHelper.beginTransaction();
			p.getFriends().get(i).removeFriends(p);;
			EntityManagerHelper.commit();
		}
		EntityManagerHelper.beginTransaction();
		EntityManagerHelper.getEntityManager().remove(p);
		EntityManagerHelper.commit();

		NewCookie c = new NewCookie("modif", p.getFirstName() + " has been deleted", "/", null, null, 5, false);
		CacheControl cc = new CacheControl();
		cc.setNoCache(true);

		return Response.status(Response.Status.SEE_OTHER)
				.cookie(c)
				.cacheControl(cc)
				.header(HttpHeaders.LOCATION, "/rest/person")
				.build();
	}
}