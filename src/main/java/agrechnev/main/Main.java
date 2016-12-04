package agrechnev.main;

import agrechnev.helpers.HibernateUtil;
import agrechnev.helpers.Util;
import agrechnev.models.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import static javafx.scene.input.KeyCode.O;

/**
 * Created by Oleksiy Grechnyev on 10/29/2016.
 * main() method for fun with hibernate queries
 */
public class Main {
    public static void main(String[] args) {
        // Create a new hibernate session
        // Note: SessionFactory and Session are Closeable, Transaction is not

        try (SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
             Session session = sessionFactory.openSession()) {

            session.beginTransaction();

            List<?> list;

            // The HQL query
            String query = "select o.city, sum(s.quota), sum(s.sales) " +
                    "from Office o, Salesrep s where s.repOffice=o " +
                    "group by o.city having count(*)>=2  order by o.city";

//            list = session.createQuery(query).getResultList();

            // The criteria query
            CriteriaBuilder builder = HibernateUtil.getInstance().getSessionFactory().getCriteriaBuilder();


            CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);

            Root<Office> o = criteria.from(Office.class);
            Root<Salesrep> s = criteria.from(Salesrep.class);

            // A somewhat ugly way to make count(*) but it seems to work
            // count(o) or count(o.get(Office_.city)) would give the same result
            Root<?> asterick = criteria.getRoots().iterator().next();

            criteria.multiselect(
                    o.get(Office_.city),
                    builder.sum(s.get(Salesrep_.quota)),
                    builder.sum(s.get(Salesrep_.sales)))
                    .where(builder.equal(s.get(Salesrep_.repOffice), o))
                    .groupBy(o.get(Office_.city))
                    .having(builder.ge(builder.count(asterick), 2))
                    .orderBy(builder.asc(o.get(Office_.city)));


            list = session.createQuery(criteria).getResultList();

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
