package com.javarush.domain;


import com.javarush.converters.RatingConverter;
import com.javarush.converters.YearConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(schema = "movie", name = "film")

@NoArgsConstructor
@AllArgsConstructor
public class Film {
    @Setter
    @Getter
    @Id
    @Column(name = "film_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Setter
    @Getter
    private String title;

    @Setter
    @Getter
    @Column(columnDefinition = "text")
    private String description;

    @Setter
    @Getter
    @Convert(converter = YearConverter.class)
    @Column(name = "release_year", columnDefinition = "year")
    private Year year;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "language_id")
    private Language language;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "original_language_id")
    private Language originalLanguage;

    @Setter
    @Getter
    @Column(name = "rental_duration")
    private Byte rentalDuration;

    @Setter
    @Getter
    @Column(name = "rental_rate")
    private BigDecimal rentalRate;

    @Setter
    @Getter
    private Short length;

    @Setter
    @Getter
    @Column(name = "replacement_cost")
    private BigDecimal replacementCost;

    @Setter
    @Getter
    @Column(columnDefinition = "enum('G', 'PG', 'PG-13', 'R', 'NC-17')")
    @Convert(converter = RatingConverter.class)
    private Rating rating;


    @Column(name = "special_features", columnDefinition = "set('Trailers', 'Commentaries', 'Deleted Scenes', 'Behind the Scenes')")
    private String specialFeatures;

    @Setter
    @Getter
    @Column(name = "last_update")
    @UpdateTimestamp
    private LocalDateTime lastUpdate;


    @Setter
    @Getter
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "film_actor",
            joinColumns = @JoinColumn(name = "film_id", referencedColumnName = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id", referencedColumnName = "actor_id"))
    private Set<Actor> actors;
    @Setter
    @Getter
    @ManyToMany
    @JoinTable(name = "film_category",
            joinColumns = @JoinColumn(name = "film_id", referencedColumnName = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "category_id"))
    private Set<Category> categories;

    public Set<Feature> getSpecialFeatures() {
        if(specialFeatures.isEmpty() || Objects.isNull(specialFeatures)) {
            return null;
        }
        HashSet<Feature> hashSet = new HashSet<>();
        String[] split = specialFeatures.split(",");
        for (String s : split) {
            hashSet.add(Feature.getFeaturesByValue(s));
        }
        hashSet.remove(null);
        return hashSet;
    }
    public void setSpecialFeatures(Set<Feature> features) {
        if(Objects.isNull(specialFeatures)) {
            specialFeatures = null;
        } else {
            specialFeatures = features.stream().map(Feature::getValue).collect(Collectors.joining(","));
        }

    }

}

