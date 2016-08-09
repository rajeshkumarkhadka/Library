package com.example.repositary;

import com.example.model.Book;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;


@Stateless
public class BookRepositoryStub implements BookRepository {
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("abc");
    private EntityManager entityManager = entityManagerFactory.createEntityManager();
    private EntityTransaction entityTransaction = entityManager.getTransaction();

    public List<Book> getAllBooks() {
        return entityManager.createQuery("SELECT r FROM Book r", Book.class).getResultList();
    }

    public Book findBookById(int id) {
        return entityManager.find(Book.class, id);
    }

    public Book updateBook(int bookId, Book book) {
        entityTransaction.begin();
        Book b = findBookById(bookId);
        b.setName(book.getName());
        b.setPublication(book.getPublication());
        b.setPrice(book.getPrice());
        entityTransaction.commit();
        return b;
    }

    public void deleteBook(int id) {
        Book book = entityManager.find(Book.class, id);
        if (book != null) {
            entityTransaction.begin();
            entityManager.remove(book);
            entityTransaction.commit();
        }
    }

    public Book createBook(Book book) {
        entityTransaction.begin();
        entityManager.persist(book);
        entityTransaction.commit();
        return book;
    }
}
