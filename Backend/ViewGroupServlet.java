import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ViewGroupServlet")
public class ViewGroupServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM bachatgat";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            out.println("<html><head><title>Group List</title>");
            out.println("<link rel='stylesheet' type='text/css' href='styles.css'>"); 
            out.println("<script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>");
            out.println("<link href='https://cdn.jsdelivr.net/npm/remixicon@4.5.0/fonts/remixicon.css' rel='stylesheet' />");
            out.println("</head><body>");
            
            out.println("<h2>Group List</h2>");
            out.println("<table border='1'>");
            out.println("<tr><th>Group ID</th><th>Group Name</th><th>Address</th><th>Chairman</th><th>Secretary</th><th>Actions</th></tr>");
            
            while (rs.next()) {
                int id = rs.getInt("GroupID");
                String name = rs.getString("GroupName");
                String address = rs.getString("Address");
                String chairman = rs.getString("GroupChairman");
                String secretary = rs.getString("GroupSecretary");
                
                out.println("<tr>");
                out.println("<td>" + id + "</td>");
                out.println("<td>" + name + "</td>");
                out.println("<td>" + address + "</td>");
                out.println("<td>" + chairman + "</td>");
                out.println("<td>" + secretary + "</td>");
                out.println("<td><a href='EditGroupServlett?GroupID=" + id + "'><i class=\"ri-edit-2-line\"></i></a> ");
               // out.println("<a href='DeleteGroupServlet?GroupID=" + id + "' onclick='return confirm(\"Are you sure?\")'><i class=\"ri-delete-bin-6-line\"></i></a></td>");
                out.println("</tr>");
            }
            
            out.println("</table>");
            out.println("<br><button><a href='ViewMember'>Existing Members </a></button><br>");
            out.println("<br><a href='home.html'>Back to Home</a>");
            out.println("</body></html>");
            
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<script>alert('Error: " + e.getMessage() + "'); window.location='home.html';</script>");
        }
    }
}
