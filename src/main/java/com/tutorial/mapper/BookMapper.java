package com.tutorial.mapper;

import org.mapstruct.Mapper;

import com.tutorial.dto.BookDTO;
import com.tutorial.model.Book;

@Mapper(componentModel = "spring", uses = {})
public interface BookMapper extends EntityConvert<Book, BookDTO> {

}