package com.github.tandcode.graphql.model;

import java.util.ArrayList;
import java.util.List;

public record Book(String id, String name, int pageCount, String authorId) {
  private static int idCounter = 1;
  private static final List<Book> BOOKS = new ArrayList<>();

  static {
    addBook("Effective Java", 416, "author-1");
    addBook("Hitchhiker's Guide to the Galaxy", 208, "author-2");
    addBook("Down Under", 436, "author-3");
  }

  public static Book addBook(String name, Integer pageCount, String authorId) {
    Book book = new Book("book-" + idCounter++, name, pageCount, authorId);
    BOOKS.add(book);
    return book;
  }

  public static Book getById(String id) {
    return BOOKS.stream()
      .filter(book -> book.id().equals(id))
      .findFirst()
      .orElse(null);
  }
}
