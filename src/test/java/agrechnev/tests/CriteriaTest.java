package agrechnev.tests;

import agrechnev.helpers.HibernateUtil;
import agrechnev.models.Office;
import agrechnev.models.Office_;
import agrechnev.testers.Testers;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Created by Oleksiy Grechnyev on 11/22/2016.
 * Tests of the Customer, Office, ... database
 * Using the criteria queries
 */
public class CriteriaTest {
    private CriteriaBuilder builder;

    @Before
    public void setUp() {
        builder = HibernateUtil.getInstance().getSessionFactory().getCriteriaBuilder();
    }

    @Test
    public void test1() {
        CriteriaQuery<Office> criteria = builder.createQuery(Office.class);
        Root<Office> root = criteria.from(Office.class);
        criteria.select(root);
        criteria.orderBy(builder.asc(root.get(Office_.city)));
        Testers.testCriteria(criteria, "tables/test1.txt");
    }


    @Test
    public void test2() {
        CriteriaQuery<Office> criteria = builder.createQuery(Office.class);
        Root<Office> root = criteria.from(Office.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get(Office_.region), "Eastern"));
        criteria.orderBy(builder.asc(root.get(Office_.city)));
        Testers.testCriteria(criteria, "tables/test2.txt");
    }


    @Test
    public void test3() {
        CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);
        Root<Office> root = criteria.from(Office.class);
        criteria.multiselect(root.get(Office_.city), root.get(Office_.target), root.get(Office_.sales))
                .where(builder.and(builder.equal(root.get(Office_.region), "Eastern"),
                        builder.gt(root.get(Office_.sales), root.get(Office_.target))))
                .orderBy(builder.asc(root.get(Office_.city)));
        Testers.testCriteria(criteria, "tables/test3.txt");
    }


}
