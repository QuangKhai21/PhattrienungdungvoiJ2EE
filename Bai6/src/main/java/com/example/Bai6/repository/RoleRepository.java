package com.example.bai5_tongvutanphat_2280602321.repository;

import com.example.bai5_tongvutanphat_2280602321.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}
