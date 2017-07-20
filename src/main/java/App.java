import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response)-> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("patrons", Patron.all());
      model.put("books", Book.all());
      model.put("cds", Cd.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/patrons", (request, response)-> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      Patron newPatron = new Patron(name);
      newPatron.save();
      String url = "/";
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/books", (request, response)-> {
      Map<String, Object> model = new HashMap<String, Object>();
      String title = request.queryParams("title");
      String description = request.queryParams("description");
      String author = request.queryParams("author");
      int publishYear = Integer.parseInt(request.queryParams("publishYear"));
      Book newBook = new Book(title, description, author, publishYear);
      newBook.save();
      String url = "/";
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/cds", (request, response)-> {
      Map<String, Object> model = new HashMap<String, Object>();
      String title = request.queryParams("title");
      String description = request.queryParams("description");
      String artist = request.queryParams("artist");
      int publishYear = Integer.parseInt(request.queryParams("publishYear"));
      Cd newCd = new Cd(title, description, artist, publishYear);
      newCd.save();
      String url = "/";
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/books/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Book book = Book.find(Integer.parseInt(request.params(":id")));
      model.put("book", book);
      model.put("patrons", Patron.all());
      model.put("template", "templates/book.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/books/:id/checkout", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      int patronId = Integer.parseInt(request.queryParams("patron"));
      Book book = Book.find(Integer.parseInt(request.params(":id")));
      book.checkOut(patronId);
      String url = String.format("/books/%d", book.getId());
      response.redirect(url);
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());



  }
}
