package com.db.controller;

import com.db.entity.JpaPerson;
import com.db.entity.Person;
import com.db.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

    @Autowired
    private PersonService service;

    @GetMapping("/personData")
    public List<Person> getPersonInfo(){ // Here @RestController will convert the Java data i.e. List<Person>, to Json (understood by HTTP) to show in browser
        return  service.getPersonInfo();
    }

    @PostMapping("/addPersonViaBody")
    public int addPersonViaBody(@RequestBody Person person){ // Here @RestController will convert the Json data i.e. body attached with the post req to Java data i.e. Person, to catch the data from body
        return service.addPerson(person);
    }

    @PostMapping("/addPersonViaParam") // http://localhost:6969/addPersonViaParam?name=param&id=3
    public int addPersonViaParam(@RequestParam("name") String name, @RequestParam("id") int id){
        return service.addPerson(new Person(name, id));
    }

    @PostMapping("/addPersonViaPathVar/{id}")  // http://localhost:6969/addPersonViaPathVar/4?name=path
    public int addPersonViaPathVar(@RequestParam("name") String name, @PathVariable("id") int id){
        return service.addPerson(new Person(name, id));
    }

    @PostMapping("/addPersonByJpa")
    public void addPersonByJpa(@RequestBody JpaPerson person){
        service.addPersonByJpa(person);
    }
}
