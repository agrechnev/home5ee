package agrechnev.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Oleksiy Grechnyev on 10/20/2016.
 * Salesrep class -> SALESREPS table
 * Comparable by empl_num
 */
@Entity
public final class Salesrep implements ShortPrintable<Salesrep> {
    // Proper fields
    @Id
    private int emplNum;

    private String name;
    private int age;
    private String title;
    private LocalDate hireDate;
    private BigDecimal quota;
    private BigDecimal sales;

    // Links to one
    @ManyToOne
    @JoinColumn(name = "manager")
    private Salesrep manager;

    @ManyToOne
    @JoinColumn(name = "repOffice")
    private Office repOffice;

    // Links to Many
    @OneToMany(mappedBy = "custRep")
    private Set<Customer> customers;

    @OneToMany(mappedBy = "mgr")
    private Set<Office> offices;

    @OneToMany(mappedBy = "rep")
    private Set<Order> orders;

    @OneToMany(mappedBy = "manager")
    private Set<Salesrep> managerTo;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Salesrep{");
        sb.append("emplNum=").append(getEmplNum());
        sb.append(", name='").append(getName()).append('\'');
        sb.append(", age=").append(getAge());
        sb.append(", title='").append(getTitle()).append('\'');
        sb.append(", hireDate=").append(getHireDate());
        sb.append(", quota=").append(getQuota());
        sb.append(", sales=").append(getSales());
        sb.append(", manager=").append(ShortPrintable.printEntity(getManager()));
        sb.append(", repOffice=").append(ShortPrintable.printEntity(getRepOffice()));
        sb.append(", customers=").append(ShortPrintable.printCollection(getCustomers()));
        sb.append(", offices=").append(ShortPrintable.printCollection(getOffices()));
        sb.append(", orders=").append(ShortPrintable.printCollection(getOrders()));
        sb.append(", managerTo=").append(ShortPrintable.printCollection(getManagerTo()));
        sb.append('}');
        return sb.toString();
    }

    /**
     * Short description of an object with no recursions
     *
     * @return
     */
    public String toShortString() {
        return "{" + getEmplNum() +
                ", " + getName() +
                ", " + getAge() +
                ", " + getTitle() +
                ", " + getHireDate() +
                ", " + getQuota() +
                ", " + getSales() +
                '}';
    }


    //-----------------------------------------------------------------
    // equals() and hashCode() include proper fields only


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Salesrep salesrep = (Salesrep) o;

        return getEmplNum() == salesrep.getEmplNum();

    }

    @Override
    public int hashCode() {
        return getEmplNum();
    }

    @Override
    public int compareTo(Salesrep o) {
        return Integer.compare(getEmplNum(), o.getEmplNum());
    }

//-----------------------------------------------------------------
    // Empty construcor, getters, setters


    public Salesrep() {
    }

    public int getEmplNum() {
        return emplNum;
    }

    public void setEmplNum(int emplNum) {
        this.emplNum = emplNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public BigDecimal getQuota() {
        return quota;
    }

    public void setQuota(BigDecimal quota) {
        this.quota = quota;
    }

    public BigDecimal getSales() {
        return sales;
    }

    public void setSales(BigDecimal sales) {
        this.sales = sales;
    }

    public Salesrep getManager() {
        return manager;
    }

    public void setManager(Salesrep manager) {
        this.manager = manager;
    }

    public Office getRepOffice() {
        return repOffice;
    }

    public void setRepOffice(Office repOffice) {
        this.repOffice = repOffice;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    public Set<Office> getOffices() {
        return offices;
    }

    public void setOffices(Set<Office> offices) {
        this.offices = offices;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public Set<Salesrep> getManagerTo() {
        return managerTo;
    }

    public void setManagerTo(Set<Salesrep> managerTo) {
        this.managerTo = managerTo;
    }
}
