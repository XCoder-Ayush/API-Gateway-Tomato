package com.backend.tomato.entitites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name="payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Payment {
    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    private String type;

    @NotNull
    private String status;

    private String rzpPayId;

    private String rzpOrderId;

    @NotNull
    private BigDecimal amount;

    private String contact;

    private String email;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;
}
