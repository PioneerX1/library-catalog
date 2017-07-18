import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import java.util.Date;
import org.sql2o.*;

public class Book extends Media {

  private String author;
  //new Book ('Beatrice', 'lorem ipsum', 'Shakespeare', '2016-08-11');
  public Book(String title, String description, String author, int publishYear) {
    this.title = title;
    this.description = description;
    this.author = author;
    this.publishYear = publishYear;
    this.patronId = -1;
  }

  public String getAuthor() {
    return author;
  }

  @Override
  public boolean equals(Object otherBook) {
    if (!(otherBook instanceof Book)) {
      return false;
    } else {
      Book newBook = (Book) otherBook;
      return this.getTitle().equals(newBook.getTitle()) &&
             this.getDescription().equals(newBook.getDescription()) &&
             this.getAuthor().equals(newBook.getAuthor()) &&
             this.getPublishYear() == newBook.getPublishYear() &&
             this.getId() == newBook.getId();
    }
  }

}
