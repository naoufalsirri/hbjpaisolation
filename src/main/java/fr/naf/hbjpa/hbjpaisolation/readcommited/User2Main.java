package fr.naf.hbjpa.hbjpaisolation.readcommited;

public class User2Main {

	public static long idguide = 1;
	
	public static void main(String[] args) {
		DaoIsolation dao =new DaoIsolation();
		Guide ajouterGuide = dao.ajouterGuide();
		dao.modifierGuide(idguide);

	}

}
