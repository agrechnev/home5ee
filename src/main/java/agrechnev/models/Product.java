package agrechnev.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Oleksiy Grechnyev on 10/20/2016.
 * Product class ->  PRODUCTS table
 * Comparable by product_id and mfr_id
 */
@Entity
@IdClass(ProductId.class)
public final class Product implements ShortPrintable<Product> {
    // Proper fields
    @Id
    private String mfrId;
    @Id
    private String productId;

    private String description;
    private BigDecimal price;
    private int qtyOnHand;

    // Links to one

    // Links to Many
    @OneToMany
    @JoinColumns({
            @JoinColumn(name = "product", referencedColumnName = "productId"),
            @JoinColumn(name = "mfr", referencedColumnName = "mfrId")
    })
    private Set<Order> orders = new HashSet<>();


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Product{");
        sb.append("mfrId='").append(mfrId).append('\'');
        sb.append(", productId='").append(productId).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", price=").append(price);
        sb.append(", qtyOnHand=").append(qtyOnHand);
        sb.append(", orders=").append(ShortPrintable.printCollection(orders));
        sb.append('}');
        return sb.toString();
    }

    /**
     * Short description of an object with no recursions
     *
     * @return
     */
    public String toShortString() {
        return "{" + mfrId +
                ", " + productId +
                ", " + description +
                ", " + price +
                ", " + qtyOnHand +
                '}';
    }


    //-----------------------------------------------------------------
    // equals() and hashCode() include proper fields only

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (qtyOnHand != product.qtyOnHand) return false;
        if (mfrId != null ? !mfrId.equals(product.mfrId) : product.mfrId != null) return false;
        if (productId != null ? !productId.equals(product.productId) : product.productId != null) return false;
        if (description != null ? !description.equals(product.description) : product.description != null) return false;
        return price != null ? price.equals(product.price) : product.price == null;

    }

    @Override
    public int hashCode() {
        int result = mfrId != null ? mfrId.hashCode() : 0;
        result = 31 * result + (productId != null ? productId.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Product o) {
        int result = productId.compareTo(o.productId);

        return result == 0 ? mfrId.compareTo(o.mfrId) : result;
    }
//-----------------------------------------------------------------
    // Empty construcor, getters, setters

    public Product() {
    }

    public String getMfrId() {
        return mfrId;
    }

    public void setMfrId(String mfr_id) {
        this.mfrId = mfr_id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String product_id) {
        this.productId = product_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qty_on_hand) {
        this.qtyOnHand = qty_on_hand;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}
