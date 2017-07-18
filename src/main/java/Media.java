import java.sql.Timestamp;
import java.util.Date;
import java.text.DateFormat;

public abstract class Media {
  public String title;
  public String description;
  public int patronId;
  public int id;
  public int publishYear;
  public Timestamp lastCheckedOut;
  public String type;

  public String getTitle() {
    return title;
  }
  public int getPatronId() {
    return patronId;
  }
  public String getDescription() {
    return description;
  }
  public int getId() {
    return id;
  }
  public int getPublishYear() {
    return publishYear;
  }
  public String getType() {
    return type;
  }
}
