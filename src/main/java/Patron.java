import org.sql2o.*;
import java.util.*;

public class Patron {
  private String name;
  private int id;

  public Patron (String name) {
    this.name = name;
  }

  public String getName () {
    return name;
  }

  public int getId () {
    return id;
  }

  public void save (){
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO patrons (name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Patron> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM patrons;";
      return con.createQuery(sql)
             .throwOnMappingFailure(false)
             .executeAndFetch(Patron.class);
    }
  }

  @Override
  public boolean equals(Object otherPatron) {
    if (!(otherPatron instanceof Patron)) {
      return false;
    } else {
      Patron newPatron = (Patron) otherPatron;
      return this.getName().equals(newPatron.getName()) &&
             this.getId() == newPatron.getId();
    }
  }

  public static Patron find (int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM patrons WHERE id = :id";
      return con.createQuery(sql)
              .addParameter("id", id)
              .throwOnMappingFailure(false)
              .executeAndFetchFirst(Patron.class);
    }
  }

  public void update(String name) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE patrons SET name = :name WHERE id = :id";
      con.createQuery(sql)
              .addParameter("id", id)
              .addParameter("name", name)
              .executeUpdate();
    }
  }
}
