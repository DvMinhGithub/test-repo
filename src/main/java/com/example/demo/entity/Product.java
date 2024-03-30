package com.example.demo.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "view")
    private Integer view = 0;

    @Column(name = "sold")
    private Integer sold = 0;

    @Column(name = "discount_percent")
    private Integer discountPercent = 0;

    @Column(name = "has_delete")
    private Boolean hasDelete = false;

    @Column(name = "has_approved")
    private Boolean hasApproved = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id",referencedColumnName = "id")
    private Shop shop;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "product")
    private Set<ProductAttribute> listAttribute;

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.LAZY, mappedBy="listProduct")
    private Set<Category> listCategory;
}
