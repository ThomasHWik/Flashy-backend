package com.flashy.server.service;

import com.flashy.server.core.UserDTO;
import com.flashy.server.data.Flashyuser;
import com.flashy.server.repository.FlashyuserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlashyuserService {

    @Autowired
    FlashyuserRepository flashyuserRepository;

    public boolean loginUser(UserDTO user) {
            Flashyuser dbuser = flashyuserRepository.getFirstByUsername(user.getUsername());
            return dbuser != null && dbuser.getPassword().equals(user.getPassword());
    }

    public boolean registrerUser(UserDTO user) {
        Flashyuser dbUser = flashyuserRepository.getFirstByUsername(user.getUsername());
        if (dbUser == null) {
            flashyuserRepository.save(new Flashyuser(user.getUsername(), user.getPassword(), user.getIsadmin()));

        } else {
            return false;
        }
        return true;
    }
}
