package com.rawen.users.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rawen.users.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByRole(String role);
}
