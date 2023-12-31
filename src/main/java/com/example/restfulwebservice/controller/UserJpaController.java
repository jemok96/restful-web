package com.example.restfulwebservice.controller;

import com.example.restfulwebservice.domain.Post;
import com.example.restfulwebservice.domain.Usertable;
import com.example.restfulwebservice.exception.UserNotFoundException;
import com.example.restfulwebservice.repository.PostRepository;
import com.example.restfulwebservice.repository.UserRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/jpa")
public class UserJpaController {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public UserJpaController(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("/users")
    public List<Usertable> retrieveAllUsers() {
        List<Usertable> users = userRepository.findAll();
        return users;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<EntityModel<Optional<Usertable>>> retrieveUser(@PathVariable int id) {
        Optional<Usertable> user = userRepository.findById(id);

        if (!user.isPresent()) {
            throw new UserNotFoundException("user not Found");
        }
        EntityModel<Optional<Usertable>> entityModel = EntityModel.of(user);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveUser(id));
        entityModel.add(linkTo.withRel("user " + id));
        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Integer id) {
        userRepository.deleteById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<Usertable> createUser(@Valid @RequestBody Usertable user) {
        Usertable savedUser = userRepository.save(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/users/{id}/posts")
    public List<Post> allPostByUser(@PathVariable int id) {
        Optional<Usertable> user = userRepository.findById(id);

        if (!user.isPresent()) {
            throw new UserNotFoundException("user not Found");
        }
        return user.get().getPosts();
    }

    @PostMapping("/users/{id}/posts")
    public ResponseEntity<Post> createPost(@PathVariable int id, @RequestBody Post post) {
        Optional<Usertable> findUser = userRepository.findById(id);
        if (!findUser.isPresent()) {
            throw new UserNotFoundException("user not Found");
        }
        Post newPost = Post.builder().
                description(post.getDescription())
                .user(findUser.get())
                .build();
        Post save = postRepository.save(newPost);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(save.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }
    @GetMapping("/users/{userId}/posts/{postId}")
    public List<Post> findPostById(@PathVariable Integer userId, @PathVariable Integer postId){
        Optional<Usertable> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new UserNotFoundException("user not Found");
        }
        System.out.println("user = " + user);


        List<Post> posts = user.get().getPosts();
        return posts;
    }
}
