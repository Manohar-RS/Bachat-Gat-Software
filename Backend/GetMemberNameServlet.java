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

@WebServlet("/GetMemberNameServlet")
public class GetMemberNameServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        
        String memberID = request.getParameter("MemberID");
        
        if (memberID != null && !memberID.trim().isEmpty()) {
        	
        	try (Connection conn = DBConnection.getConnection()){
             String sql = "SELECT MemberName FROM member WHERE MemberID=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(memberID));
                ResultSet rs = ps.executeQuery();
                
                if (rs.next()) {
                    out.print(rs.getString("MemberName"));
                } else {
                    out.print("Member Not Found");
                }
                
                rs.close();
                ps.close();
                conn.close();
            } catch (Exception e) {
                out.print("Error");
                e.printStackTrace();
            }
        }
    }
}
