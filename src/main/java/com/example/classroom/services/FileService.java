package com.example.classroom.services;


import com.example.classroom.entities.File;
import com.example.classroom.entities.Post;
import com.example.classroom.repositories.FileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FileService {

    FileRepository fileRepository;

    public void saveAll(List<File> fileList){
        this.fileRepository.saveAll(fileList);
    }

    public void deleteAllByPost(Post post){
        this.fileRepository.deleteAllByPost(post);
    }
}
