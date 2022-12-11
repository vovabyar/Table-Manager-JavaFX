package com.example.demo4.HelpRW;


public class PersonTest {

    private  String firstName;
    private  String lastName;
    private  String street;
    private  Integer postalCode;
    private  String city;
    private  String birthday;


    public PersonTest() {
        this(null, null);
    }


    public PersonTest(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = "какая-то улица";
        this.postalCode = 1234;
        this.city = "какой-то город";
        this.birthday = "12.01.2002";
    }

    

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String cityProperty() {
        return city;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }


    @Override
    public String toString() {
        return getFirstName() + " "
                + getLastName() + " "
                + getStreet() + " "
                + getCity() + " "
                + getPostalCode() + " "
                + getBirthday() + ";";
    }
}