package com.flashy.server.service;

import com.flashy.server.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    private final int SEARCH_RESULT_MAX_COUNT = 50;

    public List<String> searchTags(String searchquery) {
        if (searchquery == null || searchquery.isEmpty()) {
            return null;
        }

        return tagRepository.searchTags(PageRequest.of(0, SEARCH_RESULT_MAX_COUNT), searchquery);
    }
}
