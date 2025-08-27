import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/UpdateMeetingServlet")
public class UpdateMeetingServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        int meetingID = Integer.parseInt(request.getParameter("MeetingID"));
        //int groupID = Integer.parseInt(request.getParameter("GroupID"));
        String meetingDate = request.getParameter("MeetingDate");
        String meetingLocation = request.getParameter("MeetingLocation");
        String meetingAgenda = request.getParameter("MeetingAgenda");

        try (Connection conn = DBConnection.getConnection()) {
            String query = "UPDATE meeting SET MeetingDate = ?, MeetingLocation = ?, MeetingAgenda = ? WHERE MeetingID = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, meetingDate);
            ps.setString(2, meetingLocation);
            ps.setString(3, meetingAgenda);
            ps.setInt(4, meetingID);

            int result = ps.executeUpdate();
            if (result > 0) {
                out.println("<script>");
                out.println("alert('Meeting Details Updated Successfully!'); window.location='ViewMeetingServlet';");
                out.println("</script>");
            } else {
                out.println("<script>alert('Error updating meeting details. Try again.'); window.location='edit_meeting.jsp?meetingID=" + meetingID + "';</script>");
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<script>alert('Error: " + e.getMessage() + "'); window.location='edit_meeting.jsp?meetingID=" + meetingID + "';</script>");
        }
    }
}
