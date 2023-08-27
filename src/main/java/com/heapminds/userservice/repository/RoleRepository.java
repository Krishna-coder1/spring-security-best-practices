package com.heapminds.userservice.repository;


import java.util. Optional;

import com.heapminds.userservice.models.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Integer> {
Optional<Roles> findByName(String name);
}