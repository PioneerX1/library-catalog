import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;
import java.util.Date;
import org.sql2o.*;

public class Cd extends Media {

  private String artist;

  public static final String DATABASE_TYPE = "cd";

  //new Cd ('Beatrice', 'lorem ipsum', 'Shakespeare', '2016);
  public Cd(String title, String description, String artist, int publishYear) {
    this.title = title;
    this.description = description;
    this.artist = artist;
    this.publishYear = publishYear;
    this.patronId = -1;
    this.type = DATABASE_TYPE;
  }

  public String getArtist() {
    return artist;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO media (title, description, artist, publishYear, patronId, type) VALUES (:title, :description, :artist, :publishYear, :patronId, :type)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("title", this.title)
        .addParameter("description", this.description)
        .addParameter("artist", this.artist)
        .addParameter("publishYear", this.publishYear)
        .addParameter("patronId", this.patronId)
        .addParameter("type", this.type)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Cd> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM media WHERE type='cd';";
      return con.createQuery(sql)
             .throwOnMappingFailure(false)
             .executeAndFetch(Cd.class);
    }
  }

  @Override
  public boolean equals(Object otherCd) {
    if (!(otherCd instanceof Cd)) {
      return false;
    } else {
      Cd newCd = (Cd) otherCd;
      return this.getTitle().equals(newCd.getTitle()) &&
             this.getDescription().equals(newCd.getDescription()) &&
             this.getArtist().equals(newCd.getArtist()) &&
             this.getPublishYear() == newCd.getPublishYear() &&
             this.getId() == newCd.getId();
    }
  }

}
