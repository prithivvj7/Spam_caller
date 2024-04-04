package com.main.SpanCallerViewer.service;

import com.main.SpanCallerViewer.model.Contacts;
import com.main.SpanCallerViewer.model.UserLogs;
import com.main.SpanCallerViewer.repository.UserLogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLogsService {

    @Autowired
    private UserLogsRepository userLogsRepository;

    public void userLoginIn(String id, String key){

        UserLogs log = userLogsRepository.findByUserId(id);
        if(log ==null){
            UserLogs userLogs = new UserLogs();
            userLogs.setUserId(id);
            userLogs.setKey(key);
            userLogsRepository.save(userLogs);
        }else{
            log.setKey(key);
            userLogsRepository.save(log);
        }

    }

    public UserLogs findUserLog(String userId){
        return userLogsRepository.findByUserId(userId);
    }

    public void userLogout(String id){
        userLogsRepository.deleteByUserId(id);
    }
}
