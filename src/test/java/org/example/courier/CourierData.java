package org.example.courier;

public class CourierData {
    private String login;
    private String password;
    private String firstName;



    public CourierData withLogin(String login){
        this.login = login;
        return this;
    }
    public CourierData withPassword(String password){
        this.password = password;
        return this;
    }
    public CourierData withFirstName(String firstName){
        this.firstName = firstName;
        return this;
    }


}
