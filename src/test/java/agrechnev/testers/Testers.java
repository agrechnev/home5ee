package agrechnev.testers;

import agrechnev.helpers.HibernateUtil;
import agrechnev.helpers.Util;
import org.hibernate.Session;
import javax.persistence.criteria.CriteriaQuery;
import org.junit.Assert;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;

/**
 * Created by Oleksiy Grechnyev on 11/22/2016.
 * Use to test an HQL query vs a disk file
 */
public class Testers {
    private Testers() {
    }

    /**
     * Run an HQL/JPQL query and compare results to a disk file
     * Use the Hibernate SessionFactory from HibernateUtil
     * @param query
     * @param fileName
     */
    public static void testHql(String query, String fileName){
        testQuery(session -> session.createQuery(query).getResultList(),fileName);
    }

     /**
     * Run a Criteria query and compare results to a disk file
     * Use the Hibernate SessionFactory from HibernateUtil
     * @param query
     * @param fileName
     */
    public static void testCriteria(CriteriaQuery<?> query, String fileName){
        testQuery(session -> session.createQuery(query).getResultList(),fileName);
    }



    /**
     * Run a hibernate query (given by a Function)
     * and compare results to a disk file
     * Use the Hibernate SessionFactory from HibernateUtil
     * @param runQuery
     * @param fileName
     */
    public static void testQuery(Function <Session,List<?>> runQuery, String fileName){
        List<String> expected=null, actual=null;


        // Get the session
        try (Session session = HibernateUtil.getInstance().getSessionFactory().openSession()){
            // Execute the HQL query in transaction to get actua
            session.beginTransaction();

            // Run the query via functional interface
            List<?> list = runQuery.apply(session);

            // Convert to a List<String>
            // Lazy fetching is done here !
            actual= Util.listToStringList(list);
            session.getTransaction().commit();

            // Get expected from a file
            expected= Files.readAllLines(Paths.get(fileName));

        } catch (Throwable e) {
            e.printStackTrace();
            // Any exception means failed test
            Assert.fail();
        }

        // Now run the actual test, outside of try catch
        // I could have asserted lists directly
        // But array comparison is more verbose if errors
        Assert.assertArrayEquals(expected.toArray(),actual.toArray());

    }
}
