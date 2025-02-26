package com.example.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/api/1.0/users")
  public User createUser(@RequestBody UserDTO userDTO){
    User user = new User();
    user.setUsername(userDTO.getName());
    user.setEmail(userDTO.getEmail());
    // set other fields as needed
    return this.userService.save(user);
  }

  @PutMapping("/api/1.0/users/{id}")
  @PreAuthorize("@userAuthorizationService.canUpdate(principal.user.id, #id) or hasRole('ROLE_admin')")
  public User updateUser(@PathVariable long id, @RequestBody UserDTO userDTO){
    User user = new User();
    user.setUsername(userDTO.getName());
    user.setEmail(userDTO.getEmail());
    // set other fields as needed
    return this.userService.updateUser(id, user);
  }
  
}
