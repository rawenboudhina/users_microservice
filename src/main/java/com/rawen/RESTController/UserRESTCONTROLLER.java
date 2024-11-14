package com.rawen.RESTController;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.rawen.users.entities.User;
import com.rawen.users.repos.userRepository;
import com.rawen.users.service.UserService;
import com.rawen.users.service.register.RegistrationRequest;
@RequestMapping("/users")
@RestController
@CrossOrigin(origins = "*")
public class UserRestController {
	
	@Autowired
	userRepository userRep;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(path = "all",method = RequestMethod.GET)
	public List<User> getAllUsers() {
		return userRep.findAll();
	 }
	
	
	@PostMapping("/register")
	public User register(@RequestBody RegistrationRequest request) {
		return userService.registerUser(request);
		
	}
	
	

	
	
}