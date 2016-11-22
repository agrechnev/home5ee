package agrechnev.tests;

import agrechnev.testers.Testers;
import org.junit.Test;

/**
 * Created by Oleksiy Grechnyev on 11/22/2016.
 * Tests of the Customer, Office, ... database
 * Using the HQL/SPQL queries
 */
public class HqlTest {
    @Test
    public void test1(){
        Testers.testHql("from Office order by city","tables/test1.txt");
    }

    @Test
    public void test2(){
        Testers.testHql("from Office where region='Eastern' order by city","tables/test2.txt");
    }


    @Test
    public void test3(){
        String query = "select city, target, sales from Office where region='Eastern' " +
                "and sales>target order by city";
        Testers.testHql(query,"tables/test3.txt");
    }
}
