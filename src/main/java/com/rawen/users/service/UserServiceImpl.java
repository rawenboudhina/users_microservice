package com.rawen.users.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rawen.users.entities.Role;
import com.rawen.users.entities.User;
import com.rawen.users.repos.RoleRepository;
import com.rawen.users.repos.userRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private userRepository userRep;

    @Autowired
    private RoleRepository roleRep;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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

	
}
