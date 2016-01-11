package jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

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
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dev");
		EntityManager manager = factory.createEntityManager();
		JpaTest test = new JpaTest(manager);

		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		try {
			
			test.createPersons();
//			test.getAllPersons();
			
//			Home home = new Home("istic", 100, 4);
//			home.setInhabitant(p);
//			
//			manager.persist(home);
//			Heater h = new Heater(10);
//			
//			h.setHome(home);
//			manager.persist(h);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		tx.commit();
		// String s = "SELECT e FROM Person as e where e.name=:name";

		 //Query q = manager.createQuery(s,Person.class);
		// q.setParameter("name", "martin");
		 //List<Person> res = q.getResultList();

		 //System.err.println(res.size());
		 //System.err.println(res.get(0).getName());

		/*String s = "SELECt h FROM Home h";
		List<Home> resultList = manager.createQuery(s, Home.class).getResultList();
		for (Home h : resultList) {
			System.out.println("home id : " + h.getId());
			for (Heater he : h.getHeaters()) {
				System.out.println("heaters id : " + he.getId());
			}
		}
		*/
		
//		Person p2 = manager.createNamedQuery("findPersonById",Person.class).setParameter("PersonId", new Long(1)).getSingleResult();
//		System.out.println(p2.toString());
		 
		manager.close();
		factory.close();
	}
	
	public void createPersons() {
		Person p = new Person();
		p.setName("martin");
		manager.persist(p);
		Person p2 = new Person();
		p2.setName("robert");
		manager.persist(p2);
		
		p.addFriends(p2);
//		addFriends(p, p2);
		getFriendsOf(p);

	}
	
	public void getFriendsOf(Person p) {
		List<Person> persons = manager.createNamedQuery("findPersonFriends",Person.class).setParameter("id", p.getId()).getResultList();
		for (Person pers : persons) {
			System.out.println("Friends : "+pers.toString());
		}
	}
	
	public void addFriends(Person p, Person p2) {
		List<Person> l = new ArrayList<Person>();
		l.add(p2);
		p.setFriends(l);
	}
	
	public void getAllPersons() {
		List<Person> persons = manager.createNamedQuery("findAllPerson",Person.class).getResultList();
		for (Person p : persons) {
			System.out.println(p.toString());
		}
	}

}
