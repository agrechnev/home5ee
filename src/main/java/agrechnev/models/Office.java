package agrechnev.models;

import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Oleksiy Grechnyev on 10/20/2016.
 * Office class -> OFFICES table
 * Comparable by office
 */

@Entity
public final class Office implements ShortPrintable<Office> {
    // Proper fields

    @Id
    private int office;

    private String city;
    private String region;
    private BigDecimal target;
    private BigDecimal sales;

    // Links to one
    @ManyToOne
    @JoinColumn(name="mgr")
    private Salesrep mgr;

    // Links to Many
    @OneToMany(mappedBy = "repOffice")
    private Set<Salesrep> employees;


    @Override
    public String toString() {
        return "Office{" +
                "office=" + getOffice() +
                ", city='" + getCity() + '\'' +
                ", region='" + getRegion() + '\'' +
                ", target=" + getTarget() +
                ", sales=" + getSales() +
                ", mgr=" + getMgr() +
                ", employees=" + ShortPrintable.printCollection(getEmployees()) +
                '}';
    }

    /**
     * Short description of an object with no recursions
     *
     * @return
     */
    public String toShortString() {
        return "{" + getOffice() + ", " + getCity() +
                ", " + getRegion() + ", " + getTarget() +", " + getSales() + '}';
    }

    //-----------------------------------------------------------------
    // equals() and hashCode() include proper fields only


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Office office1 = (Office) o;

        return getOffice() == office1.getOffice();

    }

    @Override
    public int hashCode() {
        return getOffice();
    }

    @Override
    public int compareTo(Office o) {
        return Integer.compare(getOffice(), o.getOffice());
    }

//-----------------------------------------------------------------
    // Empty construcor, getters, setters

    public Office() {
    }

    public int getOffice() {
        return office;
    }

    public void setOffice(int office) {
        this.office = office;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public BigDecimal getTarget() {
        return target;
    }

    public void setTarget(BigDecimal target) {
        this.target = target;
    }

    public BigDecimal getSales() {
        return sales;
    }

    public void setSales(BigDecimal sales) {
        this.sales = sales;
    }

    public Salesrep getMgr() {
        return mgr;
    }

    public void setMgr(Salesrep mgr) {
        this.mgr = mgr;
    }

    public Set<Salesrep> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Salesrep> employees) {
        this.employees = employees;
    }
}
