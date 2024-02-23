package com.webtask.springboot.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="users")
public class User{

    @Id
    @GeneratedValue()
    private long id;

    private String username;
    @JsonIgnore
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Task> tasks;

    private String roles;

//    public User(String username, String password, String role) {
//        this.id = Long.parseLong(null);
//        this.username = username;
//        this.password = password;
//        this.roles = role;
//    }

//    public User(String username) {
//        this.id= Long.parseLong(null);
//        this.username = username;
//        this.password=null;
//    }
}
