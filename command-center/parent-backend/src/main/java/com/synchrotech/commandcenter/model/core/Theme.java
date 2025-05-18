package com.synchrotech.commandcenter.model.core;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "themes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Theme {
    @Id
    private String id;
    private String tenantId;
    private String name;
    private String primaryColor;
    private String secondaryColor;
    private String backgroundColor;
    private String textColor;
    private boolean darkMode;
} 