import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DeleteMeetingServlet")
public class DeleteMeetingServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        int meetingID = Integer.parseInt(request.getParameter("meetingID"));

        try (Connection conn = DBConnection.getConnection()) {
            String query = "DELETE FROM meeting WHERE MeetingID = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, meetingID);

            int result = ps.executeUpdate();
            if (result > 0) {
                out.println("<script>");
              //  out.println("confirm('Are you sure you want to delete this meeting?')");
                out.println("alert('Meeting deleted successfully!'); window.location='ViewMeetingServlet';");
                out.println("</script>");
            } else {
                out.println("<script>alert('Error deleting meeting. Try again.'); window.location='ViewMeetingServlet';</script>");
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<script>alert('Error: " + e.getMessage() + "'); window.location='ViewMeetingServlet';</script>");
        }
    }
}
