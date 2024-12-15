package com.rubinho.shishki.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@NamedNativeQuery(
        name = "bookings_by_account",
        query = "SELECT * FROM get_bookings_by_account(:p_account_id)",
        resultClass = Booking.class
)
@Table(name = "shishki_bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn
    @ManyToOne
    private Account user;

    @ManyToMany
    @JoinTable(
            name = "shishki_booking_shop",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "shop_item_id")
    )
    private Set<ShopItem> shopItems;

    @ManyToMany
    @JoinTable(
            name = "shishki_booking_services",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private Set<AdditionalService> services;

    @ManyToMany
    @JoinTable(
            name = "shishki_booking_guests",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "guest_id")
    )
    private Set<Guest> guests;

    @JoinColumn
    @ManyToOne
    private House house;

    @Column(nullable = false)
    private LocalDate dateStart;

    @Column(nullable = false)
    private LocalDate dateEnd;

    @Column(nullable = false, unique = true)
    @Min(1000)
    @Max(9999)
    private Integer uniqueKey;
}
