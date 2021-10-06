
/**
 * @file InsertOkonski.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/InsertOkonski")
public class InsertOkonski extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public InsertOkonski() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String item = request.getParameter("item");
      String store = request.getParameter("store");


      Connection connection = null;
      String insertSql = " INSERT INTO myList (id, ITEM, STORE) values (default, ?, ?)";

      try {
	     DBConnectionOkonski.getDBConnection(getServletContext());
	     connection = DBConnectionOkonski.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         preparedStmt.setString(1, item);
         preparedStmt.setString(2, store);
         preparedStmt.execute();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Set response content type
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title = "Item was successfully added!";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h2 align=\"center\">" + title + "</h2>\n" + //
            "<ul>\n" + //
            "  <li><b>Item </b>: " + item + "\n" + //
            "  <li><b>Store</b>: " + store + "\n" + //
            "</ul>\n");

      out.println("<p><a href=/webproject-T3-1006-Okonski/searchOkonski.html>Search Shopping List</a> <br></p>");
      out.println("<p><a href=/webproject-T3-1006-Okonski/insertOkonski.html>Add More Items</a> <br></p>");
      out.println("</body></html>");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
