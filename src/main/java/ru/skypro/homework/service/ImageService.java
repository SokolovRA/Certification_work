package ru.skypro.homework.service;

import liquibase.repackaged.org.apache.commons.lang3.tuple.Pair;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.repository.ImageRepository;

import javax.transaction.Transactional;
import java.io.IOException;
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    public void remove(Image image) {
        log.info("Used method  is - remove");
        imageRepository.delete(image);
        log.info("Image removed successfully");
    }
    public Image uploadImage(MultipartFile imageFile) throws IOException {
        log.info("Used method  is - uploadImage");
        Image image = new Image();
        image.setMediaType(imageFile.getContentType());
        image.setFileSize(imageFile.getSize());
        image.setData(imageFile.getBytes());
        return imageRepository.save(image);
    }
    public Image getImageById(Integer id) {
        log.info("Used method  is - getImageById");
        return imageRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    public Pair<String, byte[]> getImage(Integer id) {
        log.info("Used method  is - getImage");
        Image image = getImageById(id);
        return Pair.of(image.getMediaType(), image.getData());
    }
}
