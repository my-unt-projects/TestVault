package com.fantasticsix.testvault.service;

import com.fantasticsix.testvault.model.Tag;

import java.util.List;

public interface TagService {
    Tag save(Tag tag);
    Tag getById(Long id);
    void deleteById(Long id);
    Tag update(Tag tag);
    List<Tag> getAll();
    List<Tag> getByTestCaseId(Long testCaseId);
}