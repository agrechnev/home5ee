package agrechnev.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by Oleksiy Grechnyev on 10/20/2016.
 * Order class -> ORDERS table
 * Comparable by order_num
 */
@Entity
public final class Order implements ShortPrintable<Order> {
    // Proper fields
    @Id
    private int orderNum;

    private LocalDate orderDate;
    private int qty;
    private BigDecimal amount;

    // Links to one
    @ManyToOne
    @JoinColumn(name = "cust")
    private Customer cust;

    @ManyToOne
    @JoinColumn(name = "rep")
    private Salesrep rep;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "product", referencedColumnName = "productId"),
            @JoinColumn(name = "mfr", referencedColumnName = "mfrId")
    })
    private Product product;

    // Links to Many


    @Override
    public String toString() {
        return "Order{" +
                "orderNum=" + getOrderNum() +
                ", orderDate=" + getOrderDate() +
                ", qty=" + getQty() +
                ", amount=" + getAmount() +
                ", cust=" + getCust() +
                ", rep=" + getRep() +
                ", product=" + getProduct() +
                '}';
    }

    /**
     * Short description of an object with no recursions
     *
     * @return
     */
    public String toShortString() {
        return "{" + getOrderNum() +
                ", " + getOrderDate() +
                ", " + getQty() +
                ", " + getAmount() + '}';
    }

    /**
     * Short description of an object with no recursions
     *
     * @return
     */


    //-----------------------------------------------------------------
    // equals() and hashCode() include proper fields only
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        return getOrderNum() == order.getOrderNum();

    }

    @Override
    public int hashCode() {
        return getOrderNum();
    }

    @Override
    public int compareTo(Order o) {
        return Integer.compare(getOrderNum(), o.getOrderNum());
    }

    //-----------------------------------------------------------------
    // Empty construcor, getters, setters


    public Order() {
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


    public Customer getCust() {
        return cust;
    }

    public void setCust(Customer cust) {
        this.cust = cust;
    }

    public Salesrep getRep() {
        return rep;
    }

    public void setRep(Salesrep rep) {
        this.rep = rep;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
