package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdsDTO;
import ru.skypro.homework.dto.CreateAdsDTO;
import ru.skypro.homework.dto.FullAdsDTO;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.wrapper.ResponseWrapper;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class AdsController {
    private final AdsService adsService;
    private final AdsMapper adsMapper;
    private final ImageService imageService;

    @GetMapping
    public ResponseWrapper<AdsDTO> getAllAds() {
        log.info("Used method is - getAllAds");
        return ResponseWrapper.of(adsMapper.toDto(adsService.getAllAds()));
    }
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdsDTO> addAds(@RequestPart("image") MultipartFile multipartFile,
                                         @RequestPart("properties") CreateAdsDTO createAdsDto) throws Exception {
        log.info("Used method is - addAds");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(adsMapper.toDto(adsService.addAds(createAdsDto, multipartFile, authentication.getName())));
    }
    @GetMapping("/{id}")
    public ResponseEntity<FullAdsDTO> getFullAdsInfo(@PathVariable int id) throws Exception {
        log.info("Used method is - getFullAd");
        FullAdsDTO fullAdDto = adsService.getFullAds(id);
        return ResponseEntity.ok(fullAdDto);
    }
}