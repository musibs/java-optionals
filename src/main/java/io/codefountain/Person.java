package io.codefountain;

import java.util.Objects;
import java.util.Optional;

public class Person {

    private String name;
    private String contactNo;
    private String profession;
    private Address address;

    public Person(String name, String contactNo) {
        System.out.println("Invoked Person constructor");
        this.name = name;
        this.contactNo = contactNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public Optional<String> getProfession(){
        return Optional.ofNullable(profession);
    }

    public Optional<Address> getAddress() {
        return Optional.ofNullable(address);
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) &&
                Objects.equals(contactNo, person.contactNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, contactNo);
    }
}
