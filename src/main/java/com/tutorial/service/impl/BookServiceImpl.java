package com.tutorial.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.tutorial.dto.BookDTO;
import com.tutorial.mapper.BookMapper;
import com.tutorial.model.Book;
import com.tutorial.repository.BookRepository;
import com.tutorial.service.BookService;
import com.tutorial.service.utils.QueryService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookServiceImpl extends QueryService<Book, BookDTO> implements BookService {

    private final BookMapper bookMapper;

    private final BookRepository bookRepository;

    @Override
    public BookDTO save(BookDTO dto) throws Exception {
        Book book = bookRepository.save(bookMapper.toEntity(dto));
        return bookMapper.toDTO(book);
    }

    @Override
    public BookDTO update(BookDTO dto) throws Exception {
        Book book = bookRepository.save(bookMapper.toEntity(dto));
        return bookMapper.toDTO(book);
    }

    @Override
    public List<BookDTO> findAll(BookDTO condition) {
        Specification<Book> spec = createSpec(condition);
        List<Book> books = bookRepository.findAll(spec);
        List<BookDTO> result = bookMapper.toDTO(books);
        return result;
    }

    @Override
    public List<BookDTO> findAll(BookDTO condition, Sort sort) {
        Specification<Book> spec = createSpec(condition);
        List<Book> books = bookRepository.findAll(spec, sort);
        List<BookDTO> result = bookMapper.toDTO(books);
        return result;
    }

    @Override
    public Page<BookDTO> findAll(BookDTO condition, Pageable pageable) {
        Specification<Book> spec = createSpec(condition);
        Page<Book> roles = bookRepository.findAll(spec, pageable);
        Page<BookDTO> result = bookMapper.toDTO(roles);
        return result;
    }

    @Override
    public Optional<BookDTO> findById(Long id) {
        Optional<Book> optRole = bookRepository.findById(id);
        Optional<BookDTO> result = bookMapper.toDTO(optRole);
        return result;
    }

    @Override
    public Long deleteById(Long id) {
        bookRepository.deleteById(id);
        return id;
    }

}
