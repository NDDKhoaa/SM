package dktech.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "storages")
public class Storage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "storage_id", nullable = false)
    private long storageID;

    @Column(name = "quantity", nullable = false)
    private double quantity;

    @Column(name = "measurement", nullable = false, length = 100)
    private String measurement;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @Column(name = "product_id", nullable = false)
    private long productID;

    @Transient
    private String productName;

    // Constructors, Getters, Setters, and toString

    public Storage() {
        super();
    }

    public long getStorageID() {
        return storageID;
    }

    public void setStorageID(long storageID) {
        this.storageID = storageID;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public long getProductID() {
        return productID;
    }

    public void setProductID(long productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String toString() {
        return "Storage [storageID=" + storageID + ", quantity=" + quantity + ", measurement=" + measurement
                + ", createdDate=" + createdDate + ", productID=" + productID + ", productName=" + productName + "]";
    }
}
