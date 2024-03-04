package com.flashy.server.service;

import com.flashy.server.data.Carddeck;
import com.flashy.server.data.Flashyuser;
import com.flashy.server.data.dataviews.Extendedcarddeckview;
import com.flashy.server.repository.CarddeckRepository;
import com.flashy.server.repository.FlashyuserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.flashy.server.data.Comment;

/**
    * This class is used to check permissions and access rights of the user to avoid too much repetition in other classes
 */
@Service
public class AccessService {
    public boolean hasFullAccessToCarddeck(Flashyuser flashyuser, Carddeck carddeck) {
        return carddeck.getFlashyuserid() == flashyuser.getId() || flashyuser.getIsadmin() == 1;
          }

   public boolean hasFullAccessToComment(Flashyuser flashyuser, Comment comment) {
        return comment.getFlashyuserid() == flashyuser.getId() || flashyuser.getIsadmin() == 1;
    }

    public boolean hasFullAccessToUser(Flashyuser requestingUser, Flashyuser flashyuser) {
        return requestingUser.getId() == flashyuser.getId() || flashyuser.getIsadmin() == 1;
    }

    public boolean hasCommentAccessOnCarddeck(Flashyuser flashyuser, Carddeck carddeck) {
        return carddeck.getIsprivate() == 0 || carddeck.getFlashyuserid() == flashyuser.getId();
    }


    public boolean hasCarddeckViewAccess(Flashyuser user, Extendedcarddeckview deck) {
        return deck.getIsprivate() == 0 || user.getId() == deck.getFlashyuserid() || user.getIsadmin() == 1;
    }
}
