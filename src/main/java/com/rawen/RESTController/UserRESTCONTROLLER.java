package com.rawen.RESTController;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.rawen.users.entities.User;
import com.rawen.users.repos.userRepository;
//import com.rawen.users.service.UserService;

@RestController
@CrossOrigin(origins = "*")
public class UserRESTCONTROLLER {
	
	@Autowired
	userRepository userRep;
	
	@RequestMapping(path = "all",method = RequestMethod.GET)
	public List<User> getAllUsers() {
		return userRep.findAll();
	 }
}
