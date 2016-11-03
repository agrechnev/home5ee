package agrechnev.models;

import java.io.Serializable;

/**
 * Created by Oleksiy Grechnyev on 10/31/2016.
 * The compound Id class for Product entity
 */
public class ProductId implements Serializable{
    protected String mfrId;
    protected String productId;

    public ProductId() {
    }

    public ProductId(String mfr_id, String product_id) {
        this.mfrId = mfr_id;
        this.productId = product_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductId productId = (ProductId) o;

        if (mfrId != null ? !mfrId.equals(productId.mfrId) : productId.mfrId != null) return false;
        return this.productId != null ? this.productId.equals(productId.productId) : productId.productId == null;

    }

    @Override
    public int hashCode() {
        int result = mfrId != null ? mfrId.hashCode() : 0;
        result = 31 * result + (productId != null ? productId.hashCode() : 0);
        return result;
    }
}
