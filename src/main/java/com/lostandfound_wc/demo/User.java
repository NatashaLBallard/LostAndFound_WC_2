package com.lostandfound_wc.demo;


import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="email",nullable=false)
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="enabled")
    private boolean enabled;

    @Column(name="username")
    public String username;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(joinColumns=@JoinColumn(name = "user_id"),
            inverseJoinColumns=@JoinColumn(name="role_id"))
    private Collection<Role> roles;




    @OneToMany
            (mappedBy = "users", cascade = CascadeType.ALL)

    private Set<Item> myItems;

    public User() {
        this.myItems = new HashSet<>();
    }




//    public Set<Item> myItems;
//
//    @OneToMany
//    (mappedBy = "users", cascade = CascadeType.ALL)
//    public Set<Item> getMyItems(){
//        return myItems;
//    }


//    public User() {
//        this.myItems = new HashSet<>();
//    }










    public User(String email, String password, String firstName, String lastName, boolean enabled, String username) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.enabled = enabled;
        this.username = username;
    }

//    public User() {
//    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }









    public Set<Item> getMyItems(){
        return myItems;
    }

    public void setMyItems(Set<Item> myItems){
        this.myItems = myItems;
    }



    public void addItem(Item item)
    {
                this.myItems.add(item);
            }
}

