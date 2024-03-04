package com.flashy.server.service;

import com.flashy.server.core.CommentDTO;
import com.flashy.server.data.Carddeck;
import com.flashy.server.data.Flashyuser;
import com.flashy.server.data.dataviews.Extendedcommentview;
import com.flashy.server.repository.CarddeckRepository;
import com.flashy.server.repository.CommentRepository;
import com.flashy.server.repository.FlashyuserRepository;
import com.flashy.server.repository.views.ExtendedcommentviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flashy.server.data.Comment;

import java.util.List;
import java.util.UUID;

@Service
public class CommentService {
    @Autowired
    private FlashyuserRepository flashyuserRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CarddeckRepository carddeckRepository;

    @Autowired
    private ExtendedcommentviewRepository extendedcommentviewRepository;

    @Autowired
    private AccessService accessService;

    public CommentDTO createComment(CommentDTO commentDTO, String username) {
        Flashyuser dbuser = flashyuserRepository.getFirstByUsername(username);
        Carddeck dbcarddeck = carddeckRepository.getFirstByUuid(commentDTO.getCarddeckuuid());

        if (dbuser != null && dbcarddeck != null && commentDTO.getComment() != null && !commentDTO.getComment().isEmpty() && accessService.hasCommentAccessOnCarddeck(dbuser, dbcarddeck)) {
            Comment c = commentRepository.save(new Comment(commentDTO.getComment(), dbuser.getId(), dbcarddeck.getId(), UUID.randomUUID().toString()));
            return new CommentDTO(c.getComment(), dbuser.getUsername(), dbcarddeck.getUuid(), c.getUuid(), c.getCreatedat().toString());
        } else {
            return null;
        }

    }

    public List<CommentDTO> getComments(String username, String carddeckuuid) {
        Flashyuser dbuser = flashyuserRepository.getFirstByUsername(username);
        if (dbuser != null && accessService.hasCommentAccessOnCarddeck(dbuser, carddeckRepository.getFirstByUuid(carddeckuuid))) {
            List<Extendedcommentview> comments = extendedcommentviewRepository.findAllByCarddeckuuid(carddeckuuid);
            return comments.stream().map(c -> new CommentDTO(c.getComment(), c.getUsername(), c.getCarddeckuuid(), c.getUuid(), c.getCreatedat().toString())).toList();
        } else {
            return null;
        }
    }

    public boolean deleteComment(String username, String commentuuid) {
        Flashyuser dbuser = flashyuserRepository.getFirstByUsername(username);
        Comment dbcomment = commentRepository.getFirstByUuid(commentuuid);

        if (dbuser != null && dbcomment != null && accessService.hasFullAccessToComment(dbuser, dbcomment)) {
            commentRepository.delete(dbcomment);
            return true;
        } else {
            return false;
        }
    }
}
