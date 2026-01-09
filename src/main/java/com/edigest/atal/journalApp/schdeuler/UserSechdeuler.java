package com.edigest.atal.journalApp.schdeuler;

import com.edigest.atal.journalApp.entity.JournalEntry;
import com.edigest.atal.journalApp.entity.User;
import com.edigest.atal.journalApp.repository.UserCriteriaRepository;
import com.edigest.atal.journalApp.service.EMailService;
import com.edigest.atal.journalApp.service.SentimentAnalysis;
import com.edigest.atal.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserSechdeuler {

    @Autowired
    private EMailService eMailService;

    @Autowired
    private UserCriteriaRepository userCriteriaRepository;

    @Autowired
    private SentimentAnalysis sentimentAnalysis;

//    @Scheduled(cron = "0 * * ? * *")
    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendSaMail() {
        List<User> users = userCriteriaRepository.getUsersForSA();
        for (User user : users) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<String> filedEntries = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minusDays(7))).map(x -> x.getContent()).collect(Collectors.toList());
            String entry = String.join(" ", filedEntries);
            String sentiment = sentimentAnalysis.getSentiment(entry);
//            eMailService.sendEmail(user.getEmail(), "Sentiment for last 7 days", sentiment);
        }

    }
}
