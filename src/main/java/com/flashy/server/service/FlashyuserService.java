package com.flashy.server.service;

import com.flashy.server.core.LoginResponseDTO;
import com.flashy.server.core.User;
import com.flashy.server.core.UserDTO;
import com.flashy.server.data.Carddeck;
import com.flashy.server.data.Flashyuser;
import com.flashy.server.data.Userhasfavorite;
import com.flashy.server.data.Userhaslike;
import com.flashy.server.exceptions.InvalidLoginException;
import com.flashy.server.repository.*;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FlashyuserService {

    @Autowired
    FlashyuserRepository flashyuserRepository;

    @Autowired
    FlashcardRepository flashcardRepository;

    @Autowired
    CarddeckRepository carddeckRepository;

    @Autowired
    UserhasfavoriteRepository userhasfavoriteRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserhaslikeRepository userhaslikeRepository;

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
        if (dbUser == null && !user.getUsername().isEmpty()) {
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
        Flashyuser deletedUser = flashyuserRepository.getFirstByUsername(username);
        if (requestingUser != null && deletedUser != null && (requestingUser.getIsadmin() == 1 || username.equals(tokenusername))) {
            userhaslikeRepository.deleteByFlashyuserid(deletedUser.getId());
            userhasfavoriteRepository.deleteByFlashyuserid(deletedUser.getId());
            commentRepository.deleteByFlashyuserid(deletedUser.getId());
            List<Carddeck> decks = carddeckRepository.getAllByFlashyuseridEqualsAndAuthorized(deletedUser.getId());
            for (Carddeck d : decks) {
                flashcardRepository.deleteByCarddeckid(d.getId());
            }
            carddeckRepository.deleteByFlashyuserid(deletedUser.getId());

            flashyuserRepository.deleteByUsername(username);
            return true;
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



    public LoginResponseDTO changeUser(UserDTO newuserinfo, String username, String requestingusername) throws InvalidLoginException{
        if (newuserinfo.getUsername() == null || newuserinfo.getUsername().isEmpty()) {
            System.out.println(0);
            throw new InvalidLoginException();
        }


        Flashyuser requestingUser = flashyuserRepository.getFirstByUsername(requestingusername);
        if (requestingUser != null && (requestingUser.getIsadmin() == 1 || requestingUser.getUsername().equals(username))) {
            Flashyuser dbUser = flashyuserRepository.getFirstByUsername(username);
            if (dbUser != null) {
                Flashyuser usernameTaken = flashyuserRepository.getFirstByUsername(newuserinfo.getUsername());
                if (usernameTaken != null && !usernameTaken.getUsername().equals(username)) {
                    throw new InvalidLoginException();
                }

                dbUser.setUsername(newuserinfo.getUsername());
                if (newuserinfo.getPassword() != null && !newuserinfo.getPassword().isEmpty()) {
                    dbUser.setPassword(hashingService.hashPassword(newuserinfo.getPassword()));
                }
                flashyuserRepository.save(dbUser);
                return new LoginResponseDTO("", dbUser.getIsadmin());
            } else {
                System.out.println(2);
                throw new InvalidLoginException();
            }
        } else {
            return null;
        }
    }
}
