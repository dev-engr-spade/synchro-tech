package com.synchrotech.commandcenter.model.tenant;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Branding {
    private String logoUrl;
    private String primaryColor;
    private String secondaryColor;
    private String theme;
} 