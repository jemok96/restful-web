package com.example.restfulwebservice.controller;

import com.example.restfulwebservice.domain.User;
import com.example.restfulwebservice.exception.UserNotFoundException;
import com.example.restfulwebservice.service.UserService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return userService.findAll();
    }
    @GetMapping("/users/{id}")
    public  ResponseEntity<EntityModel<User>> findOneUser(@PathVariable("id")Integer id){
        User user = userService.findOne(id);
        if(user == null){
            throw new UserNotFoundException("user not found");
        }
        //HATEOAS
        EntityModel entityModel = EntityModel.of(user);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).findOneUser(id)); //이부분에 따라 link달라짐
        entityModel.add(linkTo.withRel("all-users"),linkTo.withSelfRel());
        WebMvcLinkBuilder linkTo2 = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(linkTo2.withRel("All!"));
        return ResponseEntity.ok(entityModel);

    }
    @PostMapping("/users")
    public ResponseEntity<User> saveUser(@Valid @RequestBody User user){
        User savedUser = userService.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){
        User user = userService.deleteById(id);
        if(user ==null){
            throw new UserNotFoundException("user Not Found");
        }
    }
}
