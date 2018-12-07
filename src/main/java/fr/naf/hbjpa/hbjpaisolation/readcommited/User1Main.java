package fr.naf.hbjpa.hbjpaisolation.readcommited;

public class User1Main {

	public static long idguide = 1;
	
	public static void main(String[] args) {
		
		DaoIsolation dao =new DaoIsolation();
		//Guide ajouterGuide = dao.ajouterGuide();
		//idguide =ajouterGuide.getId();
		dao.afficherTotalsalaires();

	}

}
