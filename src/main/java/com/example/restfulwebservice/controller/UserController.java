package com.example.restfulwebservice.controller;

import com.example.restfulwebservice.domain.User;
import com.example.restfulwebservice.exception.UserNotFoundException;
import com.example.restfulwebservice.service.UserService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
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

    /**
     * HATEOAS 적용
     * UserInfo Filter랑 HATEOAS 둘 다 적용하려면 어떻게?? 뭔 반환해야하지
     */
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
    

    /**
     * Status : 201 Created
     * Headers  Location : http://localhost:8080/users/4
     */
    @PostMapping("/users")
    public ResponseEntity<User> saveUser(@Valid @RequestBody User user){
        User savedUser = userService.save(user);


        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    /**
     * CustmizeExceptionHandler에서 처리
     */
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){
        User user = userService.deleteById(id);
        if(user ==null){
            throw new UserNotFoundException("user Not Found");
        }
    }
}
