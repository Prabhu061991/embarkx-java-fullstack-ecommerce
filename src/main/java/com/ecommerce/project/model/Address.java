package com.ecommerce.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @NotBlank
    @Size(min = 5 , message = "Street Name should be min 5 Chars")
    private String street;

    @NotBlank
    @Size(min = 2 , message = "Building Name should be min 2 Chars")
    private String building;

    @NotBlank
    @Size(min = 2 , message = "City Name should be min 2 Chars")
    private String city;

    @NotBlank
    @Size(min = 2 , message = "Country Name should be min 2 Chars")
    private String country;

    @NotBlank
    @Size(min = 7 , message = "Pincode Name should be min 7 Chars")
    private String pincode;

    @ToString.Exclude
    @ManyToMany(mappedBy = "addresses")
    private List<User> users = new ArrayList<>();
}
