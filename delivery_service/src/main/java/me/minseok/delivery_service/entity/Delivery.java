package me.minseok.delivery_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import me.minseok.delivery_service.enums.DeliveryStatus;

@Entity
@Table(indexes = {@Index(name = "idx_orderId", columnList = "orderId")})
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    private String productName;

    private Long productCount;

    private String address;

    private Long referenceCode;

    private DeliveryStatus deliveryStatus;

    public Delivery() {
    }

    public Delivery(Long orderId, String productName, Long productCount, String address, Long referenceCode, DeliveryStatus deliveryStatus) {
        this.orderId = orderId;
        this.productName = productName;
        this.productCount = productCount;
        this.address = address;
        this.referenceCode = referenceCode;
        this.deliveryStatus = deliveryStatus;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public Long getId() {
        return id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getProductName() {
        return productName;
    }

    public Long getProductCount() {
        return productCount;
    }

    public String getAddress() {
        return address;
    }

    public Long getReferenceCode() {
        return referenceCode;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }
}
