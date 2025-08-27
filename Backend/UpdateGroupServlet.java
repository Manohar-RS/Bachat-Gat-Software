import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/UpdateGroupServlet")
public class UpdateGroupServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int groupId = Integer.parseInt(request.getParameter("GroupID"));
        String groupName = request.getParameter("GroupName");
        String address = request.getParameter("Address");
        String chairman = request.getParameter("GroupChairman");
        String secretary = request.getParameter("GroupSecretary");

        try (Connection conn = DBConnection.getConnection()) {
            String query = "UPDATE bachatgat SET GroupName=?, Address=?, GroupChairman=?, GroupSecretary=? WHERE GroupID=?";
            PreparedStatement ps = conn.prepareStatement(query);
           
            ps.setString(1, groupName);
            ps.setString(2, address);
            ps.setString(3, chairman);
            ps.setString(4, secretary);
            ps.setInt(5, groupId);
            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                response.sendRedirect("ViewGroupServlet");
            } else {
                response.getWriter().println("<script>alert('Update Failed!'); window.location='EditGroupServlet?GroupID=" + groupId + "';</script>");
            }

            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<script>alert('Error: " + e.getMessage() + "'); window.location='ViewGroupServlet';</script>");
        }
    }
}
