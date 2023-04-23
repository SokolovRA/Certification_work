package ru.skypro.homework.service;

import liquibase.repackaged.org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.service.ImageService;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ImageServiceTest {
    @Mock
    private ImageRepository imageRepository;
    @InjectMocks
    private ImageService imageService;
    @Test
    void testRemoveImage() {

        ImageRepository imageRepository = mock(ImageRepository.class);
        ImageService imageService = new ImageService(imageRepository);
        Image testImage = new Image();
        testImage.setId(1);
        imageService.remove(testImage);
        verify(imageRepository, times(1)).delete(testImage);
    }
    @Test
    void testUploadImage() throws IOException {

        byte[] imageContent = "Test image".getBytes();
        MockMultipartFile file = new MockMultipartFile("image", "test.jpg", "image/jpeg", imageContent);

        Image savedImage = new Image();
        savedImage.setId(1);
        savedImage.setData(imageContent);
        savedImage.setFileSize((long) imageContent.length);
        savedImage.setMediaType("image/jpeg");
        when(imageRepository.save(any(Image.class))).thenReturn(savedImage);
        Image image = imageService.uploadImage(file);
        verify(imageRepository, times(1)).save(any(Image.class));
        assertThat(image).isEqualTo(savedImage);
    }
    @Test
    void testGetImageById() {
        ImageRepository imageRepository = mock(ImageRepository.class);
        ImageService imageService = new ImageService(imageRepository);
        Image testImage = new Image();
        testImage.setId(1);
        when(imageRepository.findById(1)).thenReturn(Optional.of(testImage));
        Image returnedImage = imageService.getImageById(1);
        assertEquals(testImage, returnedImage);
    }
    @Test
    void testGetImage() {

        ImageRepository imageRepository = mock(ImageRepository.class);
        ImageService imageService = new ImageService(imageRepository);
        Image testImage = new Image();
        testImage.setId(1);
        testImage.setMediaType("image/jpeg");
        testImage.setData("test image data".getBytes());
        when(imageRepository.findById(1)).thenReturn(Optional.of(testImage));
        Pair<String, byte[]> imageData = imageService.getImage(1);
        assertEquals(testImage.getMediaType(), imageData.getLeft());
        assertArrayEquals(testImage.getData(), imageData.getRight());
    }
}

