package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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
    public Image uploadImage(MultipartFile multipartFile) throws IOException {

        log.info("Current method is - uploadImage");
        Image image = new Image();
        image.setData(multipartFile.getBytes());
        image.setFileSize(multipartFile.getSize());
        image.setMediaType(multipartFile.getContentType());
        image.setData(multipartFile.getBytes());
        return imageRepository.save(image);

    }

}