package agrechnev.tests;

import agrechnev.testers.Testers;
import org.junit.Test;

/**
 * Created by Oleksiy Grechnyev on 11/22/2016.
 * Tests of the Customer, Office, ... database
 * Using the HQL/JPQL queries
 */
public class HqlTest {
    @Test
    public void test1() {
        Testers.testHql("from Office order by city", "tables/test1.txt");
    }

    @Test
    public void test2() {
        Testers.testHql("from Office where region='Eastern' order by city", "tables/test2.txt");
    }


    @Test
    public void test3() {
        String query = "select city, target, sales from Office where region='Eastern' " +
                "and sales>target order by city";
        Testers.testHql(query, "tables/test3.txt");
    }


    // Note: 4_1 and 4_2 are equivalent and share a single results table
    @Test
    public void test4_1() {
        String query = "select o, c from Order o, Customer c where o.cust=c order by o.orderNum";
        Testers.testHql(query, "tables/test4.txt");
    }

    @Test
    public void test4_2() {
        String query = "select o, c from Order o join o.cust c where o.cust=c order by o.orderNum";
        Testers.testHql(query, "tables/test4.txt");
    }

    // A bit more sophisticated
    @Test
    public void test5_1() {
        String query = "select o.orderNum, o.qty, c.company from Order o, Customer c " +
                "where o.cust=c and o.qty>10 order by o.orderNum";
        Testers.testHql(query, "tables/test5.txt");
    }

    @Test
    public void test5_2() {
        String query = "select o.orderNum, o.qty, c.company from Order o join o.cust c " +
                "where o.qty>10 order by o.orderNum";
        Testers.testHql(query, "tables/test5.txt");
    }


    // Arithmetic expression in column and order by
    @Test
    public void test6() {
        String query = "select o.office, o.city, o.sales-o.target from Office o " +
                "order by o.sales-o.target";
        Testers.testHql(query, "tables/test6.txt");
    }

    // Summary queries: avg
    @Test
    public void test7() {
        String query = "select avg(quota), avg(sales) from Salesrep";
        Testers.testHql(query, "tables/test7.txt");
    }

    //  avg with a function
    @Test
    public void test8() {
        String query = "select avg(100*sales/quota) from Salesrep";
        Testers.testHql(query, "tables/test8.txt");
    }


    //  Now we make it real hardcore
    @Test
    public void test9() {
        String query = "select avg(o.amount), sum(o.amount), (100*avg(o.amount/c.creditLimit))," +
                "(100*avg(o.amount/r.quota)) from Order o, Customer c, Salesrep r " +
                "where o.cust=c and o.rep=r";

        Testers.testHql(query, "tables/test9.txt");
    }

    //  Finally, an example with group by and having
    @Test
    public void test10() {
        String query = "select o.city, sum(s.quota), sum(s.sales) " +
                "from Office o, Salesrep s where s.repOffice=o " +
                "group by o.city having count(*)>=2 order by o.city";

        Testers.testHql(query, "tables/test10.txt");
    }
}
