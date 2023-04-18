package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.AdsCommentDTO;
import ru.skypro.homework.model.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper extends MapperScheme<AdsCommentDTO, Comment> {

    @Mapping(target = "author", ignore = true)
    @Mapping(target = "id", source="pk")
    @Mapping(target = "createdAt", ignore = true)
    Comment toEntity(AdsCommentDTO dto);

    @Mapping(target = "author", source = "author.id")
    @Mapping(source = "id", target = "pk")
    @Mapping(target = "createdAt", source = "entity.createdAt")
    AdsCommentDTO toDto(Comment entity);

}

