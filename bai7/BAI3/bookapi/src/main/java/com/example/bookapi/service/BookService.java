package com.example.bookapi.service;

import com.example.bookapi.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private List<Book> books = new ArrayList<>();
    private int currentId = 1; // ID tự tăng

    public BookService() {
        books.add(new Book(currentId++, "J2EE", "Huy Cường"));
        books.add(new Book(currentId++, "Spring Boot", "Nguyễn Khải"));
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public Book getBookById(int id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // ⭐ THÊM SÁCH – TỰ GÁN ID
    public void addBook(Book book) {
        book.setId(currentId++);
        books.add(book);
    }

    // ⭐ SỬA
    public void updateBook(int id, Book updatedBook) {
        Book book = getBookById(id);
        if (book != null) {
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
        }
    }

    // ⭐ XOÁ
    public void deleteBook(int id) {
        books.removeIf(book -> book.getId() == id);
    }
}


