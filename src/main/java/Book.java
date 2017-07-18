import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import java.util.Date;
import org.sql2o.*;

public class Book extends Media {

  private String author;

  public static final String DATABASE_TYPE = "book";

  //new Book ('Beatrice', 'lorem ipsum', 'Shakespeare', '2016-08-11');
  public Book(String title, String description, String author, int publishYear) {
    this.title = title;
    this.description = description;
    this.author = author;
    this.publishYear = publishYear;
    this.patronId = -1;
    this.type = DATABASE_TYPE;
  }

  public String getAuthor() {
    return author;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO media (title, description, author, publishYear, patronId, type) VALUES (:title, :description, :author, :publishYear, :patronId, :type)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("title", this.title)
        .addParameter("description", this.description)
        .addParameter("author", this.author)
        .addParameter("publishYear", this.publishYear)
        .addParameter("patronId", this.patronId)
        .addParameter("type", this.type)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Book> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM media WHERE type = 'book';";
      return con.createQuery(sql)
             .throwOnMappingFailure(false)
             .executeAndFetch(Book.class);
    }
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

  public static Book find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM media WHERE id=:id";
      Book book = con.createQuery(sql)
        .addParameter("id", id)
        .throwOnMappingFailure(false)
        .executeAndFetchFirst(Book.class);
      return book;
    }
  }

  public void update(String title, String description, String author, int publishYear) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE media SET title=:title, description=:description, author=:author, publishYear=:publishYear WHERE id=:id";
      con.createQuery(sql)
        .addParameter("title", title)
        .addParameter("description", description)
        .addParameter("author", author)
        .addParameter("publishYear", publishYear)
        .addParameter("id", id)  
        .executeUpdate();
    }
  }

}
