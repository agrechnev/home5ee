package agrechnev.helpers;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

/**
 * Created by Oleksiy Grechnyev on 10/29/2016.
 * Physical naming strategy with underscores,
 * This version changes all field names to underscored UPPERCASE, e.g. CustNum->CUST_NUM
 * And table names to uppercase+S, e.g. Customer -> CUSTOMERS
 * I made my own implementation similar to the one discussed here
 * http://stackoverflow.com/questions/32437202/improvednamingstrategy-no-longer-working-in-hibernate-5
 */
public class PhysicalNamingStrategyUnderscore extends PhysicalNamingStrategyStandardImpl {

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        return new Identifier(name.getText().toUpperCase().concat("S"),name.isQuoted());
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        return new Identifier(addUnderscores(name.getText()), name.isQuoted());
    }

    /**
     * Replace camel caps with underscores and put to upper case
     */
    private static String addUnderscores(String text) {
        StringBuilder result = new StringBuilder(text);

        // Look for the "aBc" - like combination
        for (int i = 1; i < result.length() - 1; i++) {
            if (Character.isLowerCase(result.charAt(i - 1)) &&
                    Character.isUpperCase(result.charAt(i)) &&
                    Character.isLowerCase(result.charAt(i+1))) {
                result.insert(i,'_');
            }
        }

        return result.toString().toUpperCase();
    }
}
