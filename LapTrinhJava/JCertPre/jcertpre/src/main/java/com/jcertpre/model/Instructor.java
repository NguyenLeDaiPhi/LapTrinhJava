package com.jcertpre.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "instructor")
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "description", length = 300)
    private String description;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "password", length = 100)
    private String password;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "login", length = 100)
    private String login;

    public int getId() { return id;}
    public void setId(int id) { this.id = id; } 
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public byte[] getImage() { return image; }
    public void setImage(byte[] image) { this.image = image; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
}
