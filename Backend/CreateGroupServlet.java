import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/CreateGroupServlet")
public class CreateGroupServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
//      String groupID = request.getParameter("GroupID");
        String groupName = request.getParameter("GroupName");
        String address = request.getParameter("Address");
        String chairman = request.getParameter("GroupChairman");
        String secretary = request.getParameter("GroupSecretary");
        
        try (Connection conn = DBConnection.getConnection()) {

        	String query = "INSERT INTO bachatgat (GroupName, Address, GroupChairman, GroupSecretary) VALUES (?, ?, ?, ?)";
        	PreparedStatement ps = conn.prepareStatement(query);
//          ps.setString(1, groupID);
            ps.setString(1, groupName);
            ps.setString(2, address);
            ps.setString(3, chairman);
            ps.setString(4, secretary);
                     
            int result = ps.executeUpdate();
            if (result > 0) {
            	 out.println("<script>");
                 out.println("alert('Group Details Add Successfully');");
                 out.println("window.location.href='add_bank.html';");
                 out.println("</script>");
            } else {
            	out.println("<script>alert('Error adding Details. Try again.'); window.location='create_group.html';</script>");
            }
            ps.close();
            conn.close();
        } catch (Exception e) {
        	 e.printStackTrace();
            out.println("<script>");
            out.println("alert('Error: " + e.getMessage() + "'); window.location='create_group.html';");
            out.println("</script>");
        }
    }
}
