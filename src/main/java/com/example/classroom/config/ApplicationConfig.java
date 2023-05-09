package com.example.classroom.config;

import com.example.classroom.services.FileStorageService;
import org.jobrunr.jobs.mappers.JobMapper;
import org.jobrunr.storage.InMemoryStorageProvider;
import org.jobrunr.storage.StorageProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig implements CommandLineRunner {

    FileStorageService fileStorageService;
    @Autowired
    public ApplicationConfig(FileStorageService fileStorageService){
        this.fileStorageService = fileStorageService;
    }
    @Bean
    public StorageProvider storageProvider(JobMapper jobMapper) {
        InMemoryStorageProvider storageProvider = new InMemoryStorageProvider();
        storageProvider.setJobMapper(jobMapper);
        return storageProvider;
    }

    @Override
    public void run(String... args) throws Exception {
        this.fileStorageService.init();
    }
}
