package com.main.SpanCallerViewer.controller;

import com.main.SpanCallerViewer.model.SampleData;
import com.main.SpanCallerViewer.service.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contacts")
public class ContactsController {

    @Autowired
    private ContactsService service;

    @GetMapping("/find/{name}")
    public ResponseEntity findByName(@RequestHeader String key, @RequestHeader String userid, @PathVariable String name) {
        if (service.userLoggedInOrNot(key, userid)) {
            return service.filterContactByNames(name);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not logged.");
    }

    @GetMapping("/{number}")
    public ResponseEntity findBynumber(@RequestHeader String key, @RequestHeader String userid, @PathVariable String number) {
        if (service.userLoggedInOrNot(key, userid)) {
            return service.findAllByNumber(number);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not logged.");
    }

    @PostMapping("/addSampleData")
    public ResponseEntity addSampleDatas(@RequestBody List<SampleData> datas, @RequestHeader String userid, @RequestHeader String key){
        if (service.userLoggedInOrNot(key, userid)) {
            return service.addAllContact(datas,userid);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not logged.");
    }

    @PostMapping("/addToSpam/{name}/{number}")
    public ResponseEntity addToSpam(@RequestHeader String key, @RequestHeader String userid, @PathVariable String number,@PathVariable String name){
        if (service.userLoggedInOrNot(key, userid)) {
            return service.addToSpan(number,name);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not logged.");
    }
}
