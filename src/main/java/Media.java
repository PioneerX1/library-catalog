import java.sql.Timestamp;
import java.util.Date;
import java.text.DateFormat;
import org.sql2o.*;
import java.util.*;
import java.util.List;
import java.util.ArrayList;



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

  public boolean isCheckedOut() {
    if (patronId > 0) {
      return true;
    } else {
      return false;
    }
  }

  public void checkOut(int patronId) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE media SET patronId = :patronId WHERE id=:id";
      con.createQuery(sql)
        .addParameter("id", id)
        .addParameter("patronId", patronId)
        .executeUpdate();
    }
  }

  public void checkIn() {
    try(Connection con = DB.sql2o.open()){
      String sql = "UPDATE media SET patronId = -1 WHERE id=:id";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }


}
