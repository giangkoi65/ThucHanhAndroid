package com.example.firebase;

public class Student {
    private String id;
    private String name;
    private String email;

    // Default constructor required for calls to DataSnapshot.getValue(Student.class)
    public Student() {
    }

    // Parameterized constructor
    public Student(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // Getter for id
    public String getId() {
        return id;
    }

    // Setter for id
    public void setId(String id) {
        this.id = id;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for email
    public String getEmail() {
        return email;
    }

    // Setter for email
    public void setEmail(String email) {
        this.email = email;
    }
}
