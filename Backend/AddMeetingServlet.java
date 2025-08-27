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

@WebServlet("/AddMeetingServlet")
public class AddMeetingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
//      String meetingID = request.getParameter("MeetingID");
        String groupID = request.getParameter("GroupID");
        String meetingDate = request.getParameter("MeetingDate");
        String meetingLoc = request.getParameter("MeetingLocation");
        String meetingAgenda = request.getParameter("MeetingAgenda");
      
        
        try (Connection conn = DBConnection.getConnection()){
            
        	String query = "INSERT INTO meeting (GroupID, MeetingDate, MeetingLocation, MeetingAgenda) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
//          ps.setString(1, meetingID);
            ps.setString(1, groupID); 
            ps.setString(2, meetingDate);
            ps.setString(3, meetingLoc);
            ps.setString(4, meetingAgenda);
            
            
            int result = ps.executeUpdate();
            if (result > 0) {
                out.println("<script>");
                out.println(" alert('Meeting Details Add Successfully!'); window.location='home.html'; ");
                out.println("</script>");
            } else {
                out.println("<script>alert('Error adding Meeting Details. Try again.'); window.location='add_meeting.html';</script>");
            }
            ps.close();
           conn.close();
           
        } catch (Exception e) {
            out.print("<script>alert('Error: \" + e.getMessage() + \"');window.location='meeting.html';</script>\");");
        }
    }
}
