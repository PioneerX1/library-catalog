import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class BookTest {
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Book_instantiatesCorrectly_true() {
    Book testBook = new Book("Macbeth", "lorem ipsum", "Shakespeare", 1600);
    assertEquals(true, testBook instanceof Book);
  }
  @Test
  public void getTitle_retrieveTitle_Macbeth() {
    Book testBook = new Book("Macbeth", "lorem ipsum", "Shakespeare", 1600);
    assertEquals("Macbeth", testBook.getTitle());
  }
  @Test
  public void getAuthor_retrieveAuthor_Shakespeare() {
    Book testBook = new Book("Macbeth", "lorem ipsum", "Shakespeare", 1600);
    assertEquals("Shakespeare", testBook.getAuthor());
  }
  @Test
  public void getPatronId_retrievesPatronIdAfterInstantiation_NegativeOne() {
    Book testBook = new Book("Macbeth", "lorem ipsum", "Shakespeare", 1600);
    assertEquals(-1, testBook.getPatronId());
  }
  @Test
  public void equals_returnsTrueIfAllFieldsAreTheSame_true() {
    Book testBook = new Book("Macbeth", "lorem ipsum", "Shakespeare", 1600);
    Book testBook2 = new Book("Macbeth", "lorem ipsum", "Shakespeare", 1600);
    assertTrue(testBook.equals(testBook2));
  }
  @Test
  public void save_successfullyAddsBookToDatabase_List() {
    Book testBook = new Book("Macbeth", "lorem ipsum", "Shakespeare", 1600);
    testBook.save();
    assertTrue(Book.all().get(0).equals(testBook));
  }
  @Test
  public void save_assignsIdToBook_1() {
    Book testBook = new Book("Macbeth", "lorem ipsum", "Shakespeare", 1600);
    testBook.save();
    Book savedBook = Book.all().get(0);
    assertTrue(Book.all().get(0).getId() > 0);
    assertEquals(testBook.getId(), savedBook.getId());
  }

  @Test
  public void all_returnsAllInstancesOfBooks_true() {
    Book firstBook = new Book("Macbeth", "lorem ipsum", "Shakespeare", 1600);
    firstBook.save();
    Book secondBook = new Book("Bible", "lorem ipsum", "God", 1565);
    secondBook.save();
    assertEquals(true, Book.all().get(0).equals(firstBook));
    assertEquals(true, Book.all().get(1).equals(secondBook));
  }

  @Test
  public void find_returnsBookWithSameId_secondBook() {
    Book firstBook = new Book("Macbeth", "lorem ipsum", "Shakespeare", 1600);
    firstBook.save();
    Book secondBook = new Book("Bible", "lorem ipsum", "God", 1565);
    secondBook.save();
    assertEquals(Book.find(secondBook.getId()), secondBook);
  }

  @Test
  public void update_saveOverOldAttributesOfBook_1590() {
    Book firstBook = new Book("Macbeth", "lorem ipsum", "Shakespeare", 1600);
    firstBook.save();
    firstBook.update("Macbeth", "lorem ipsum", "Shakespeare", 1590);
    assertEquals(1590, Book.all().get(0).getPublishYear());
  }

  @Test
  public void delete_deletesABookFromDB_null() {
    Book firstBook = new Book("Macbeth", "lorem ipsum", "Shakespeare", 1600);
    firstBook.save();
    int testId = firstBook.getId();
    firstBook.delete();
    assertEquals(null, Book.find(testId));
  }


}
