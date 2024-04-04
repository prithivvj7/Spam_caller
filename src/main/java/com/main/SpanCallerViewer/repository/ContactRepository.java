package com.main.SpanCallerViewer.repository;

import com.main.SpanCallerViewer.model.Contacts;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends MongoRepository<Contacts, String> {
    List<Contacts> findAllByNumber (String number);
}
