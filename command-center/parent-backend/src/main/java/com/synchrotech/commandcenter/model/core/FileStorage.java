package com.synchrotech.commandcenter.model.core;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "file_storage")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileStorage {
    @Id
    private String id;
    private String tenantId;
    private String fileName;
    private String fileType;
    private String url;
    private long size;
    private String uploadedBy;
    private Date uploadedAt;
    private Date createdAt;
    private Date updatedAt;
} 