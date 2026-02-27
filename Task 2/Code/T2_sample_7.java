package BookshelfAPI.BookshelfAPI.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "shelves")

public class Shelf implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;
    private String name;
    @OneToMany(mappedBy = "shelf", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Book> books;

    public Shelf(){}

    public Shelf(String name) {
        this.name = name;
        this.books = new ArrayList<>();
    }

    public int getId() {
        return Id;
    }
    public void setId(int id) {
        Id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<Book> getBooks() {
        return books;
    }
    public void setBooks(List<Book> books) {
        this.books = books;
    }
    package BookshelfAPI.BookshelfAPI.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "books")
public class Book  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;
    private String title;
    private String description;
    private int numberOfPages;
    private int yearPublished;
    @ManyToOne(fetch = FetchType.LAZY, optional = true)  
    @JoinColumn(name = "shelf_id")
    @JsonIgnore
    private Shelf shelf;

    public Book(){}

    public Book(String title, String description, int numberOfPages, int yearPublished, Shelf shelf) {
        this.title = title;
        this.description = description;
        this.numberOfPages = numberOfPages;
        this.yearPublished = yearPublished;
        this.shelf = shelf;
    }

    public int getId() {
        return Id;
    }
    public void setId(int id) {
        Id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getNumberOfPages() {
        return numberOfPages;
    }
    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }
    public int getYearPublished() {
        return yearPublished;
    }
    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }
    public Shelf getShelf() {
        return shelf;
    }
    public void setShelf(Shelf shelf) {
        this.shelf = shelf;
    }
}
package BookshelfAPI.BookshelfAPI.repositories;

import BookshelfAPI.BookshelfAPI.models.Shelf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShelfRepository extends JpaRepository<Shelf, Integer> {
}
package BookshelfAPI.BookshelfAPI.service;
import BookshelfAPI.BookshelfAPI.models.Shelf;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface ShelfService {
    void createShelf(Shelf shelf);
    Shelf getShelfById(int shelfId);
    List<Shelf>  getAllShelves();
}
package BookshelfAPI.BookshelfAPI.service;
import BookshelfAPI.BookshelfAPI.models.Shelf;
import BookshelfAPI.BookshelfAPI.repositories.ShelfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ShelfServiceImpl implements ShelfService{

    @Autowired
    ShelfRepository shelfRepository;

    @Override
    public void createShelf(Shelf shelf) {
        shelfRepository.save(shelf);
    }

    @Override
    public Shelf getShelfById(int shelfId) {
        if(shelfRepository.findById(shelfId).isPresent()){
            return  shelfRepository.findById(shelfId).get();
        }
        return  null;
    }

    @Override
    public List<Shelf> getAllShelves() {
        return shelfRepository.findAll();
    }
}
package BookshelfAPI.BookshelfAPI.service;
import BookshelfAPI.BookshelfAPI.dtos.BookDto;
import BookshelfAPI.BookshelfAPI.models.Book;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public interface BookService {
    void addBook(BookDto bookDto);
    List<Book> getAllBooks();
    Book getBookById(int bookId);
}
package BookshelfAPI.BookshelfAPI.service;
import BookshelfAPI.BookshelfAPI.dtos.BookDto;
import BookshelfAPI.BookshelfAPI.models.Book;
import BookshelfAPI.BookshelfAPI.models.Shelf;
import BookshelfAPI.BookshelfAPI.repositories.BookRepository;
import BookshelfAPI.BookshelfAPI.repositories.ShelfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ShelfRepository shelfRepository;

    @Override
    public void addBook(BookDto bookDto) {
        Book book = null;
        if(bookDto.getShelfId()!=0){
            Shelf shelfForBook = null;
            boolean shelfExists = shelfRepository.findById(bookDto.getShelfId()).isPresent();
            if(shelfExists){
                shelfForBook = shelfRepository.findById(bookDto.getShelfId()).get();
                book = new Book(bookDto.getTitle(), bookDto.getDescription(), bookDto.getNumberOfPages(), bookDto.getYearPublished(), shelfForBook);
            }
        }else{
            book = new Book(bookDto.getTitle(), bookDto.getDescription(), bookDto.getNumberOfPages(), bookDto.getYearPublished());
        }
        bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(int bookId) {
        if(bookRepository.existsById(bookId)){
            return bookRepository.findById(bookId).get();
        }
        return null;
    }
}
package BookshelfAPI.BookshelfAPI.dtos;
import BookshelfAPI.BookshelfAPI.models.Shelf;
import java.io.Serializable;

