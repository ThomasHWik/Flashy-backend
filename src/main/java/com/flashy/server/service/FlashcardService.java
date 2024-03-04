package com.flashy.server.service;

import com.flashy.server.core.*;
import com.flashy.server.data.dataviews.Extendedcarddeckview;
import com.flashy.server.data.*;
import com.flashy.server.data.dataviews.Extendedcommentview;
import com.flashy.server.repository.*;

import com.flashy.server.repository.views.ExtendedcarddeckviewRepository;
import com.flashy.server.repository.views.ExtendedcommentviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class FlashcardService {

    @Autowired
    private FlashcardRepository flashcardRepository;

    @Autowired
    private CarddeckRepository carddeckRepository;

    @Autowired
    private FlashyuserRepository flashyuserRepository;

    @Autowired
    private UserhasfavoriteRepository userhasfavoriteRepository;

    @Autowired
    private UserhaslikeRepository userhaslikeRepository;

    @Autowired
    private ExtendedcarddeckviewRepository extendedcarddeckviewRepository;

    @Autowired
    private ExtendedcommentviewRepository extendedcommentviewRepository;

    @Autowired
    private AccessService accessService;

    private boolean createFlashcards(FlashcardDeck flashcardDeck, int carddeckId) {
        try {
            flashcardDeck.setCards(flashcardDeck.getCards().stream()
                    .filter(x -> (x.getQuestion() == null || !x.getQuestion().isEmpty())
                            && (x.getQuestion() == null || !x.getAnswer().isEmpty()))
                    .toList());

            for (FlashcardDTO f : flashcardDeck.getCards()) {
                if (f.getQuestion() == null) {
                    f.setQuestion("");
                }
                if (f.getAnswer() == null) {
                    f.setAnswer("");
                }
            }

            flashcardRepository.saveAll(flashcardDeck.getCards().stream()
                    .map(x -> new Flashcard(x.getQuestion(), x.getAnswer(), UUID.randomUUID().toString(), carddeckId))
                    .toList());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String createCarddeck(FlashcardDeck flashcardDeck, String username) {

        Flashyuser user = flashyuserRepository.getFirstByUsername(username);
        if (user == null) {
            return null;
        } else if (!flashcardDeck.getName().isEmpty()) {
            Carddeck deck = carddeckRepository.save(new Carddeck(UUID.randomUUID().toString(), flashcardDeck.getName(),
                    flashcardDeck.getIsprivate(), user.getId()));

            boolean success = createFlashcards(flashcardDeck, deck.getId());
            if (!success) {
                carddeckRepository.deleteById(deck.getId());
                return null;
            }
            return deck.getUuid();
        } else {
            return null;
        }
    }

    public ExtendedCarddeckDTO getCarddeck(String username, String uuid) {
        Extendedcarddeckview deck = extendedcarddeckviewRepository.getFirstByUuid(uuid);
        Flashyuser dbuser = flashyuserRepository.getFirstByUsername(username);
        if (deck != null && dbuser != null && accessService.hasCarddeckViewAccess(dbuser, deck)) {
            List<Flashcard> cards = flashcardRepository.findAllByCarddeckid(deck.getId());
            List<FlashcardDTO> dtocards = cards.stream()
                    .map(x -> new FlashcardDTO(x.getQuestion(), x.getAnswer(), x.getUuid())).toList();
            Flashyuser user = flashyuserRepository.getById(deck.getFlashyuserid());

            List<Extendedcommentview> comments = extendedcommentviewRepository.findAllByCarddeckuuid(deck.getUuid());

            ExtendedCarddeckDTO res = new ExtendedCarddeckDTO(deck.getTitle(), dtocards, deck.getIsprivate(), deck.getUuid(),
                    user != null ? user.getUsername() : "", deck.getCardcount(), deck.getLikecount(), deck.getFavoritecount());
            res.setComments(comments.stream().map(x -> new CommentDTO(x.getComment(), x.getUsername(), x.getCarddeckuuid(), x.getUuid(), x.getCreatedat().toString())).toList());
            return res;
        }
        return null;
    }

    public CarddeckListDTO getUserDecks(String username, Boolean isAuthorized) {
        Flashyuser dbUser = flashyuserRepository.getFirstByUsername(username);
        if (dbUser != null) {
            List<Carddeck> storedDecks = isAuthorized
                    ? carddeckRepository.getAllByFlashyuseridEqualsAndAuthorized(dbUser.getId())
                    : carddeckRepository.getAllByFlashyuseridEqualsAndNotAuthorized(dbUser.getId());
            List<CarddeckDTO> dtoDecks = storedDecks.stream()
                    .map(x -> new CarddeckDTO(x.getTitle(), null, x.getIsprivate(), x.getUuid(), username)).toList();
            return new CarddeckListDTO(username, dtoDecks, isAuthorized ? 1 : 0);
        } else {
            return null;
        }

    }

    @Transactional
    public boolean editCarddeck(FlashcardDeck flashcardDeck, String username) {

        Flashyuser dbUser = flashyuserRepository.getFirstByUsername(username);
        Carddeck dbDeck = carddeckRepository.getFirstByUuid(flashcardDeck.getUuid());
        if (dbDeck == null) {

            return false;
        }
        System.out.println(dbDeck.getIsprivate());
        dbDeck.setTitle(flashcardDeck.getName());
        if (dbUser != null && (dbUser.getIsadmin() == 1 || dbDeck.getFlashyuserid() == dbUser.getId())) {
            dbDeck.setIsprivate(flashcardDeck.getIsprivate());
        } else if (dbDeck.getIsprivate() == 1) {
            // return false only if the deck is private and is being edited by a person not
            // owner or admin
            return false;
        }

        carddeckRepository.save(dbDeck);
        flashcardRepository.deleteByCarddeckid(dbDeck.getId());

        return createFlashcards(flashcardDeck, dbDeck.getId());
    }

    @Transactional
    public boolean deleteCarddeck(String uuid, String username) {
        Flashyuser dbUser = flashyuserRepository.getFirstByUsername(username);
        Carddeck dbDeck = carddeckRepository.getFirstByUuid(uuid);

        if ((dbUser != null && dbDeck != null)
                && (dbUser.getIsadmin() == 1 || dbDeck.getFlashyuserid() == dbUser.getId())) {
            flashcardRepository.deleteByCarddeckid(dbDeck.getId());
            carddeckRepository.deleteById(dbDeck.getId());
            return true;
        } else {
            return false;
        }
    }

    public boolean addUserFavorites(String username, String uuid) {
        Carddeck dbDeck = carddeckRepository.getFirstByUuid(uuid);
        Flashyuser dbUser = flashyuserRepository.getFirstByUsername(username);
        if (dbUser != null && dbDeck != null && (dbDeck.getIsprivate() == 0 || dbDeck.getFlashyuserid() == dbUser.getId())) {
            Userhasfavorite userFavorite = new Userhasfavorite(dbUser.getId(), dbDeck.getId());
            userhasfavoriteRepository.save(userFavorite);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean removeUserFavorites(String username, String uuid) {
        Carddeck dbDeck = carddeckRepository.getFirstByUuid(uuid);
        Flashyuser dbUser = flashyuserRepository.getFirstByUsername(username);
        if (dbUser != null && dbDeck != null) {
            userhasfavoriteRepository.deleteByUserAndCarddeckid(dbUser.getId(), dbDeck.getId());
            return true;
        }
        return false;
    }

    public boolean addUserLikes(String username, String uuid) {
        Carddeck dbDeck = carddeckRepository.getFirstByUuid(uuid);
        Flashyuser dbUser = flashyuserRepository.getFirstByUsername(username);
        if (dbUser != null && dbDeck != null && (dbDeck.getIsprivate() == 0 || dbDeck.getFlashyuserid() == dbUser.getId())) {
            Userhaslike userLike = new Userhaslike(dbUser.getId(), dbDeck.getId());
            userhaslikeRepository.save(userLike);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean removeUserLikes(String username, String uuid) {
        Carddeck dbDeck = carddeckRepository.getFirstByUuid(uuid);
        Flashyuser dbUser = flashyuserRepository.getFirstByUsername(username);
        if (dbUser != null && dbDeck != null) {
            userhaslikeRepository.deleteByUserAndCarddeckid(dbUser.getId(), dbDeck.getId());
            return true;
        }
        return false;
    }

    public ExtendedCarddeckListDTO getAll(int from, int count, String orderby) {
        List<Extendedcarddeckview> decks;
        switch (orderby) {
            case "likeasc":
                decks = extendedcarddeckviewRepository.findAllPublicOrderbyLikecountAsc(PageRequest.of(from, count)).toList();
                break;
            case "likedesc":
                decks = extendedcarddeckviewRepository.findAllPublicOrderbyLikecountDesc(PageRequest.of(from, count)).toList();
                break;
            case "favoriteasc":
                decks = extendedcarddeckviewRepository.findAllPublicOrderbyFavoritecountAsc(PageRequest.of(from, count)).toList();
                break;
            case "favoritedesc":
                decks = extendedcarddeckviewRepository.findAllPublicOrderbyFavoritecountDesc(PageRequest.of(from, count)).toList();
                break;
            case "cardcountasc":
                decks = extendedcarddeckviewRepository.findAllPublicOrderbyCardcountAsc(PageRequest.of(from, count)).toList();
                break;
            case "cardcountdesc":
                decks = extendedcarddeckviewRepository.findAllPublicOrderbyCardcountDesc(PageRequest.of(from, count)).toList();
                break;
            default:
                decks = extendedcarddeckviewRepository.findAllPublic(PageRequest.of(from, count)).toList();
        }

        List<ExtendedCarddeckDTO> dtoDecks = decks.stream()
                .map(x -> new ExtendedCarddeckDTO(x.getTitle(), null, x.getIsprivate(), x.getUuid(), x.getUsername(),
                        x.getCardcount(), x.getLikecount(), x.getFavoritecount()))
                .toList();

        return new ExtendedCarddeckListDTO("", dtoDecks, 0, dtoDecks.size(), from);


    }
}
