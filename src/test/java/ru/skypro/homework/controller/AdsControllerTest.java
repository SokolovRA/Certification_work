package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockPart;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dto.CreateAdsDTO;
import ru.skypro.homework.enums.Role;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.MyUserDetailsService;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AdsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AdsRepository adsRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private ImageRepository imageRepository;

    private Authentication auth;
    private final MockPart imageFile = new MockPart("image", "image", "image".getBytes());
    private final User user = new User();
    private final CreateAdsDTO createAdsDTO = new CreateAdsDTO();
    private final Ads ads = new Ads();
    private final Image image = new Image();

    @BeforeEach
    void setUp() {
        user.setUsername("sokolovRA@mail.ru");
        user.setFirstName("Roma");
        user.setLastName("Sokolov");
        user.setPhone("+79214403109");
        user.setPassword(encoder.encode("password"));
        user.setRole(Role.USER);
        user.setEnabled(true);
        userRepository.save(user);

        UserDetails userDetails = myUserDetailsService.loadUserByUsername(user.getUsername());
        auth = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities());

        createAdsDTO.setTitle("Product");
        createAdsDTO.setDescription("description");
        createAdsDTO.setPrice(100);


        ads.setTitle("Product");
        ads.setDescription("description");
        ads.setPrice(100);
        ads.setAuthor(user);
        adsRepository.save(ads);
    }

    @AfterEach
    void cleanUp() {
        userRepository.delete(user);
    }

    @Test
    public void shouldReturnCorrectAdsListWhenGetAllAdsIsCalled() throws Exception {
        mockMvc.perform(get("/ads"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.count").isNumber())
                .andExpect(jsonPath("$.results").isArray());
    }


    @Test
    public void testGetAdByIdReturnsCorrectDetails() throws Exception {
        mockMvc.perform(get("/ads/{id}", ads.getId())
                        .with(authentication(auth)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pk").value(ads.getId()))
                .andExpect(jsonPath("$.title").value(ads.getTitle()))
                .andExpect(jsonPath("$.description").value(ads.getDescription()))
                .andExpect(jsonPath("$.price").value(ads.getPrice()))
                .andExpect(jsonPath("$.email").value(user.getUsername()))
                .andExpect(jsonPath("$.authorFirstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.authorLastName").value(user.getLastName()))
                .andExpect(jsonPath("$.phone").value(user.getPhone()));
    }
    @Test
    void testRemoveAdByIdFromDatabase() throws Exception {
        mockMvc.perform(delete("/ads/{id}", ads.getId())
                        .with(authentication(auth)))
                .andExpect(status().isOk());
        assertFalse(adsRepository.findById(ads.getId()).isPresent());
    }
    @Test
    public void testGetUserAdsReturnsListOfUserAdsFromDatabase() throws Exception {
        mockMvc.perform(get("/ads/me")
                        .with(authentication(auth)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.count").isNumber())
                .andExpect(jsonPath("$.results").isArray());
    }
    @Test
    public void testGetAdImageByIdReturnsCorrectImageData() throws Exception {
        image.setData("image".getBytes());
        image.setMediaType("image/jpeg");
        imageRepository.save(image);
        ads.setImage(image);
        adsRepository.save(ads);

        mockMvc.perform(get("/ads/image/{id}", image.getId())
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .with(authentication(auth)))
                .andExpect(status().isOk())
                .andExpect(content().bytes(image.getData()));
    }
}
