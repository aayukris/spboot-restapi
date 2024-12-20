package com.example.restful_web_services.User;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserResource {
    @Autowired
    private UserDaoService userDaoService;

    public UserResource(UserDaoService userDaoService) {

        this.userDaoService = userDaoService;
    }

    @GetMapping("/getall")
    public List<User> retrieveAllUsers(){
        return userDaoService.findAll();
    }

    @GetMapping("/getall/{id}")
    public EntityModel<User> retrieveOneUser(@PathVariable int id){

        User user = userDaoService.findOne(id);
        if(user==null)
            throw new UserNotFoundException("id"+id);

        EntityModel<User> entityModel = EntityModel.of(user);
        WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(link.withRel("all-Users"));
        return entityModel;
    }

    @DeleteMapping("/getall/{id}")
    public void deleteOneUser(@PathVariable int id){
        userDaoService.deleteOne(id);
    }

    @PostMapping("/create")
    public ResponseEntity<User> CreateUser(@Valid @RequestBody User user){
        User saveduser = userDaoService.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
                buildAndExpand(saveduser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}
