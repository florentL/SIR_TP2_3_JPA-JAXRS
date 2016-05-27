package jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import domain.MyCommand;

public class JpaTest {

	static final int[] NUMBERS = {5, 4, 2, 9, 8, 3, 6, 7};
	static final char[] LETTERS = {'R', 'G', 'P', 'F', 'K', 'T', 'Q', 'V', 'Z', 'X', 'N', 'D', 'A', 'U', 'W', 'H', 'E', 'Y', 'C', 'B', 'S', 'J', 'M'};
	static final int N = (int) (Math.pow(8, 3) * Math.pow(23, 4));
	static final int N_LOOP = 300;

	
	public JpaTest () {
		EntityManagerHelper.getEntityManager();
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JpaTest test = new JpaTest();
		EntityManagerHelper.beginTransaction();
		try {
			
			test.dropTheTable();
			test.fillBase();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		EntityManagerHelper.commit();
		 
		EntityManagerHelper.closeEntityManager();
		EntityManagerHelper.closeEntityManagerFactory();
	}
	
	public void fillBase() {
//		for (long i=0;i<N;i++) {
		for (int i=0;i<N_LOOP;i++) {
			MyCommand myOrder = new MyCommand();
			String customId = this.getCustomId(i);
			myOrder.setCustomId(customId);
			EntityManagerHelper.getEntityManager().persist(myOrder);

		}
		
	}
	
	public String getCustomId(int index) {
//		Random rand = new Random();
//		int s = rand.nextInt(N);
		
		String awesomeId = formatId(index);
		
		return awesomeId;
	}
	
	public String formatId(int s) {
		int rest = (int) (s % (Math.pow(8, 3)));
		List<Integer> eight_decomposition = decompose(rest, 8); 
		
		// add 0 if the size of eight_decomposition < 3
		int nbZeroToAdd = 3 - eight_decomposition.size();
		while (nbZeroToAdd > 0) {
			eight_decomposition.add(0);
			nbZeroToAdd--;
		}
		
		List<Integer> twentyThree_decomposition = decompose((int)((s- rest) / (Math.pow(8, 3))), 23); 

		// add 0 if the size of eight_decomposition < 3
		nbZeroToAdd = 4 - twentyThree_decomposition.size();
		while (nbZeroToAdd > 0) {
			twentyThree_decomposition.add(0);
			nbZeroToAdd--;
		}
		
		String awesomeId = ""+
				LETTERS[twentyThree_decomposition.get(0)]+
				LETTERS[twentyThree_decomposition.get(1)]+
		        NUMBERS[eight_decomposition.get(0)]+
		        NUMBERS[eight_decomposition.get(1)]+
				NUMBERS[eight_decomposition.get(2)]+
				LETTERS[twentyThree_decomposition.get(2)]+
				LETTERS[twentyThree_decomposition.get(3)]
				;
//		System.out.println(awesomeId);
		return awesomeId;
	}
	
	public List<Integer> decompose (int n, int base) {
		List<Integer> decomposition = new ArrayList<Integer>();
		while (n > 0) {
			int rest = n % base;
			
			n = (int) ((n - rest) / base);
			decomposition.add(rest);
		}
		return decomposition;
	}
	
	public void dropTheTable() {
	    Query q = EntityManagerHelper.getEntityManager().createQuery("DELETE FROM MyCommand");
	    q.executeUpdate();
	}
}
