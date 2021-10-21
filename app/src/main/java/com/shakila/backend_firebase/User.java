package com.shakila.backend_firebase;

public class User {
    private  String ID;
    private String FirstName;
    private String LastName;
    private String Email;
    private int PhoneNumber;
    private String Password;

    public User(){
        FirstName = "";
        LastName = "";
        Email = "";
        PhoneNumber = 0;
        ID = "";
        Password = "";
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public User (String ID, String FN, String LN, String EA, int PN)
    {
        this.ID = ID;
        FirstName = FN;
        LastName = LN;
        Email = EA;
        PhoneNumber = PN;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
