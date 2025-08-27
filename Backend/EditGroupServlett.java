import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;

@WebServlet("/EditGroupServlett")
public class EditGroupServlett extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String groupId = request.getParameter("GroupID");

        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM bachatgat WHERE GroupID=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(groupId));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                request.setAttribute("GroupID", rs.getInt("GroupID"));
                request.setAttribute("GroupName", rs.getString("GroupName"));
                request.setAttribute("Address", rs.getString("Address"));
                request.setAttribute("GroupChairman", rs.getString("GroupChairman"));
                request.setAttribute("GroupSecretary", rs.getString("GroupSecretary"));
                
                RequestDispatcher dispatcher = request.getRequestDispatcher("edit_group.jsp");
                dispatcher.forward(request, response);
            } else {
                response.getWriter().println("<script>alert('Group not found!'); window.location='ViewGroupServlet';</script>");
            }

            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<script>alert('Error: " + e.getMessage() + "'); window.location='ViewGroupServlet';</script>");
        }
    }
}
