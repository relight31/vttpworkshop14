package sg.edu.nus.iss.app.vttpworkshop14.model;

import java.io.Serializable;
import java.util.Random;

//represents each contact's information

public class Contact implements Serializable{
    private String id;
    private String name;
    private String phone;
    private String email;

    public Contact() {
        this.id = generate(8);
    }

    // public Contact(String id, String name, String phone, String email) {
    //     this.id = id;
    //     this.name = name;
    //     this.phone = phone;
    //     this.email = email;
    // }

    public Contact(String name, String phone, String email) {
        this.id = generate(8);
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private synchronized String generate(int numChars){
        Random r = new Random();
        StringBuilder strBuilder = new StringBuilder();
        while(strBuilder.length()<numChars){
            strBuilder.append(Integer.toHexString(r.nextInt()));
        }
        return strBuilder.toString().substring(0,numChars);
    }
}
