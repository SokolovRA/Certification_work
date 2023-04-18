package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.skypro.homework.dto.AdsDTO;
import ru.skypro.homework.dto.CreateAdsDTO;
import ru.skypro.homework.dto.FullAdsDTO;
import ru.skypro.homework.model.Ads;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.service.ImageService;

@Mapper(componentModel = "spring", uses = {CommentMapper.class, ImageService.class})
public interface AdsMapper extends MapperScheme<AdsDTO, Ads> {

    @Mapping(target = "id", source = "pk")
    @Mapping(target = "author.id", source = "author")
    @Mapping(target = "image", ignore = true)
    Ads toEntity(AdsDTO dto);

    @Mapping(target = "pk", source = "id")
    @Mapping(target = "author", source = "author.id")
    @Mapping(target = "image", source = "entity.image", qualifiedByName = "imageMapping")
    AdsDTO toDto(Ads entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "image", ignore = true)
    Ads toEntity(CreateAdsDTO  dto);

    @Mapping(target = "authorFirstName", source = "author.firstName")
    @Mapping(target = "authorLastName", source = "author.lastName")
    @Mapping(target = "phone", source = "author.phone")
    @Mapping(target = "email", source = "author.email")
    @Mapping(target = "image", source = "entity.image", qualifiedByName = "imageMapping")
    @Mapping(target = "pk", source = "id")
    FullAdsDTO toFullAdsDto(Ads entity);

    @Named("imageMapping")
    default String imageMapping(Image image) {
        if (image == null) {
            return null;
        }
        return "/ads/image/" +image.getId();
    }
}
