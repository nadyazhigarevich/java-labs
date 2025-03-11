package com.zhigarevich.student.entity;

public class Address {
    private String country;
    private String city;
    private String street;

    public Address(String country, String city, String street) {
        this.country = country;
        this.city = city;
        this.street = street;
    }

    public Address() {

    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Address{");
        sb.append("country='").append(country).append('\'')
                .append(", city='").append(city).append('\'')
                .append(", street='").append(street).append('\'')
                .append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Address)) return false;

        Address address = (Address) obj;
        return (country == null ? address.country == null : country.equals(address.country)) &&
                (city == null ? address.city == null : city.equals(address.city)) &&
                (street == null ? address.street == null : street.equals(address.street));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        return result;
    }
}
