package com.main.SpanCallerViewer.service;

import com.main.SpanCallerViewer.model.Contacts;
import com.main.SpanCallerViewer.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonService {
    @Autowired
    private ContactRepository contactRepository;
    public void userAddContact(String name, String number, String userId) {
        Contacts contacts = new Contacts();
        contacts.setName(name);
        contacts.setNumber(number);
        contacts.setSpam(0);
        contacts.setUserId(userId);
        contactRepository.save(contacts);
    }

}
