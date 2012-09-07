package org.resthub.training.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
public class Address implements Serializable {
    private String city;
    private String country;

    public Address() {
    }

    public Address(String city, String country) {
        this.city = city;
        this.country = country;
    }

    @NotNull
    @NotEmpty
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @NotNull
    @NotEmpty
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
