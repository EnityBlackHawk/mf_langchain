package org.mf.langchain.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "TB_AIRPORT")
public class Airport {

    @Id
    private String id;
    private String name;
    private String city;
    private String country;

    public Airport(String id, String name, String city, String country) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.country = country;
    }

    public Airport() {
    }

    public static AirportBuilder builder() {
        return new AirportBuilder();
    }

    public static class AirportBuilder {
        private String id;
        private String name;
        private String city;
        private String country;

        AirportBuilder() {
        }

        public AirportBuilder id(String id) {
            this.id = id;
            return this;
        }

        public AirportBuilder name(String name) {
            this.name = name;
            return this;
        }

        public AirportBuilder city(String city) {
            this.city = city;
            return this;
        }

        public AirportBuilder country(String country) {
            this.country = country;
            return this;
        }

        public Airport build() {
            return new Airport(this.id, this.name, this.city, this.country);
        }

        public String toString() {
            return "Airport.AirportBuilder(id=" + this.id + ", name=" + this.name + ", city=" + this.city + ", country=" + this.country + ")";
        }
    }
}
