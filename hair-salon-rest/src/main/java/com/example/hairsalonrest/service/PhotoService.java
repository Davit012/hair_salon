package com.example.hairsalonrest.service;


import com.hairsaloncommon.model.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface PhotoService {

    Photo savePhoto(MultipartFile multipartFile,int id);
    Optional<List<Photo>> getPhotoByWorkerId(int id);
    void deletePhoto(int id);


}
