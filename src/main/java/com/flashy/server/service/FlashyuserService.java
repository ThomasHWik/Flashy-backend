package com.flashy.server.service;

import com.flashy.server.core.UserDTO;
import com.flashy.server.data.Flashyuser;
import com.flashy.server.exceptions.InvalidLoginException;
import com.flashy.server.repository.FlashyuserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlashyuserService {

    @Autowired
    FlashyuserRepository flashyuserRepository;

    HashingService hashingService = new HashingService();

    public int loginUser(UserDTO user) throws InvalidLoginException {
        Flashyuser dbUser = flashyuserRepository.getFirstByUsername(user.getUsername());
        if (dbUser != null && hashingService.verifyPassword(user.getPassword(), dbUser.getPassword())) {
            return dbUser.getIsadmin();
        } else {
            throw new InvalidLoginException();
        }
    }

    public int registrerUser(UserDTO user) throws InvalidLoginException {
        Flashyuser dbUser = flashyuserRepository.getFirstByUsername(user.getUsername());
        if (dbUser == null) {
            Flashyuser savedUser = flashyuserRepository.save(new Flashyuser(user.getUsername(), hashingService.hashPassword(user.getPassword()), user.getIsadmin()));
            return savedUser.getIsadmin();
        } else {
            throw new InvalidLoginException();

        }
    }
}
