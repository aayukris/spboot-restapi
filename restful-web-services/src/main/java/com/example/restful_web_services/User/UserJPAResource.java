package com.example.restful_web_services.User;

import com.example.restful_web_services.jpa.PostRepository;
import com.example.restful_web_services.jpa.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserJPAResource {
    private UserRepository userRepository;
    private PostRepository postRepository;

    public UserJPAResource(UserRepository userRepository,PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("/jpa/getall")
    public List<User> retrieveAllUsers(){

        return userRepository.findAll();
    }

    @GetMapping("/jpa/getall/{id}")
    public EntityModel<User> retrieveOneUser(@PathVariable int id){

        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
            throw new UserNotFoundException("id"+id);

        EntityModel<User> entityModel = EntityModel.of(user.get());
        WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(link.withRel("all-Users"));
        return entityModel;
    }
    @GetMapping("/jpa/getall/{id}/posts")
    public List<Post> retrieveUserPost(@PathVariable int id){

        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
            throw new UserNotFoundException("id"+id);
        System.out.println(user.get());

        return user.get().getPosts();

    }

    @DeleteMapping("/jpa/getall/{id}")
    public void deleteOneUser(@PathVariable int id){

        userRepository.deleteById(id);
    }

    @PostMapping("/jpa/create")
    public ResponseEntity<User> CreateUser(@Valid @RequestBody User user){
        User saveduser = userRepository.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
                buildAndExpand(saveduser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/jpa/create/{id}/posts")
    public ResponseEntity<Post> CreatePostUser(@PathVariable int id,@Valid @RequestBody Post post ){

        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
            throw new UserNotFoundException("id"+id);

        post.setUser(user.get());
        Post savedpost = postRepository.save(post);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
                buildAndExpand(savedpost.getId()).toUri();
        return ResponseEntity.created(location).build();


    }
}


