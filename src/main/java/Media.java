import java.sql.Timestamp;
import java.util.Date;
import java.text.DateFormat;
import org.sql2o.*;


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

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM media WHERE id=:id";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }
}
