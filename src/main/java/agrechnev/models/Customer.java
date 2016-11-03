package agrechnev.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Oleksiy Grechnyev on 10/20/2016.
 * Customer class -> CUSTOMERS table
 * Comparable by cust_num
 */
@Entity
public final class Customer implements ShortPrintable<Customer> {
    // Proper fields
    @Id
    private int custNum;

    private String company;
    private BigDecimal creditLimit;


    // Links to one
    @ManyToOne
    @JoinColumn(name="custRep")
    private Salesrep custRep;


    // Links to Many
    @OneToMany(mappedBy = "cust")
    private Set<Order> orders;

    /**
     * Note that toString uses toShortString()-based printCollection to avoid infinite recursion
     * @return
     */
    @Override
    public String toString() {
        return "Customer{" +
                "custNum=" + getCustNum() +
                ", company='" + getCompany() + '\'' +
                ", creditLimit=" + getCreditLimit() +
                ", custRep=" + getCustRep() +
                ", orders=" + ShortPrintable.printCollection(getOrders()) +
                '}';
    }

    /**
     * Short description of an object with no recursions
     *
     * @return
     */
    public String toShortString() {
        return "{" + getCustNum() +
                ", " + getCompany() +
                ", " + getCreditLimit() + '}';
    }

    //-----------------------------------------------------------------
    // equals() and hashCode() include id only


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        return getCustNum() == customer.getCustNum();

    }

    @Override
    public int hashCode() {
        return getCustNum();
    }

    @Override
    public int compareTo(Customer o) {
        return Integer.compare(getCustNum(), o.getCustNum());
    }

    //-----------------------------------------------------------------
    // Empty construcor, getters, setters

    public Customer() {
    }

    public int getCustNum() {
        return custNum;
    }

    public void setCustNum(int custNum) {
        this.custNum = custNum;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Salesrep getCustRep() {
        return custRep;
    }

    public void setCustRep(Salesrep custRep) {
        this.custRep = custRep;
    }



    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}
