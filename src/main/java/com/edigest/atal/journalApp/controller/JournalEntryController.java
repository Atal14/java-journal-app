 package com.edigest.atal.journalApp.controller;

import com.edigest.atal.journalApp.entity.User;
import com.edigest.atal.journalApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edigest.atal.journalApp.entity.JournalEntry;
import com.edigest.atal.journalApp.service.JournalEntryService;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@Slf4j
@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb = userService.findByUserName(userName);
        List<JournalEntry> all = userInDb.getJournalEntries();
        if (!all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry entry) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            JournalEntry journalEntry = journalEntryService.createEntry(entry, userName);
            return new ResponseEntity<JournalEntry>(journalEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("e: ", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/journalId/{id}")
    public ResponseEntity<JournalEntry> getJournalById(@PathVariable final ObjectId id) {
       Optional<JournalEntry> journalEntry = journalEntryService.getJournalById(id);
        return journalEntry.map(entry -> new ResponseEntity<>(entry, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/journalId/{id}")
    public ResponseEntity<?> updateEntry(@PathVariable final ObjectId id, @RequestBody JournalEntry entry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        // User user = userService.findByUserName(userName);
        Optional<JournalEntry> journalEntry = journalEntryService.getJournalById(id);
        if (journalEntry.isPresent()) {
            JournalEntry old = journalEntry.get();
            final String title = !entry.getTitle().isEmpty() ? entry.getTitle() : old.getTitle();
            final String content = entry.getContent() != null && !entry.getContent().isEmpty() ? entry.getContent() : old.getContent();
            old.setTitle(title);
            old.setContent(content);
            JournalEntry newEntry = journalEntryService.createEntry(old, userName);
            return new ResponseEntity<>(newEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/journalId/{id}")
    public boolean deleteByJournalId(@PathVariable final ObjectId id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        journalEntryService.deleteByJournalId(id, userName);
        return true;
    }
    
}
