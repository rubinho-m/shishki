package com.rubinho.shishki.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "shishki_glampings")
public class Glamping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn
    @ManyToOne
    private Account owner;

    @OneToMany(mappedBy = "glamping", cascade = CascadeType.REMOVE)
    private List<House> houses;

    @OneToMany(mappedBy = "glamping", cascade = CascadeType.REMOVE)
    private List<Review> reviews;

    @Column
    private String address;

    @Column
    private String description;

    @Column
    private String photoName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GlampingStatus glampingStatus;
}
