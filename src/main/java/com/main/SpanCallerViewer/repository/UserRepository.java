package com.main.SpanCallerViewer.repository;

import com.main.SpanCallerViewer.model.UserDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserDetails, String> {
    UserDetails findByNumber(String number);
}
