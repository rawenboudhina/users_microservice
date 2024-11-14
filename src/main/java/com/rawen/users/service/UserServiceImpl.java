package com.rawen.users.service;
import java.util.Optional;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rawen.users.entities.Role;
import com.rawen.users.entities.User;
import com.rawen.users.repos.RoleRepository;
import com.rawen.users.repos.userRepository;
import com.rawen.users.service.exceptions.EmailAlreadyExistsException;
import com.rawen.users.service.register.RegistrationRequest;
import com.rawen.users.service.register.VerificationToken;
import com.rawen.users.service.register.VerificationTokenRepository;
import com.rawen.users.util.EmailSender;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private userRepository userRep;

    @Autowired
    private RoleRepository roleRep;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired

    VerificationTokenRepository verificationTokenRepo;
    
    @Autowired
    EmailSender emailSender;
    @Override
    @Transactional
    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRep.save(user);
    }

	/*
	 * @Override
	 * 
	 * @Transactional public User addRoleToUser(String username, String rolename) {
	 * User usr = userRep.findByUsername(username); Role role =
	 * roleRep.findByRole(rolename);
	 * 
	 * // Ensure both user and role exist if (usr != null && role != null) {
	 * usr.getRoles().add(role); userRep.save(usr); }
	 * 
	 * return usr; }
	 */
	@Override
	public User addRoleToUser(String username, String rolename) {
		
		User usr = userRep.findByUsername(username);
		Role role = roleRep.findByRole(rolename);
		usr.getRoles().add(role);
		return usr;
	}

    @Override
    @Transactional
    public Role addRole(Role role) {
        return roleRep.save(role);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRep.findByUsername(username);
    }

	@Override
	public List<User> findAllUsers() {
		return userRep.findAll();
	}

	
	

@Override 
 public User registerUser(RegistrationRequest request) { 
  Optional<User> optionaluser = userRep.findByEmail(request.getEmail()); 
  if(optionaluser.isPresent()) 
   throw new EmailAlreadyExistsException("email déjà existant!"); 
   
     User newUser = new User(); 
        newUser.setUsername(request.getUsername()); 
        newUser.setEmail(request.getEmail()); 
        newUser.setPassword(bCryptPasswordEncoder.encode(request.getPassword())); 
        newUser.setEnabled(false); 
 
        //ajouter à newUser le role par défaut USER 
        Role r = roleRep.findByRole("USER"); 
        List<Role> roles = new ArrayList<>(); 
        roles.add(r); 
        newUser.setRoles(roles); 
       
        userRep.save(newUser); 
        
        //génére le code secret  
        String code = this.generateCode(); 
         
        VerificationToken token = new VerificationToken(code, newUser); 
		verificationTokenRepo.save(token); 
		//envoyer par email pour valider l'email de l'utilisateur  
		sendEmailUser(newUser,token.getToken());
		
         
   
        return newUser; 
 } 
  
  
  
 
public String  generateCode() { 
     Random random = new Random(); 
     Integer code = 100000 + random.nextInt(900000);  
     return code.toString(); 
	
}
@Override

public void sendEmailUser(User u, String code) { 
    String emailBody ="Bonjour "+ "<h1>"+u.getUsername() +"</h1>" + 
       " Votre code de validation est "+"<h1>"+code+"</h1>";   
  emailSender.sendEmail(u.getEmail(), emailBody); 
 }
}
