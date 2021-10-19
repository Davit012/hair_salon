package com.example.hairsalonrest.endpoint;

import com.example.hairsalonrest.service.WorkerService;
import com.example.hairsalonrest.service.impl.PhotoServiceImpl;
import com.hairsaloncommon.model.Photo;
import com.hairsaloncommon.model.Worker;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/photos")
public class PhotoEndpoint {

    private final PhotoServiceImpl photoService;
    private final WorkerService workerService;
    @Value("${file.upload.dir}")
    private String uploadDir;

    @PostMapping("/{id}")
    public ResponseEntity<Photo> addImage(@RequestParam("image") MultipartFile multipartFile,
                                          @PathVariable(name = "id") int id) throws IOException {
        Worker worker = workerService.findWorkerById(id);
        if (worker != null) {
            return ResponseEntity.ok(photoService.savePhoto(multipartFile, id));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte[] getImage(@RequestParam("imageUrl") String imageUrl) {
        InputStream in = null;
        try {
            in = new FileInputStream((uploadDir + imageUrl));
            return IOUtils.toByteArray(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deletePhoto(@PathVariable(name = "id") int id) {
        photoService.deletePhoto(id);
    }
}
