package com.edigest.atal.journalApp.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
public class BasicUserDTO {
    @NonNull
    private String userName;
    @NonNull
    private String password;
    private String email;
    private boolean sentimentAnalysis;
}
