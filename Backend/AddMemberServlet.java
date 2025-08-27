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

@WebServlet("/AddMemberServlet")
public class AddMemberServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
//        String memberID = request.getParameter("MemberID");
        String groupID = request.getParameter("GroupID");
        String memberName = request.getParameter("MemberName");
        String memberPosition = request.getParameter("MemberPosition");
        String mobileNumber = request.getParameter("MobileNumber");
        String aadharNumber = request.getParameter("AadharNumber");
        String maddress = request.getParameter("MAddress");
       
        
        try (Connection conn = DBConnection.getConnection()) {
           
        	String query= "INSERT INTO member (MemberName, MemberPosition, MobileNumber, AadharNumber, MAddress,GroupID) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
//            ps.setString(1, memberID);
//            ps.setString(1, groupID);
            ps.setString(1, memberName);
            ps.setString(2, memberPosition);
            ps.setString(3, mobileNumber);
            ps.setString(4, aadharNumber);
            ps.setString(5, maddress);
            ps.setString(6, groupID);
            
            int result = ps.executeUpdate();
            
            if (result > 0) {
                out.println("<script>");
                out.println("alert('Member Details Add Successfully!'); window.location='home.html';");
                out.println("</script>");
            } else {
                out.println("<script>alert('Error adding member Details. Try again.'); window.location='add_member.html';</script>");
            }
            ps.close();
            conn.close();
        } catch (Exception e) {
            out.println("<script>alert('Error: " + e.getMessage() + "'); window.location='add_member.html';</script>");
        }
    }
}
