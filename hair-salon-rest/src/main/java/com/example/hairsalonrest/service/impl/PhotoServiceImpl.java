package com.example.hairsalonrest.service.impl;

import com.example.hairsalonrest.repository.PhotoRepository;
import com.example.hairsalonrest.service.PhotoService;
import com.example.hairsalonrest.service.WorkerService;
import com.hairsaloncommon.model.Photo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepository photoRepository;
    private final WorkerService workerService;
    @Value("${file.upload.dir}")
    private String uploadDir;

    @Override
    public Photo savePhoto(MultipartFile multipartFile, int id) {

        if (!multipartFile.isEmpty()) {
            String picUrl = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
            try {
                multipartFile.transferTo(new File(uploadDir + File.separator + picUrl));
            } catch (IOException e) {
                    e.printStackTrace();
            }
            return photoRepository.save(Photo.builder()
                    .value(picUrl)
                    .worker(workerService.findWorkerById(id))
                    .build());
        }
        return null;
    }

    @Override
    public void deletePhoto(int id) {
        photoRepository.deleteById(id);
    }
}
