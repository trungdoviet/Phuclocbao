package vn.com.phuclocbao.entity.factory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMFactory {
	public static final String persistenceUnitName = "plb_unit";
	
	private static EntityManagerFactory emf;
    /**
     * Entity manager entity holder for local thread.
     */
   /* private static ThreadLocal<EntityManager> entityManagerHolder = new ThreadLocal<EntityManager>() {
        protected EntityManager initialValue() {
        	EntityManager em;
        	if(emf == null){
        		emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        	}
        	em = emf.createEntityManager();
            return em;
        };
    };*/

    /**
     * Private constructor.
     */
    private EMFactory() {
    }

    /**
     * Get EnityMangager entity for thread.
     * 
     * @return EntityManager - an <strong>OPEN</strong> {@code EntityManager}
     *         instance.
     */
   /* public static final EntityManager em() {
        if (entityManagerHolder.get().isOpen()) {
            return entityManagerHolder.get();
        }

        entityManagerHolder.remove();
        return entityManagerHolder.get();
    }*/

    /**
     * <p>
     * Closes and also removes the instance of {@code EntityManager} of current
     * thread.
     * </p>
     * <p>
     * <strong>IMPORTANT NOTE: It is strongly recommended to call this method
     * when the {@code EntityManager} is not in used anymore to avoid memory
     * leak</strong>
     * </p>
     */
   /* public static void closeAndRemove() {
        entityManagerHolder.get().close();
        entityManagerHolder.remove();
    }*/
    
    public static void setCustomEMF(EntityManagerFactory customEMF){
    	emf = customEMF;
    }
}