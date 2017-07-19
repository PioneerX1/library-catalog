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

  @Test
  public void all_returnsAllInstancesOfCDs_true() {
    Cd firstCd = new Cd("We Write the Songs", "Lorem ipsum etc", "Barry Manilow", 1971);
    firstCd.save();
    Cd secondCd = new Cd("They Write the Songs", "Lorem ipsum etc", "Barry White", 1981);
    secondCd.save();
    assertEquals(true, Cd.all().get(0).equals(firstCd));
    assertEquals(true, Cd.all().get(1).equals(secondCd));
  }

  @Test
  public void find_returnsCDWithSameId_secondCD() {
    Cd firstCd = new Cd("We Write the Songs", "Lorem ipsum etc", "Barry Manilow", 1971);
    firstCd.save();
    Cd secondCd = new Cd("They Write the Songs", "Lorem ipsum etc", "Barry White", 1981);
    secondCd.save();
    assertEquals(Cd.find(secondCd.getId()), secondCd);
  }

  @Test
  public void update_saveOverOldAttributesOfCd() {
    Cd firstCd = new Cd("We Write the Songs", "Lorem ipsum etc", "Barry Manilow", 1971);
    firstCd.save();
    firstCd.update("We Write the Songs", "Lorem ipsum etc", "Barry White", 1981);
    assertEquals("Barry White", Cd.all().get(0).getArtist());
  }

  @Test
  public void delete_deletesACdFromDB_null() {
    Cd firstCd = new Cd("We Write the Songs", "Lorem ipsum etc", "Barry Manilow", 1971);
    firstCd.save();
    int testId = firstCd.getId();
    firstCd.delete();
    assertEquals(null, Cd.find(testId));
  }

  @Test
  public void checkOut_checksOutCd() {
    Cd testCd = new Cd("We Write the Songs", "Lorem ipsum etc", "Barry Manilow", 1971);
    testCd.save();
    int patronId = 9;
    testCd.checkOut(patronId);
    assertTrue(Cd.find(testCd.getId()).getPatronId() > 0);
  }

  @Test
  public void isCheckedOut_checksIfCheckedOut_true() {
    Cd testCd = new Cd("We Write the Songs", "Lorem ipsum etc", "Barry Manilow", 1971);
    testCd.save();
    int patronId = 9;
    testCd.checkOut(patronId);
    Cd savedCd = Cd.find(testCd.getId());
    assertEquals(savedCd.isCheckedOut(),true);
  }

  @Test
  public void checkIn_returnsCd_true() {
    Cd testCd = new Cd("We Write the Songs", "Lorem ipsum etc", "Barry Manilow", 1971);
    testCd.save();
    int patronId = 9;
    testCd.checkOut(patronId);
    testCd.checkIn();
    Cd savedCd = Cd.find(testCd.getId());
    assertTrue(Cd.find(testCd.getId()).getPatronId() == -1);
    assertEquals(savedCd.isCheckedOut(),false);
  }

  @Test
  public void getAllCheckedOut_List() {
    Cd firstCd = new Cd("We Write the Songs", "Lorem ipsum etc", "Barry Manilow", 1971);
    firstCd.save();
    Cd secondCd = new Cd("They Write the Songs", "Lorem ipsum etc", "Barry White", 1981);
    secondCd.save();
    int patronId = 9;
    firstCd.checkOut(patronId);
    secondCd.checkOut(patronId);    
    Cd savedFirstCd = Cd.find(firstCd.getId());
    Cd savedSecondCd = Cd.find(secondCd.getId());
    assertTrue(Cd.getAllCheckedOut().contains(savedFirstCd));
    assertTrue(Cd.getAllCheckedOut().contains(savedSecondCd));
  }


}
