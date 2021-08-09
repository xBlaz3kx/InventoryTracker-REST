package com.bdular.inventorytracker.data.supplier.data;

import com.bdular.inventorytracker.data.product.data.Product;
import com.mongodb.lang.Nullable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "suppliers")
public class Supplier {

    @MongoId
    private String ID;
    @NotEmpty
    @Size(min = 1)
    private String name;
    @NotEmpty
    @Size(min = 1)
    private String companyName;
    @NotEmpty
    @Size(min = 1)
    private String address;
    @NotEmpty
    @Size(min = 1)
    private String post;
    @NotEmpty
    @Size(min = 3)
    private String country;
    @NotEmpty
    @DBRef
    private ArrayList<Product> products = new ArrayList<>();
    @DBRef
    private List<SupplierOrder> orders = new ArrayList<>();
    @Nullable
    private Date lastOrdered;
    @DBRef
    private SupplierOrderTemplate orderTemplate;
    @NotEmpty
    @Size(min = 8, max = 11)
    @Pattern(regexp = "^((AT)?U[0-9]{8}|(BE)?0[0-9]{9}|(BG)?[0-9]{9,10}|(CY)?[0-9]{8}L|↵\n" +
            "(CZ)?[0-9]{8,10}|(DE)?[0-9]{9}|(DK)?[0-9]{8}|(EE)?[0-9]{9}|↵\n" +
            "(EL|GR)?[0-9]{9}|(ES)?[0-9A-Z][0-9]{7}[0-9A-Z]|(FI)?[0-9]{8}|↵\n" +
            "(FR)?[0-9A-Z]{2}[0-9]{9}|(GB)?([0-9]{9}([0-9]{3})?|[A-Z]{2}[0-9]{3})|↵\n" +
            "(HU)?[0-9]{8}|(IE)?[0-9]S[0-9]{5}L|(IT)?[0-9]{11}|↵\n" +
            "(LT)?([0-9]{9}|[0-9]{12})|(LU)?[0-9]{8}|(LV)?[0-9]{11}|(MT)?[0-9]{8}|↵\n" +
            "(NL)?[0-9]{9}B[0-9]{2}|(PL)?[0-9]{10}|(PT)?[0-9]{9}|(RO)?[0-9]{2,10}|↵\n" +
            "(SE)?[0-9]{12}|(SI)?[0-9]{8}|(SK)?[0-9]{10})$")
    private String VAT_ID;

    public Supplier(@NotEmpty @Size(min = 1) String name,
                    @NotEmpty @Size(min = 1) String companyName,
                    @NotEmpty @Size(min = 1) String address,
                    @NotEmpty @Size(min = 1) String post,
                    @NotEmpty @Size(min = 3) String country,
                    @NotEmpty @Size(min = 8, max = 11)
                    @Pattern(regexp = "^((AT)?U[0-9]{8}|(BE)?0[0-9]{9}|(BG)?[0-9]{9,10}|(CY)?[0-9]{8}L|↵\n" +
                            "(CZ)?[0-9]{8,10}|(DE)?[0-9]{9}|(DK)?[0-9]{8}|(EE)?[0-9]{9}|↵\n" +
                            "(EL|GR)?[0-9]{9}|(ES)?[0-9A-Z][0-9]{7}[0-9A-Z]|(FI)?[0-9]{8}|↵\n" +
                            "(FR)?[0-9A-Z]{2}[0-9]{9}|(GB)?([0-9]{9}([0-9]{3})?|[A-Z]{2}[0-9]{3})|↵\n" +
                            "(HU)?[0-9]{8}|(IE)?[0-9]S[0-9]{5}L|(IT)?[0-9]{11}|↵\n" +
                            "(LT)?([0-9]{9}|[0-9]{12})|(LU)?[0-9]{8}|(LV)?[0-9]{11}|(MT)?[0-9]{8}|↵\n" +
                            "(NL)?[0-9]{9}B[0-9]{2}|(PL)?[0-9]{10}|(PT)?[0-9]{9}|(RO)?[0-9]{2,10}|↵\n" +
                            "(SE)?[0-9]{12}|(SI)?[0-9]{8}|(SK)?[0-9]{10})$") String VAT_ID) {
        this.name = name;
        this.companyName = companyName;
        this.address = address;
        this.post = post;
        this.country = country;
        this.VAT_ID = VAT_ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public void setLastOrdered(Date lastOrdered) {
        this.lastOrdered = lastOrdered;
    }

    public void setOrderTemplate(SupplierOrderTemplate orderTemplate) {
        this.orderTemplate = orderTemplate;
    }

    public void setVAT_ID(String VAT_ID) {
        this.VAT_ID = VAT_ID;
    }

    public String getName() {
        return name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getAddress() {
        return address;
    }

    public String getPost() {
        return post;
    }

    public String getCountry() {
        return country;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public Date getLastOrdered() {
        return lastOrdered;
    }

    public SupplierOrderTemplate getOrderTemplate() {
        return orderTemplate;
    }

    public String getVAT_ID() {
        return VAT_ID;
    }

    public String getID() {
        return ID;
    }

    public List<SupplierOrder> getOrders() {
        return orders;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("ID", ID)
                .append("name", name)
                .append("companyName", companyName)
                .append("address", address)
                .append("post", post)
                .append("country", country)
                .append("products", products)
                .append("orders", orders)
                .append("lastOrdered", lastOrdered)
                .append("orderTemplate", orderTemplate)
                .append("VAT_ID", VAT_ID)
                .toString();
    }
}
