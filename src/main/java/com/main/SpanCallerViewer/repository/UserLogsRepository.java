package com.main.SpanCallerViewer.repository;

import com.main.SpanCallerViewer.model.UserLogs;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLogsRepository extends MongoRepository<UserLogs, String> {
    void deleteByUserId(String id);
    UserLogs findByUserId(String userId);
}
