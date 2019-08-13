package com.weekender;

class User {

    String firstname,lastname,email;

    public User(String firstname, String lastname, String email) {
     this.firstname=firstname;
     this.lastname=lastname;
     this.email=email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }
}
