import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/MyServletOkonskiDB")
public class MyServletOkonskiDB extends HttpServlet {
   private static final long serialVersionUID = 1L;
   static String url = "jdbc:mysql://ec2-3-17-142-69.us-east-2.compute.amazonaws.com:3306/ShoppingList" + "?useSSL=false";
   static String user = "newmysqlremoteuser";
   static String password = "mypassword";
   static Connection connection = null;

   public MyServletOkonskiDB() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setContentType("text/html;charset=UTF-8");
      PrintWriter out = response.getWriter();
      String title1 = "We Love Shopping!";
      String title2 = "Welcome to Your Shopping List!!";
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
      out.println(docType + //
            "<html>\n" + //
            "<head><title>" + title1 + "</title></head>\n" + //
            "<body bgcolor=\"#f0f0f0\">\n" + //
            "<h1 align=\"center\">" + title2 + "</h1>\n"+ //
      		"<hr>"); //
      try {
         Class.forName("com.mysql.jdbc.Driver");
      } catch (ClassNotFoundException e) {
         System.out.println("Where is your MySQL JDBC Driver?");
         e.printStackTrace();
         return;
      }
      connection = null;
      try {
         connection = DriverManager.getConnection(url, user, password);
      } catch (SQLException e) {
         System.out.println("Connection Failed! Check output console");
         e.printStackTrace();
         return;
      }
      if (connection != null) {
      } else {
         System.out.println("Failed to make connection!");
      }
      try {
         String selectSQL = "SELECT * FROM myList";
         response.getWriter().println("<b>Current Items In The List:</b><br>");
         PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
         ResultSet rs = preparedStatement.executeQuery();
         while (rs.next()) {
            String id = rs.getString("ID");
            String item = rs.getString("ITEM");
            String store = rs.getString("STORE");

            response.getWriter().append("ID: " + id + ", ");
            response.getWriter().append("Item: " + item + ", ");
            response.getWriter().append("Store: " + store + "<br> ");

         }
         out.println("<p><a href=/webproject-T3-1006-Okonski/searchOkonski.html>Search Shopping List</a> <br></p>");
         out.println("<p><a href=/webproject-T3-1006-Okonski/insertOkonski.html>Add Items To List</a> <br></p>");
         out.println("</body></html>");
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }
}
