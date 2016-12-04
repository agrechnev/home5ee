package agrechnev.helpers;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Created by Oleksiy Grechnyev on 10/29/2016.
 * Singleton class to provide hibernate SessionFactory
 */
public class HibernateUtil {
    private static HibernateUtil instance;  // Class instance

    private StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();

    private SessionFactory sessionFactory = null;

    public static HibernateUtil getInstance() {

        if (instance == null) {
            instance = new HibernateUtil();
        }

        return instance;
    }

    /**
     * Get the session factory field
     *
     * @return The Session factory
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    private HibernateUtil() {
        createSessionFactory();
    }

    private void createSessionFactory() {
        // Create the hibernate session factory
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Throwable e) {
            StandardServiceRegistryBuilder.destroy(registry);
            throw new ExceptionInInitializerError(e);
        }
    }
}
