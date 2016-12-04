package agrechnev.tests;

import agrechnev.helpers.HibernateUtil;
import agrechnev.models.*;
import agrechnev.testers.Testers;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
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
        Root<Office> o = criteria.from(Office.class);
        criteria.select(o);
        criteria.orderBy(builder.asc(o.get(Office_.city)));
        Testers.testCriteria(criteria, "tables/test1.txt");
    }


    @Test
    public void test2() {
        CriteriaQuery<Office> criteria = builder.createQuery(Office.class);
        Root<Office> o = criteria.from(Office.class);
        criteria.select(o);
        criteria.where(builder.equal(o.get(Office_.region), "Eastern"));
        criteria.orderBy(builder.asc(o.get(Office_.city)));
        Testers.testCriteria(criteria, "tables/test2.txt");
    }


    // Note: using Object[] output type
    @Test
    public void test3() {
        CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);
        Root<Office> o = criteria.from(Office.class);
        criteria.multiselect(o.get(Office_.city), o.get(Office_.target), o.get(Office_.sales))
                .where(builder.and(builder.equal(o.get(Office_.region), "Eastern"),
                        builder.gt(o.get(Office_.sales), o.get(Office_.target))))
                .orderBy(builder.asc(o.get(Office_.city)));
        Testers.testCriteria(criteria, "tables/test3.txt");
    }

    // Note: 4_1 and 4_2 are equivalent and share a single results table
    // 4_1 is and example of a two-root criteria
    @Test
    public void test4_1() {
        CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);
        Root<Order> o = criteria.from(Order.class);
        Root<Customer> c = criteria.from(Customer.class);

        criteria.multiselect(o, c)
                .where(builder.equal(o.get(Order_.cust), c))
                .orderBy(builder.asc(o.get(Order_.orderNum)));

        Testers.testCriteria(criteria, "tables/test4.txt");
    }

    // 4_2 is a join example
    @Test
    public void test4_2() {
        CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);
        Root<Order> o = criteria.from(Order.class);
        Join<Order, Customer> c = o.join(Order_.cust);

        criteria.multiselect(o, c)
                .orderBy(builder.asc(o.get(Order_.orderNum)));

        Testers.testCriteria(criteria, "tables/test4.txt");
    }

    // A bit more sophisticated
    @Test
    public void test5_1() {
        CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);
        Root<Order> o = criteria.from(Order.class);
        Root<Customer> c = criteria.from(Customer.class);

        criteria.multiselect(o.get(Order_.orderNum), o.get(Order_.qty), c.get(Customer_.company))
                .where(builder.and(
                        builder.equal(o.get(Order_.cust), c),
                        builder.gt(o.get(Order_.qty), 10)
                ))
                .orderBy(builder.asc(o.get(Order_.orderNum)));

        Testers.testCriteria(criteria, "tables/test5.txt");
    }

    @Test
    public void test5_2() {
        CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);
        Root<Order> o = criteria.from(Order.class);
        Join<Order, Customer> c = o.join(Order_.cust);

        criteria.multiselect(o.get(Order_.orderNum), o.get(Order_.qty), c.get(Customer_.company))
                .where(builder.gt(o.get(Order_.qty), 10))
                .orderBy(builder.asc(o.get(Order_.orderNum)));

        Testers.testCriteria(criteria, "tables/test5.txt");
    }


    // Arithmetic expression in column and order by
    @Test
    public void test6() {
        CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);
        Root<Office> o = criteria.from(Office.class);

        criteria.multiselect(o.get(Office_.office), o.get(Office_.city),
                builder.diff(o.get(Office_.sales), o.get(Office_.target)))
                .orderBy(builder.asc(
                        builder.diff(o.get(Office_.sales), o.get(Office_.target))
                ));


        Testers.testCriteria(criteria, "tables/test6.txt");
    }

    // Summary queries: avg
    @Test
    public void test7() {
        // Note that we can use Double[] here
        CriteriaQuery<Double[]> criteria = builder.createQuery(Double[].class);
        Root<Salesrep> s = criteria.from(Salesrep.class);

        criteria.multiselect(
                builder.avg(s.get(Salesrep_.quota)),
                builder.avg(s.get(Salesrep_.sales))
        );


        Testers.testCriteria(criteria, "tables/test7.txt");
    }


    //  avg with a function
    @Test
    public void test8() {

        CriteriaQuery<Double> criteria = builder.createQuery(Double.class);
        Root<Salesrep> s = criteria.from(Salesrep.class);

        // Who would have gussed that division is called "quot" (from quotient) ??!!!
        // F@#$ing stupid !!!

        criteria.select(builder.avg(builder.prod(100,
                builder.quot(s.get(Salesrep_.sales), s.get(Salesrep_.quota))
        )));


        Testers.testCriteria(criteria, "tables/test8.txt");
    }

    // OH MY GOD !
    @Test
    public void test9() {
        CriteriaQuery<Double[]> criteria = builder.createQuery(Double[].class);

        Root<Order> o = criteria.from(Order.class);
        Root<Customer> c = criteria.from(Customer.class);
        Root<Salesrep> s = criteria.from(Salesrep.class);

        criteria.multiselect(
                builder.avg(o.get(Order_.amount)),
                builder.sum(o.get(Order_.amount)),

                builder.prod(100, builder.avg(
                        builder.quot(o.get(Order_.amount), c.get(Customer_.creditLimit)))),

                builder.prod(100, builder.avg(
                        builder.quot(o.get(Order_.amount), s.get(Salesrep_.quota)))))

                .where(builder.and(
                        builder.equal(o.get(Order_.cust), c),
                        builder.equal(o.get(Order_.rep), s)
                ));


        Testers.testCriteria(criteria, "tables/test9.txt");
    }


    //  Finally, an example with group by and having
    @Test
    public void test10() {
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

        Testers.testCriteria(criteria, "tables/test10.txt");
    }
}
