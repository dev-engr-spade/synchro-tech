package com.synchrotech.commandcenter.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentSettings {
    private String description;
    private boolean active;
    // Add more department-specific settings as needed
}
