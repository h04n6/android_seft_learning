package h04n6.json_example;

/**
 * Created by hoang on 2/17/2018.
 */

public class Address {
    private String street;
    private String city;
    private Address[] addresses;

    public Address(){
        addresses = new Address[10];
    }

    public Address(String street, String city){
        this.street = street;
        this.city = city;
    }

    public String getStreet(){
        return this.street;
    }

    public void setStreet(String street){
        this.street = street;
    }

    public String getCity(){
        return this.city;
    }

    public void setCity(String city){
        this.city = city;
    }

    @Override
    public String toString(){
        return street + ", " + city;
    }
}