public class ShelfDto implements Serializable {
    private String name;

    public ShelfDto(){}
    public ShelfDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
package BookshelfAPI.BookshelfAPI.dtos;
import org.springframework.lang.Nullable;
import java.io.Serializable;

public class BookDto implements Serializable {
    private String title;
    private String description;
    private int numberOfPages;
    private int yearPublished;
    @Nullable
    private int shelfId;
    
    public BookDto (){}
    public BookDto(String title, String description, int numberOfPages, int yearPublished) {
        this.title = title;
        this.description = description;
        this.numberOfPages = numberOfPages;
        this.yearPublished = yearPublished;
    }

    public BookDto(String title, String description, int numberOfPages, int yearPublished, int shelfId) {
        this.title = title;
        this.description = description;
        this.numberOfPages = numberOfPages;
        this.yearPublished = yearPublished;
        this.shelfId = shelfId;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getNumberOfPages() {
        return numberOfPages;
    }
    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }
    public int getYearPublished() {
        return yearPublished;
    }
    public void setYearPublished(int yearPublished) {
        this.yearPublished = yearPublished;
    }
    public int getShelfId() {
        return shelfId;
    }
    public void setShelfId(int shelfId) {
        this.shelfId = shelfId;
    }
}
package BookshelfAPI.BookshelfAPI.controllers;
import BookshelfAPI.BookshelfAPI.dtos.ShelfDto;
import BookshelfAPI.BookshelfAPI.models.Shelf;
import BookshelfAPI.BookshelfAPI.service.ShelfService;
import BookshelfAPI.BookshelfAPI.service.ShelfServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
public class ShelfController {

    @Autowired
    private ShelfService shelfService;

    public ShelfController() {
        this.shelfService = new ShelfServiceImpl();
    }

    @PostMapping("/shelf")
    public ResponseEntity createBookShelf(@RequestBody ShelfDto shelfDto){

        Shelf newShelf = new Shelf(shelfDto.getName());

        shelfService.createShelf(newShelf);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("shelves")
    public ResponseEntity<List<Shelf>> getAllShelves(){

        List<Shelf> shelves = shelfService.getAllShelves();

        return  ResponseEntity.status(HttpStatus.OK).body(shelves);
    }

    @GetMapping("shelf/{id}")
    public ResponseEntity<Shelf> getShelfById(@PathVariable("id") int id){
        Shelf shelf = shelfService.getShelfById(id);

        return ResponseEntity.status(HttpStatus.OK).body(shelf);
    }
}package BookshelfAPI.BookshelfAPI.controllers;
import BookshelfAPI.BookshelfAPI.dtos.BookDto;
import BookshelfAPI.BookshelfAPI.models.Book;
import BookshelfAPI.BookshelfAPI.service.BookService;
import BookshelfAPI.BookshelfAPI.service.ShelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@RestController
public class BooksController {

    @Autowired
    private BookService bookService;
    @Autowired
    private ShelfService shelfService;

    @PostMapping("book")
    public ResponseEntity addBook(@RequestBody BookDto bookDto){

        bookService.addBook(bookDto);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("books")
    public ResponseEntity<List<Book>> getAllBooks(){

        List<Book> allBooks = bookService.getAllBooks();

        return ResponseEntity.status(HttpStatus.OK).body(allBooks);
    }

    @GetMapping("books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id){

        Book bookToReturn = bookService.getBookById(id);

        if (bookToReturn==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(bookToReturn);
    }
}