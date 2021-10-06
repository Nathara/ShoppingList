import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SearchOkonski")
public class SearchOkonski extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SearchOkonski() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String keyword = request.getParameter("keyword");
      search(keyword, response);
   }

   void search(String keyword, HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String title1 = "We Love Shopping!";
      String title2 = "Welcome To Your Shopping List!";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title1 + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h1 align=\"center\">" + title2 + "</h1><hr>\n");

      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try {
         DBConnectionOkonski.getDBConnection(getServletContext());
         connection = DBConnectionOkonski.connection;

         if (keyword.isEmpty()) {
            String selectSQL = "SELECT * FROM myList";
            preparedStatement = connection.prepareStatement(selectSQL);
         } else {
            String selectSQL = "SELECT * FROM myList WHERE ITEM LIKE ?";
            String theListItem = keyword + "%";
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setString(1, theListItem);
         }
         ResultSet rs = preparedStatement.executeQuery();

         while (rs.next()) {
            String item = rs.getString("item").trim();
            String store = rs.getString("store").trim();


            if (keyword.isEmpty() || item.contains(keyword)) {
               out.println(" " + item + " ");
               out.println("from " + store + "<br>");

            }
            
         }

         out.println("<p><a href=/webproject-T3-1006-Okonski/searchOkonski.html>Go Back To Search</a> <br></P>");
         out.println("<p><a href=/webproject-T3-1006-Okonski/insertOkonski.html>Add More Items</a> <br></p>");
         out.println("<p><div><button onClick=\"window.print()\">Print My List!\r\n"
         		+ "</button></div></p>");
         out.println("</body></html>");
         rs.close();
         preparedStatement.close();
         connection.close();
      } catch (SQLException se) {
         se.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if (preparedStatement != null)
               preparedStatement.close();
         } catch (SQLException se2) {
         }
         try {
            if (connection != null)
               connection.close();
         } catch (SQLException se) {
            se.printStackTrace();
         }
      }
   }
   

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
