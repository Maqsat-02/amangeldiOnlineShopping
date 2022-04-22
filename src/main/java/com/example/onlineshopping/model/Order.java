package com.example.onlineshopping.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`order`")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "orderDate", columnDefinition = "TIMESTAMP")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime orderDate;

    @Column(name = "totalPrice")
    private Long totalPrice;

    @Column(name = "isPaid")
    private Boolean isPaid;

    @Column(name = "isDelivered")
    private Boolean isDelivered;

    @JsonBackReference(value = "user")
    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private AppUser user;

    @JsonBackReference(value = "orderItems")
    @OneToMany(mappedBy="order",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Items> orderItems;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", totalPrice=" + totalPrice +
                ", isPaid=" + isPaid +
                ", isDelivered=" + isDelivered +
                ", user=" + user +
                ", orderItems=" + orderItems +
                '}';
    }
}
