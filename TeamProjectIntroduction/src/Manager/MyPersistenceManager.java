package Manager;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;

public class MyPersistenceManager {
	
	public static PersistenceManager getManager(){
		PersistenceManager pm =  JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
		return pm;
	}
	
}
