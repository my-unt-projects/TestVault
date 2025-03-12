package com.fantasticsix.testvault.service.impl;

import com.fantasticsix.testvault.model.Tag;
import com.fantasticsix.testvault.model.TestCase;
import com.fantasticsix.testvault.repository.TagRepository;
import com.fantasticsix.testvault.repository.TestCaseRepository;
import com.fantasticsix.testvault.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final TestCaseRepository testCaseRepository;

    @Override
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag getById(Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found with id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        tagRepository.deleteById(id);
    }

    @Override
    public Tag update(Tag tag) {
        Tag savedTag = tagRepository.findById(tag.getTagId())
                .orElseThrow(() -> new RuntimeException("Tag not found with id: " + tag.getTagId()));
        savedTag.setName(tag.getName());
        savedTag.setType(tag.getType());
        savedTag.setTestCases(tag.getTestCases());
        return tagRepository.save(savedTag);
    }

    @Override
    public List<Tag> getAll() {
        return tagRepository.findAll();
    }
}
