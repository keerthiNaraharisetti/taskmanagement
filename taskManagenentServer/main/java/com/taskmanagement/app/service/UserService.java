package com.taskmanagement.app.service;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.taskmanagement.app.repository.UserRepository;
import com.taskmanagement.app.request.UserRequest;
import com.taskmanagement.app.exception.ResourceNotFoundException;
import com.taskmanagement.app.exception.BadRequestException;
import com.taskmanagement.app.mapper.UserMapper;
import com.taskmanagement.app.model.Role;
import com.taskmanagement.app.model.User;
import org.springframework.context.annotation.Lazy;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service(value = "userService")
@RequiredArgsConstructor(onConstructor = @__({@Autowired, @Lazy}))
public class UserService implements UserDetailsService{

    private final RoleService roleService;

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bcryptEncoder;

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    } 

    public User create(User user) {
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        Role role = roleService.findByName("USER");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        if(user.getUsername().split("-")[1].contains("admin")){
            role = roleService.findByName("ADMIN");
            roleSet.add(role);
        }
        user.setRoles(roleSet);
        return userRepository.save(user);
    }

    public User update(UserRequest userRequest, Long id) throws ResourceNotFoundException, BadRequestException {
        User user = find(id);
        if(!userRequest.getPassword().isEmpty())
            userRequest.setPassword(bcryptEncoder.encode(userRequest.getPassword()));
        userMapper.merge(userRequest, user);
        user.setId(id);
        return userRepository.save(user);
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getPrincipal().toString();
            return this.userRepository.findByUsername(username);
        }
        return null;
    }

    public User find(Long id) throws ResourceNotFoundException, BadRequestException {
        User currentUser = getCurrentUser();
        if(currentUser.hasRole("ADMIN")){
            return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User is not found with id: " + id));
        }else if (currentUser.getId() == id){
            return currentUser;
        }else{
            throw new BadRequestException("You are not authorized to access user with id : " + id);
        }
    }
    
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public void destroy(Long id) throws ResourceNotFoundException {
        User user = find(id);
        userRepository.delete(user);
    }
}