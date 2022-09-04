package com.tutorial.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorial.constant.UrlPathConstant;
import com.tutorial.dto.BookDTO;
import com.tutorial.service.BookService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = UrlPathConstant.Book.PRE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<?> saveBook(@RequestBody @Valid BookDTO dto) throws Exception {
        return new ResponseEntity<>(bookService.save(dto), HttpStatus.CREATED);
    }

    @DeleteMapping(UrlPathConstant.Book.DELETE)
    public ResponseEntity<?> deleteBook(@PathVariable Long bookId) {
        bookService.deleteById(bookId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllBooks(BookDTO condition) {
        return new ResponseEntity<>(bookService.findAll(condition), HttpStatus.OK);
    }
}
