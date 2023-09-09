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

    @Column(length = 1000) // Set the length for the 'name' column
    private String name;

    private boolean favorite = false;

    @ElementCollection
    @CollectionTable(name = "food_tags", joinColumns = @JoinColumn(name = "food_id"))
    @Column(name = "tag", length = 1000) // Set the length for elements in 'tags'
    private List<String> tags;

    @Column(length = 1000) // Set the length for the 'imageUrl' column
    private String imageUrl;

    @Column(length = 1000) // Set the length for the 'cookTime' column
    private String cookTime;

    @ElementCollection
    @CollectionTable(name = "food_origins", joinColumns = @JoinColumn(name = "food_id"))
    @Column(name = "origin", length = 1000) // Set the length for elements in 'origins'
    private List<String> origins;

    private double stars;
}
