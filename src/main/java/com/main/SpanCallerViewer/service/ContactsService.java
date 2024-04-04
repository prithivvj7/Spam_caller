package com.main.SpanCallerViewer.service;

import com.main.SpanCallerViewer.model.Contacts;
import com.main.SpanCallerViewer.model.SampleData;
import com.main.SpanCallerViewer.model.UserDetails;
import com.main.SpanCallerViewer.model.UserLogs;
import com.main.SpanCallerViewer.repository.ContactRepository;
import com.main.SpanCallerViewer.repository.UserRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ContactsService {

    private final MongoTemplate mongoTemplate;
    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserLogsService userLogsService;


    public ContactsService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public boolean userLoggedInOrNot(String key, String id) {
        UserLogs log = userLogsService.findUserLog(id);
        if (log != null) {
            if (log.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }


    public ResponseEntity addAllContact(List<SampleData> datas, String userId) {
        Contacts contacts = new Contacts();
        for (SampleData data : datas) {
            contacts.setName(data.getName());
            contacts.setNumber(data.getNumber());
            contacts.setSpam(0);
            contacts.setUserId(userId);
            contactRepository.save(contacts);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Contact added.");
    }

    public void userAddContact(String name, String number, String userId) {
        Contacts contacts = new Contacts();
        contacts.setName(name);
        contacts.setNumber(number);
        contacts.setSpam(0);
        contacts.setUserId(userId);
        contactRepository.save(contacts);
    }

    public ResponseEntity findAllByNumber(String number) {
        UserDetails user = userRepository.findByNumber(number);

        if (user == null) {
            List<Contacts> contactsList = contactRepository.findAllByNumber(number);
            return ResponseEntity.status(HttpStatus.OK).body(contactsList);
        } else {
            HashMap<String, String > response = new HashMap<>();
            response.put("name", user.getUsername());
            response.put("email",user.getEmail());
            response.put("number",user.getNumber());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    public ResponseEntity filterContactByNames(String name) {
        List<Document> pipeline = List.of(
                new Document("$match", new Document("name", new Document("$regex", "^" + name))),
                new Document("$addFields", new Document("startsWithString", new Document("$cond",
                        List.of(new Document("$eq", List.of(new Document("$substr", List.of("$name", 0, name.length())), name)),
                                1, 0)))),
                new Document("$sort", new Document("startsWithString", -1)),
                new Document("$project", new Document("name", 1).append("number", 1).append("spam", 1).append("_id", 0))
        );

        return ResponseEntity.status(HttpStatus.OK).body(mongoTemplate.getCollection("contacts").aggregate(pipeline).into(new ArrayList<>()));
    }

    public ResponseEntity addToSpan(String number, String name) {
        List<Contacts> contacts = contactRepository.findAllByNumber(number);
        if (contacts != null) {
            for (Contacts contact : contacts) {
                if (name.equals(contact.getName())) {
                    contact.setSpam(contact.getSpam() + 1);
                    contactRepository.save(contact);
                    return ResponseEntity.status(HttpStatus.OK).body("Number added to span.");

                }
            }
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(contactRepository.findAllByNumber(number));
    }

}
