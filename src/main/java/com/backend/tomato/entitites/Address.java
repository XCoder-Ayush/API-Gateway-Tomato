package com.backend.tomato.entitites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue
    @Column(name = "address_id")
    private UUID id;

    @Column(nullable = false)
    private BigDecimal latitude;

    @Column(nullable = false)
    private BigDecimal longitude;

    @Column(length = 1000)
    private String details;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false, length = 6)
    private String pincode;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", details='" + details + '\'' +
                ", city='" + city + '\'' +
                ", pincode='" + pincode + '\'' +
                ", userId=" + (user != null ? user.getUserId() : "not initialized") +
                '}';
    }
}