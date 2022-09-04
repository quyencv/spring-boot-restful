package com.tutorial.mapper;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Page;

public interface EntityConvert<ENTITY, DTO> {

    DTO toDTO(ENTITY e);

    ENTITY toEntity(DTO d);

    List<DTO> toDTO(List<ENTITY> e);

    List<ENTITY> toEntity(List<DTO> d);

    default Optional<DTO> toDTO(Optional<ENTITY> entity) {
        return Optional.ofNullable(entity.map(this::toDTO).orElse(null));
    }

    default Optional<ENTITY> toEntity(Optional<DTO> dto) {
        return Optional.ofNullable(dto.map(this::toEntity).orElse(null));
    }

    default Page<DTO> toDTO(Page<ENTITY> entities) {
        if (Objects.isNull(entities)) {
            return Page.empty();
        }
        return entities.map(this::toDTO);
    }

    default Page<ENTITY> toEntity(Page<DTO> dtos) {
        if (Objects.isNull(dtos)) {
            return Page.empty();
        }
        return dtos.map(this::toEntity);
    }

}
