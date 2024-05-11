package com.github.tandcode.graphql.controller;

import com.github.tandcode.graphql.model.Author;
import com.github.tandcode.graphql.model.Book;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class BookController {

  @QueryMapping
  public Book bookById(@Argument String id) {
    return Book.getById(id);
  }

  @MutationMapping
  public Book createBook(@Argument String name, @Argument Integer pageCount, @Argument String authorId) {
    return Book.addBook(name, pageCount, authorId);
  }

  @SchemaMapping
  public Author author(Book book) {
    return Author.getById(book.authorId());
  }
}
