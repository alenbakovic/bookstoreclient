package com.bookstore.client;

import com.bookstore.model.Book;
import com.sun.jersey.api.client.Client;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

public class ClientModule {

    private static final String BOOKSTORE_URL = "http://localhost:8080/bookstore";
    private static final String GET_BOOK = "/getBook";
    private static final String ADD_BOOK = "/addBook";
    private static final String ISBN_BASE = "0978-3-16-148410-";



    public void testBookstore() {
        // create client
        Client bookstoreClient = Client.create();

        // create books
        System.out.println("Creating books");
        List<Book> bookList = createBooks();

        // upload books
        System.out.println("Uploading books to server and saving in database");
        bookList.forEach(book -> {
            bookstoreClient
                    .resource(BOOKSTORE_URL + ADD_BOOK)
                    .type(MediaType.APPLICATION_JSON_TYPE)
                    .post(book);
        });

        // read 5 books from database
        System.out.println("Reading books from database");
        List<Book> receivedBooks = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Book receivedBook = bookstoreClient
                    .resource(BOOKSTORE_URL + GET_BOOK)
                    .queryParam("isbn", ISBN_BASE + i)
                    .get(Book.class);
            receivedBooks.add(receivedBook);
        }

        // print books in console
        System.out.println("Books from database:");
        receivedBooks.forEach(System.out::println);

    }

    private List<Book> createBooks() {
        List<Book> bookList = new ArrayList<>();

        bookList.add(createBook("In Search of Lost Time by Marcel Proust", "Marcel Proust","Fiction",
                4215, "0978-3-16-148410-0"));
        bookList.add(createBook("Don Quixote", "Miguel de Cervantes","Fiction",
                429, "0978-3-16-148410-1"));
        bookList.add(createBook("Crime and Punishment", "Fyodor Dostoevsky","Psychological novel",
                524, "0978-3-16-148410-2"));
        bookList.add(createBook("The Bridge on the Drina", "Ivo Andric","Historical fiction",
                333, "0978-3-16-148410-3"));
        bookList.add(createBook("Pride and Prejudice", "Jane Austen","Novel of manners, Satire",
                432, "0978-3-16-148410-4"));
        bookList.add(createBook("Death and the Dervish", "Mesa Selimovic","Novel",
                473, "0978-3-16-148410-5"));
        bookList.add(createBook("Harry Potter and the Deathly Hallows", "J. K. Rowling","Fantasy Fiction",
                607, "0978-3-16-148410-6"));
        bookList.add(createBook("Moby-Dick", "Herman Melville","Adventure fiction, Epic",
                752, "0978-3-16-148410-7"));
        bookList.add(createBook("War and Peace", "Leo Tolstoy","Novel",
                1225, "0978-3-16-148410-8"));
        bookList.add(createBook("The Master and Margarita", "Mikhail Bulgakov","Farce, Mysticism, Romance novel",
                367, "0978-3-16-148410-9"));
        return bookList;
    }

    private Book createBook(String title, String authors, String genre, int numOfPagees, String isbn) {
        return  Book.builder()
                .title(title)
                .authors(authors)
                .genre(genre)
                .numberOfPages(numOfPagees)
                .isbn(isbn)
                .build();
    }

}
