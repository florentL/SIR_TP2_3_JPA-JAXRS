package jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import domain.ElectronicDevice;
import domain.Heater;
import domain.Home;
import domain.Person;

public class JpaTest {

	private EntityManager manager;
	
	public JpaTest (EntityManager manager) {
		this.manager = manager;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("mysql");
		EntityManager manager = factory.createEntityManager();
		JpaTest test = new JpaTest(manager);

		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		try {
			
			test.fillBase();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		tx.commit();
		
//		criteria request
//		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
//		CriteriaQuery<String> query = criteriaBuilder.createQuery(String.class);
//		Root<Person> from = query.from(Person.class);
//		query.multiselect(from.get("firstName")).where(from.get("name").in("Andronov"));
		
//		String result = manager.createQuery(query).getSingleResult();
//		System.out.println(result);
		
		
//		for (Home h : resultList) {
//			System.out.println("home id : " + h.getId());
//			for (Heater he : h.getHeaters()) {
//				System.out.println("heaters id : " + he.getId());
//			}
//		}
		// String s = "SELECT e FROM Person as e where e.name=:name";

		 //Query q = manager.createQuery(s,Person.class);
		// q.setParameter("name", "martin");
		 //List<Person> res = q.getResultList();

		 //System.err.println(res.size());
		 //System.err.println(res.get(0).getName());

//		String s = "SELECt h FROM Home h";
//		List<Home> resultList = manager.createQuery(s, Home.class).getResultList();
//		for (Home h : resultList) {
//			System.out.println("home id : " + h.getId());
//			for (Heater he : h.getHeaters()) {
//				System.out.println("heaters id : " + he.getId());
//			}
//		}
		
//		Person p2 = manager.createNamedQuery("findPersonById",Person.class).setParameter("PersonId", new Long(1)).getSingleResult();
//		System.out.println(p2.toString());
		 
		manager.close();
		factory.close();
	}
	
	public void fillBase() {
		Person p = new Person("Dupont", "Martin", "martin@gmail.com");
		Person p2 = new Person("Durant", "Robert", "robert@gmail.com");
		Person p3 = new Person("Durant", "Micheline", "micheline@gmail.com");
		Person p4 = new Person("Andronov", "Rumen", "rumen@gmail.com");
		manager.persist(p);
		p2.addFriends(p);
		p.addFriends(p3);
		p3.addFriends(p4);
		Home h = new Home("3 rue des Juifs", 100, 4);
		Home h2 = new Home("53 rue Jean Jaurès", 450, 13);
		Home h3 = new Home("1048 rue des Peupliers", 30, 1);
		p.addHome(h);
		p2.addHome(h2);
		p3.addHome(h2);
		p4.addHome(h3);
		Heater he = new Heater(2000);
		Heater he1 = new Heater(1000);
		Heater he2 = new Heater(1000);
		Heater he3 = new Heater(20);
		h.addHeater(he);
		h2.addHeater(he1);
		h2.addHeater(he2);
		h3.addHeater(he3);
		ElectronicDevice ed = new ElectronicDevice(100);
		ElectronicDevice ed1 = new ElectronicDevice(200);
		ElectronicDevice ed2 = new ElectronicDevice(500);
		ElectronicDevice ed3 = new ElectronicDevice(50);
		p.addEd(ed);
		p2.addEd(ed1);
		p2.addEd(ed2);
		p4.addEd(ed3);
	}
	
	public void getFriendsOf(Person p) {
		List<Person> persons = manager.createNamedQuery("findPersonFriends",Person.class).setParameter("id", p.getId()).getResultList();
		for (Person pers : persons) {
			System.out.println("Friends : "+pers.toString());
		}
	}
	
	public void getAllPersons() {
		List<Person> persons = manager.createNamedQuery("findAllPerson",Person.class).getResultList();
		for (Person p : persons) {
			System.out.println(p.toString());
		}
	}
	
	public void getPersonById(Long id) {
		Person p = manager.createNamedQuery("findPersonById",Person.class).setParameter("PersonId", id).getSingleResult();
		System.out.println(p.toString());
	}
	
	public void getPersonByName(String name) {
		Person p = manager.createNamedQuery("findPersonByName",Person.class).setParameter("PersonName", name).getSingleResult();
		System.out.println(p.toString());
	}
}
