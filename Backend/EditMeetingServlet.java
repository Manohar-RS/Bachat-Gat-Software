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

@WebServlet("/EditMeetingServlet")
public class EditMeetingServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String meetingID = request.getParameter("meetingID");

        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM meeting WHERE MeetingID = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, meetingID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                request.setAttribute("MeetingID", rs.getInt("MeetingID"));
                request.setAttribute("GroupID", rs.getInt("GroupID"));
                request.setAttribute("MeetingDate", rs.getString("MeetingDate"));
                request.setAttribute("MeetingLocation", rs.getString("MeetingLocation"));
                request.setAttribute("MeetingAgenda", rs.getString("MeetingAgenda"));
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher("edit_meeting.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<script>alert('Error fetching meeting details: " + e.getMessage() + "');</script>");
        }
    }
}
