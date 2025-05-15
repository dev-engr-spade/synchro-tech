package com.synchrotech.commandcenter.repository.module;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.synchrotech.commandcenter.model.module.Project;

/**
 * Repository for project entities.
 */
public interface ProjectRepository extends MongoRepository<Project, String> {
    // Add custom query methods if needed
} 