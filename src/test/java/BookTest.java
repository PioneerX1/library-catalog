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

  @Test
  public void checkOut_checksOutBook_true() {
    Book testBook = new Book("Macbeth", "lorem ipsum", "Shakespeare", 1600);
    testBook.save();
    int patronId = 9;
    testBook.checkOut(patronId);
    assertTrue(Book.find(testBook.getId()).getPatronId() > 0);
  }

  @Test
  public void isCheckedOut_checksIfCheckedOut_true() {
    Book testBook = new Book("Macbeth", "lorem ipsum", "Shakespeare", 1600);
    testBook.save();
    int patronId = 9;
    testBook.checkOut(patronId);
    Book savedBook = Book.find(testBook.getId());
    assertEquals(savedBook.isCheckedOut(),true);
  }

  @Test
  public void checkIn_returnsBook_true() {
    Book testBook = new Book("Macbeth", "lorem ipsum", "Shakespeare", 1600);
    testBook.save();
    int patronId = 9;
    testBook.checkOut(patronId);
    testBook.checkIn();
    Book savedBook = Book.find(testBook.getId());
    assertTrue(Book.find(testBook.getId()).getPatronId() == -1);
    assertEquals(savedBook.isCheckedOut(),false);
  }

  @Test
  public void getAllCheckedOut_masterList_List() {
    Book firstBook = new Book("Macbeth", "lorem ipsum", "Shakespeare", 1600);
    firstBook.save();
    Book secondBook = new Book("Bible", "lorem ipsum", "God", 1565);
    secondBook.save();
    int patronId = 9;
    firstBook.checkOut(patronId);
    secondBook.checkOut(patronId);
    Book savedFirstBook = Book.find(firstBook.getId());
    Book savedSecondBook = Book.find(secondBook.getId());
    assertTrue(Book.getAllCheckedOut().contains(savedFirstBook));
    assertTrue(Book.getAllCheckedOut().contains(savedSecondBook));
  }

  @Test
  public void getAllCheckedOut_BySpecificPatron_List() {
    Book firstBook = new Book("Macbeth", "lorem ipsum", "Shakespeare", 1600);
    firstBook.save();
    Book secondBook = new Book("Bible", "lorem ipsum", "God", 1565);
    secondBook.save();
    int patronId = 9;
    firstBook.checkOut(patronId);
    secondBook.checkOut(patronId);
    Book savedFirstBook = Book.find(firstBook.getId());
    Book savedSecondBook = Book.find(secondBook.getId());
    assertTrue(Book.getAllCheckedOut(patronId).contains(savedFirstBook));
    assertTrue(Book.getAllCheckedOut(patronId).contains(savedSecondBook));
  }

}
