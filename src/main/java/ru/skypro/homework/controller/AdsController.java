package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdsDTO;
import ru.skypro.homework.dto.CreateAdsDTO;
import ru.skypro.homework.dto.FullAdsDTO;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.wrapper.ResponseWrapper;

import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class AdsController {
    private final AdsService adsService;
    private final ImageService imageService;

    @GetMapping
    public ResponseWrapper<AdsDTO> getAllAds() {
        log.info("Used method is - getAllAds");
        return ResponseWrapper.of(adsService.getAllAds());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdsDTO> addAds(@RequestPart("image") MultipartFile imageFile,
                                         @Valid
                                         @RequestPart("properties") CreateAdsDTO createAds,
                                         Authentication authentication) throws Exception {
        log.info("Used method is - addAds");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(adsService.addAds( createAds,imageFile, authentication));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullAdsDTO> getFullAdsInfo(@PathVariable Integer id) throws Exception {
        log.info("Used method is - getFullAd");
        FullAdsDTO fullAdsDto = adsService.getFullAds(id);
        return ResponseEntity.ok(fullAdsDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeAds(@PathVariable("id") Integer adsId) {
        log.info("Used method is - removeAds");
        adsService.removeAdsById(adsId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AdsDTO> updateAds(@PathVariable("id") Integer id,
                                            @RequestBody CreateAdsDTO createAds) {
        return ResponseEntity.ok(adsService.updateAds(id, createAds));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getAdsAuthorizedUsers(Authentication authentication) {
        log.info("Used method is - getAdsMe");
         return ResponseEntity.ok(ResponseWrapper.of(adsService.getAdsAuthorizedUsers(authentication)));
    }

    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateAdsImage(@PathVariable("id") Integer id,
                                            @RequestPart("image") MultipartFile image) throws IOException {
        log.info("Used method is - updateAdsImage");
        adsService.updateAdsImage(id, image);
        return ResponseEntity.ok().build();

    }
    @GetMapping(value = "/image/{id}")
    public ResponseEntity<byte[]> getAdsImage(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(imageService.getImageById(id).getData());
    }
}
