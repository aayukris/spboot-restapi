package com.example.restful_web_services.Versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersioningPersonController {

    @GetMapping("/v1/person")
    public PersonV1 getFirstVersion(){
        return new PersonV1("Bob Charlie");
    }

    @GetMapping("/v2/person")
    public PersonV2 getSecondVersion(){
        return new PersonV2(new Name("Bob","Charlie"));
    }
    @GetMapping(path = "/person",params = "version=1")
    public PersonV1 getFirstVersionParam(){
        return new PersonV1("Bob Charlie");
    }

    @GetMapping(path = "/person",params = "version=2")
    public PersonV2 getSecondVersionParam(){
        return new PersonV2(new Name("Bob","Charlie"));
    }
}
