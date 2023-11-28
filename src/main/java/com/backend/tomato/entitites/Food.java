package com.backend.tomato.entitites;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="food")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Food {

    @Id
    private Long id;

    private double price;

    @Column(length = 1000)
    private String name;

//    private boolean favorite = false;

    @ElementCollection
    @CollectionTable(name = "food_tags", joinColumns = @JoinColumn(name = "food_id"))
    @Column(name = "tag", length = 1000)
    private List<String> tags;

    @Column(length = 1000)
    private String imageUrl;

//    @Column(length = 1000)
//    private String cookTime;


//    @ElementCollection
//    @CollectionTable(name = "food_origins", joinColumns = @JoinColumn(name = "food_id"))
//    @Column(name = "origin", length = 1000)
//    private List<String> origins;
    private Integer cookTime;

    @Column(length = 1000)
    private String description;

    private double stars;

    private double onSale;
}
