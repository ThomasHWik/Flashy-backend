package com.flashy.server.service;

import com.flashy.server.core.User;
import com.flashy.server.core.UserDTO;
import com.flashy.server.data.Flashyuser;
import com.flashy.server.exceptions.InvalidLoginException;
import com.flashy.server.repository.FlashyuserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
            Flashyuser savedUser = flashyuserRepository.save(new Flashyuser(user.getUsername(), hashingService.hashPassword(user.getPassword()), 0));
            return savedUser.getIsadmin();
        } else {
            throw new InvalidLoginException();

        }
    }

    public boolean registerAdmin(UserDTO user, String authUsername) throws InvalidLoginException  {
        Flashyuser requestingUser = flashyuserRepository.getFirstByUsername(authUsername);

        if (requestingUser != null && requestingUser.getIsadmin() == 1) {
            Flashyuser dbUser = flashyuserRepository.getFirstByUsername(user.getUsername());
            if (dbUser == null) {
                flashyuserRepository.save(new Flashyuser(user.getUsername(), hashingService.hashPassword(user.getPassword()), 1));
                return true;
            } else {
                throw new InvalidLoginException();
            }
        } else {
            return false;
        }
    }

    @Transactional
    public boolean deleteUser(String username, String tokenusername) {
        Flashyuser requestingUser = flashyuserRepository.getFirstByUsername(tokenusername);

        if (requestingUser != null && (requestingUser.getIsadmin() == 1 || username.equals(tokenusername))) {
            System.out.println("deleted");

            // flashyuserRepository.deleteByUsername(username);
            return true;
        } else {
            return false;
        }

    }

    @Transactional
    public boolean changePassword(UserDTO user, String tokenusername) throws InvalidLoginException {
        Flashyuser requestingUser = flashyuserRepository.getFirstByUsername(tokenusername);
        if (requestingUser != null && (requestingUser.getIsadmin() == 1 || tokenusername.equals(user.getUsername()))) {
            Flashyuser dbUser = flashyuserRepository.getFirstByUsername(user.getUsername());
            if (dbUser != null) {
                dbUser.setPassword(hashingService.hashPassword(user.getPassword()));
                flashyuserRepository.save(dbUser);
                return true;
            } else {
                throw new InvalidLoginException();
            }
        } else {
            return false;
        }
    }

    public List<String> getAllAdmins(String username) {
        Flashyuser requestingUser = flashyuserRepository.getFirstByUsername(username);
        if (requestingUser != null && requestingUser.getIsadmin() == 1) {
            return flashyuserRepository.findWhereIsAdmin().stream().map(x -> x.getUsername()).toList();
        } else {
            return null;
        }
    }
}
