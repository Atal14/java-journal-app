package com.edigest.atal.journalApp.schdeuler;

import com.edigest.atal.journalApp.entity.JournalEntry;
import com.edigest.atal.journalApp.entity.User;
import com.edigest.atal.journalApp.enums.Sentiment;
import com.edigest.atal.journalApp.repository.UserCriteriaRepository;
import com.edigest.atal.journalApp.service.EMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserSechdeuler {

    @Autowired
    private EMailService eMailService;

    @Autowired
    private UserCriteriaRepository userCriteriaRepository;

//    @Scheduled(cron = "0 * * ? * *")
    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendSaMail() {
        List<User> users = userCriteriaRepository.getUsersForSA();
        for (User user : users) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minusDays(7))).map(x -> x.getSentiment()).collect(Collectors.toList());

            Map<Sentiment, Integer> sentimentsCount = new HashMap<>();
            for (Sentiment sentiment : sentiments) {
                if (sentiment != null) {
                    sentimentsCount.put(sentiment, sentimentsCount.getOrDefault(sentiment, 0) + 1);
                }
            }
            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for (Map.Entry<Sentiment, Integer> entry : sentimentsCount.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }

            if (mostFrequentSentiment != null) {
                System.out.println("Email Sent");
                eMailService.sendEmail("atal@live.com", "Sentiment for last 7 days", mostFrequentSentiment.toString());
            }
        }

    }
}
