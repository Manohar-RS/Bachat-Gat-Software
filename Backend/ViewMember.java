import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ViewMember")
public class ViewMember extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM member";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            out.println("<html><head>");
            out.println("<title>Member Details</title>");
            out.println("<link rel='stylesheet' type='text/css' href='styles.css'>");
            out.println("<script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>");
            out.println("<link href='https://cdn.jsdelivr.net/npm/remixicon@4.5.0/fonts/remixicon.css' rel='stylesheet' />");
            out.println("</head><body>");
            
            out.println("<table border='1'>");
            out.println("<tr><th>Member ID</th><th>Group ID</th><th>Name</th><th>Position</th><th>Mobile No.</th></tr>");
            
            out.println("<h1>Pragati Mahila Bachat Gat</h1>");
            out.println("<br><h2>Member Details</h2>");
            while (rs.next()) {
                int memberID = rs.getInt("MemberID");
                out.println("<tr>");
                out.println("<td>" + rs.getInt("MemberID") + "</td>");
                out.println("<td>" + rs.getString("GroupID") + "</td>");
                out.println("<td>" + rs.getString("MemberName") + "</td>");
                out.println("<td>" + rs.getString("MemberPosition") + "</td>");
                out.println("<td>" + rs.getString("MobileNumber") + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("<br><a href='ViewGroupServlet'>< Back </a>");
            out.println("</body></html>");

            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<script>alert('Error fetching data: " + e.getMessage() + "');</script>");
        }
    }
}