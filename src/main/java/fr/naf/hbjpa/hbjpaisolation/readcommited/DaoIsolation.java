package fr.naf.hbjpa.hbjpaisolation.readcommited;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;

public class DaoIsolation {

	private static EntityManagerFactory emf;
	private static EntityManager em1;	
	private static EntityTransaction ts1;
	
	
	
	public DaoIsolation() {
		 emf = Persistence.createEntityManagerFactory("persistence");    						         	             
		 em1 = emf.createEntityManager();
		 ts1= em1.getTransaction();

	}
	
	public Guide ajouterGuide() {
		Guide guide =new Guide();
		guide.setNom("Farad");
		guide.setSalaire(3000);		
		ts1.begin();
        em1.persist(guide);                 
        ts1.commit(); 
		return guide;
	}
	
	public void modifierGuide(long id) {
		
		Guide find = em1.find(Guide.class, id);
		find.setNom("Olay");
		ts1.begin();
        em1.merge(find);                 
        ts1.commit(); 
		
	}
	
	public void afficherTotalsalaires() {
		
		//Isolation Serializable arretra toutes les transactions jusqu'a ce que la transaction en cours soit terminee
		//SQL : set global  transaction isolation level serializable;
		
		//Isolation repeatable_read (par defaut sous mysql) prendera en charge les nouvelles lignes inserees apres 
		//un ajout dans la table par une autre transaction
		//SQL : set global  transaction isolation level repeatable read;
				
		//Isolation read_committed (par defaut sous oracle) prendera en charge les nouvelles lignes inserees apres 
		//un ajout dans la table par une autre transaction et meme les modifications dans la meme table
		//SQL : set global  transaction isolation level read committed;
			
		//Isolation read_uncommitted  prendera en charge les nouvelles lignes inserees apres 
	    //un ajout dans la table par une autre transaction et meme les modifications dans la meme table
		//mais les transactions ne sont pas commites jusqu'a que la transaction en cours soit commitee
	    //SQL : set global  transaction isolation level read uncommitted;
		
		//pour afficher l'isolation sous mysql par ex:
		//sql: SELECT @@GLOBAL.transaction_isolation
		
		//pour parametrer isolation sous hibernate
		//voir fichier persistence.xml :
		//<property name="hibernate.connection.isolation" value="2"/>
		//1:read uncommitted, 2:read committed , 4:repeatable read , 8: serializable
		
		em1 = emf.createEntityManager();
		ts1= em1.getTransaction();
		ts1.begin();
		
		
		List<Guide> guides =em1.createQuery("select guide from Guide guide").getResultList();		
		
		for(Guide guide:guides) {
			System.out.println("Name :"+guide.getNom()+ " , Salaire : "+guide.getSalaire());
		}
	
		long totalSalaire = (long) em1.createQuery("select sum(guide.salaire) from Guide guide").getSingleResult();
		System.out.println("Total :"+totalSalaire);
				
		ts1.commit();
		em1.close();
	
	}
	
	
	
}
