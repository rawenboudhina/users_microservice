package com.rawen.users;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.rawen.users.entities.Role;
import com.rawen.users.entities.User;
import com.rawen.users.service.UserService;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@ComponentScan(basePackages = "com.rawen.users")

public class UsersMicroserviceApplication {
	@Autowired
	UserService userService;
	


	public static void main(String[] args) {
		SpringApplication.run(UsersMicroserviceApplication.class, args);}
	
	/*
	 * @PostConstruct void init_users() { //ajouter les rôles
	 * userService.addRole(new Role(null,"ADMIN")); userService.addRole(new
	 * Role(null,"USER")); userService.addRole(new Role(null,"AGENT"));
	 * 
	 * //ajouter les users userService.saveUser(new
	 * User(null,"admin","123",true,null)); userService.saveUser(new
	 * User(null,"rawen","123",true,null)); userService.saveUser(new
	 * User(null,"ahmed","123",true,null)); //ajouter les rôles aux users
	 * userService.addRoleToUser("admin", "ADMIN");
	 * userService.addRoleToUser("admin", "USER");
	 * 
	 * userService.addRoleToUser("rawen", "USER");
	 * userService.addRoleToUser("ahmed", "AGENT"); }
	 */
	/*@Transactional
	@PostConstruct
	void init_users() {
	    // Check if the user 'admin' already exists
	    User existingAdmin = userService.findUserByUsername("admin");

	    // If 'admin' doesn't exist, create and save the user
	    if (existingAdmin == null) {
	        User adminUser = new User(null, "admin", "123", true, null);
	        userService.saveUser(adminUser);

	        // Add both 'ADMIN' and 'USER' roles to the 'admin' user
	        userService.addRoleToUser("admin", "ADMIN");
	        userService.addRoleToUser("admin", "USER");
	    } else {
	        // Check if the 'USER' role is already assigned to 'admin'
	        boolean userRoleExists = existingAdmin.getRoles().stream()
	                .anyMatch(role -> role.getRole().equals("USER"));

	        // If 'USER' role doesn't exist, add it
	        if (!userRoleExists) {
	            userService.addRoleToUser("admin", "USER");
	        }
	    }
	}



*/
		@Bean
		BCryptPasswordEncoder getBCE() {
		return new BCryptPasswordEncoder();
		}


}
