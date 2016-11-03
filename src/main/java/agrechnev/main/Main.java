package agrechnev.main;

import agrechnev.helpers.HibernateUtil;
import agrechnev.models.Salesrep;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by Oleksiy Grechnyev on 10/29/2016.
 * main() method to have fun with hibernate
 */
public class Main {
    public static void main(String[] args) {
        // Create a new hibernate session
        // Note: SessionFactory and Session are Closeable, Transaction is not
        try (SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
             Session session = sessionFactory.openSession()) {

            // Let us get all customers
            session.beginTransaction();


//            List<Customer> customerList = (List<Customer>) session.createQuery("from Customer").list();
//            customerList.forEach(System.out::println);


            List list = session.createCriteria(Salesrep.class).list();

            // Print each row while handling arrays correctly
            list.forEach(row -> {
                if (row.getClass().isArray()) {
                    for (Object o : (Object[]) row) {
                        System.out.print(o + " ");
                    }
                    System.out.println("");
                } else {
                    System.out.println(row);
                }
            });

            session.getTransaction().commit();

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
