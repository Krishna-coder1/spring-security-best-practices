package com.heapminds.userservice.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="users")
@Data
@Entity
@NoArgsConstructor
public class UserEntity {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private int id;
     private String username;
     private String password;
     
     @ManyToMany(fetch = FetchType.EAGER)
     @JoinTable(name="user_roles", joinColumns = @JoinColumn(name="user_id", referencedColumnName = "id"),
     inverseJoinColumns = @JoinColumn(name="role_id", referencedColumnName = "id"))
     private List<Roles> roles = new ArrayList<>();
}
