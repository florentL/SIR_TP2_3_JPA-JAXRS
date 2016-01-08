package jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import domain.Heater;
import domain.Home;
import domain.Person;

public class JpaTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dev");
		EntityManager manager = factory.createEntityManager();

		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		try {

//			Person p = new Person();
//			p.setName("martin");
//			manager.persist(p);
			Home home = new Home("istic", 100, 4);
//			home.setInhabitant(p);
			
			manager.persist(home);
			Heater h = new Heater(10);
			
			h.setHome(home);
			manager.persist(h);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		tx.commit();
		// String s = "SELECT e FROM Person as e where e.name=:name";

		// Query q = manager.createQuery(s,Person.class);
		// q.setParameter("name", "martin");
		// List<Person> res = q.getResultList();

		// System.err.println(res.size());
		// System.err.println(res.get(0).getName());

		String s = "SELECt h FROM Home h";
		List<Home> resultList = manager.createQuery(s, Home.class).getResultList();
		for (Home h : resultList) {
			System.out.println("home id : " + h.getId());
			for (Heater he : h.getHeaters()) {
				System.out.println("heaters id : " + he.getId());
			}
		}

		manager.close();
		factory.close();
	}

}
