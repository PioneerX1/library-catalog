import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class PatronTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Patron_instantiatesCorrectly_true() {
    Patron testPatron = new Patron("Joe Schmo");
    assertEquals(true, testPatron instanceof Patron);
  }

  @Test
  public void getName_getsNamefromPatron_Joe_Schmo() {
    Patron testPatron = new Patron("Joe Schmo");
    assertEquals("Joe Schmo", testPatron.getName());
  }

  @Test
  public void getId_instantiatesWithId_true() {
    Patron testPatron = new Patron("Joe Schmo");
    testPatron.save();
    assertTrue(testPatron.getId() > 0);
  }

  @Test
  public void all_retrievesAllPatronObjects_true() {
    Patron firstPatron = new Patron("Joe Schmo");
    firstPatron.save();
    Patron secondPatron = new Patron("Jill Shill");
    secondPatron.save();
    assertTrue(Patron.all().get(0).equals(firstPatron));
    assertTrue(Patron.all().get(1).equals(secondPatron));
  }

  @Test
  public void find_retrievesPatronBasedOnPatronId_true() {
    Patron testPatron = new Patron("Joe Schmo");
    testPatron.save();
    assertTrue(testPatron.equals(Patron.find(testPatron.getId())));
  }

  @Test
  public void update_updatesPatronName_Tommen() {
    Patron testPatron = new Patron("Joe Schmo");
    testPatron.save();
    testPatron.update("Tommen");
    Patron savedPatron = Patron.find(testPatron.getId());
    assertEquals("Tommen", savedPatron.getName());

  }
}
