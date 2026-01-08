package com.edigest.atal.journalApp.cache;

import com.edigest.atal.journalApp.entity.ConfigJournalAppEntity;
import com.edigest.atal.journalApp.repository.ConfigJournalAppRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {
    public Map<String, String> appCache;

    @Autowired()
    private ConfigJournalAppRepository configJournalAppRepository;

    @PostConstruct()
    public void init() {
        appCache = new HashMap<>();
        List<ConfigJournalAppEntity> configs = configJournalAppRepository.findAll();
        for (ConfigJournalAppEntity config : configs) {
            appCache.put(config.getKey(), config.getValue());
        }
    }
}
