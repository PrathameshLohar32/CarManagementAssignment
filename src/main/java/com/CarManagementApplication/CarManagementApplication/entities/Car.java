package com.CarManagementApplication.CarManagementApplication.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Table(name = "Cars")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String description;

    @ElementCollection
    @CollectionTable(
            name = "car_images",
            joinColumns = @JoinColumn(name = "car_id")
    )
    @Column(name = "image")
    private List<String> images = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "car_tags",
            joinColumns = @JoinColumn(name = "car_id")
    )
    @Column(name = "tag", nullable = false)
    private List<String> tags = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Helper methods to ensure lists are never null
    public void setImages(List<String> images) {
        this.images = images != null ? images : new ArrayList<>();
    }

    public void setTags(List<String> tags) {
        this.tags = tags != null ? tags : new ArrayList<>();
    }
}