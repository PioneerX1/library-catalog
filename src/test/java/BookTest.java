import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class BookTest {
  // @Rule
  // public DatabaseRule database = new DatabaseRule();

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


}