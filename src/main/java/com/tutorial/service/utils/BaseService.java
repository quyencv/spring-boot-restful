package com.tutorial.service.utils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

public interface BaseService<ENTITY, DTO> {

    default List<DTO> findAll(DTO condition) {
        return Collections.emptyList();
    };

    default List<DTO> findAll(DTO condition, Sort sort) {
        return Collections.emptyList();
    };

    default Page<DTO> findAll(DTO condition, Pageable page) {
        return Page.empty();
    };

    default Optional<DTO> findById(Long id) {
        return Optional.empty();
    };

    @Transactional
    default Long deleteById(Long id) {
        return id;
    };

    @Transactional
    default DTO save(DTO dto) throws Exception {
        return dto;
    };

    @Transactional
    default DTO update(DTO dto) throws Exception {
        return dto;
    };

}
