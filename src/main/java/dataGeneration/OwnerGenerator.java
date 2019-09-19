package dataGeneration;

import com.github.javafaker.Faker;

import java.util.Locale;

public class OwnerGenerator {
    String firstName;
    String lastName;
    String address;
    String city;
    String phoneNumber;
    Faker fake = new Faker(new Locale("en-US"));

    public OwnerGenerator() {
        this.firstName = fake.name().firstName();
        this.lastName = fake.name().lastName();
        this.address = fake.address().streetAddress();
        this.city = fake.address().city();
        this.phoneNumber = fake.phoneNumber().phoneNumber().replace("-","")
                .replace(".","").replace(" ","")
                .replace("(","").replace(")","");
        //this.phoneNumber = fake.phoneNumber().phoneNumber().replaceAll("[ -().]","");
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
