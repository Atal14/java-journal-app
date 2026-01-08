package com.edigest.atal.journalApp.entity;

import java.time.LocalDateTime;

import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Document(collection = "journal_entries")
@Setter
@Getter
@AllArgsConstructor
public class JournalEntry {

    @Id
    private ObjectId id;
    private String content;
    @NonNull
    private String title;
    private LocalDateTime date;
    
}
