package agrechnev.main;

import agrechnev.helpers.HibernateUtil;
import agrechnev.helpers.Util;
import agrechnev.models.Office;
import agrechnev.models.Office_;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

            List<?> list;

            // The HQL query
            String query = "select o, c " +
                    " from Order o join o.cust c";

            list = session.createQuery(query).getResultList();

            // The criteria query
            CriteriaBuilder builder = HibernateUtil.getInstance().getSessionFactory().getCriteriaBuilder();


            CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);
            Root<Office> root = criteria.from(Office.class);


//            list = session.createQuery(criteria).getResultList();

            // Print each row while handling arrays correctly
            List<String> stringList = Util.listToStringList(list);
            stringList.forEach(System.out::println);

            // Optional: write to file
            Path path = Paths.get("tables/test.txt");
//            Files.write(path, stringList);

            session.getTransaction().commit();

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
