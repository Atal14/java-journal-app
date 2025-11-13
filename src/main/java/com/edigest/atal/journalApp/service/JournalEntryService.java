package com.edigest.atal.journalApp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.edigest.atal.journalApp.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.edigest.atal.journalApp.entity.JournalEntry;
import com.edigest.atal.journalApp.repository.JournalEntryRepository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public JournalEntry createEntry(JournalEntry journal, String userName) {
        User user = userService.findByUserName(userName);
        journal.setDate(LocalDateTime.now());
        JournalEntry saved = journalEntryRepository.save(journal);
        user.getJournalEntries().add(saved);
        userService.updateUser(user);
        return saved;
    }

    public JournalEntry createEntry(JournalEntry journal) {
        journal.setDate(LocalDateTime.now());
        return journalEntryRepository.save(journal);
    }

    public List<JournalEntry> getAllJournals() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getJournalById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    // public JournalEntry updateEntryById(Object id) {
    //     return journalEntryRepository.updateEntryById(id);
    // }

    @Transactional
    public void deleteByJournalId(ObjectId id, String userName) {
        try {
            User user = userService.findByUserName(userName);
            boolean removed = user.getJournalEntries().removeIf(journal -> journal.getId().equals(id));
            if (removed) {
                userService.updateUser(user);
                journalEntryRepository.deleteById(id);
            }
        } catch (Exception e) {
            log.error("e: ", e);
            throw new RuntimeException("An error occurred while deleting the entry", e);
        }


    }
}
