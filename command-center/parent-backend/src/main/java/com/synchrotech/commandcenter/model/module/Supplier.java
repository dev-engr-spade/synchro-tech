package com.synchrotech.commandcenter.model.module;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "suppliers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {
    @Id
    private String id;
    private String tenantId;
    private String name;
    private String contactName;
    private String contactEmail;
    private String contactPhone;
    private String address;
    private boolean active;
    private Date createdAt;
    private Date updatedAt;
} 