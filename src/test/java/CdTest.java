import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class CdTest {
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Cd_instantiatesCorrectly_true() {
    Cd testCd = new Cd("We Write the Songs", "Lorem ipsum etc", "Barry Manilow", 1971);
    assertEquals(true, testCd instanceof Cd);
  }
  @Test
  public void getTitle_retrieveTitle_WeWriteTheSongs() {
    Cd testCd = new Cd("We Write the Songs", "Lorem ipsum etc", "Barry Manilow", 1971);
    assertEquals("We Write the Songs", testCd.getTitle());
  }
  @Test
  public void getArtist_retrieveArtist_BarryManilow() {
    Cd testCd = new Cd("We Write the Songs", "Lorem ipsum etc", "Barry Manilow", 1971);
    assertEquals("Barry Manilow", testCd.getArtist());
  }
  @Test
  public void getPatronId_retrievesPatronIdAfterInstantiation_NegativeOne() {
    Cd testCd = new Cd("We Write the Songs", "Lorem ipsum etc", "Barry Manilow", 1971);
    assertEquals(-1, testCd.getPatronId());
  }
  @Test
  public void equals_returnsTrueIfAllFieldsAreTheSame_true() {
    Cd testCd = new Cd("We Write the Songs", "Lorem ipsum etc", "Barry Manilow", 1971);
    Cd testCd2 = new Cd("We Write the Songs", "Lorem ipsum etc", "Barry Manilow", 1971);
    assertTrue(testCd.equals(testCd2));
  }
  @Test
  public void save_successfullyAddsCdToDatabase_List() {
    Cd testCd = new Cd("We Write the Songs", "Lorem ipsum etc", "Barry Manilow", 1971);
    testCd.save();
    assertTrue(Cd.all().get(0).equals(testCd));
  }
  @Test
  public void save_assignsIdToCd_1() {
    Cd testCd = new Cd("We Write the Songs", "Lorem ipsum etc", "Barry Manilow", 1971);
    testCd.save();
    Cd savedCd = Cd.all().get(0);
    assertTrue(Cd.all().get(0).getId() > 0);
    assertEquals(testCd.getId(), savedCd.getId());
  }


}
