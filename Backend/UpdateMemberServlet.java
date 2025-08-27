
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/UpdateMemberServlet")
public class UpdateMemberServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        int memberID = Integer.parseInt(request.getParameter("MemberID"));
//        String groupID = request.getParameter("GroupID");
        String memberName = request.getParameter("MemberName");
        String memberPosition = request.getParameter("MemberPosition");
        String mobileNumber = request.getParameter("MobileNumber");
        String aadharNumber = request.getParameter("AadharNumber");
        String maddress = request.getParameter("MAddress");

        try (Connection conn = DBConnection.getConnection()) {
            String query = "UPDATE member SET  MemberName=?, MemberPosition=?, MobileNumber=?, AadharNumber=?, MAddress=? WHERE MemberID=?";
            PreparedStatement ps = conn.prepareStatement(query);
//            GroupID=?,
//            ps.setString(1, groupID);
            ps.setString(1, memberName);
            ps.setString(2, memberPosition);
            ps.setString(3, mobileNumber);
            ps.setString(4, aadharNumber);
            ps.setString(5, maddress);
            ps.setInt(6, memberID);
            
            int result = ps.executeUpdate();
            if (result > 0) {
                out.println("<script>");
                out.println("alert('Member Details Updated Successfully!'); window.location='ViewMemberServlet';");
                out.println("</script>");
            } else {
                out.println("<script>alert('Error updating member details. Try again.'); window.location='edit_member.jsp?memberID=" + memberID + "';</script>");
            }
            ps.close();
            conn.close();
        } catch (Exception e) {
            out.println("<script>alert('Error: " + e.getMessage() + "'); window.location='edit_member.jsp?memberID=" + memberID + "';</script>");
        }
    }
}

//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@WebServlet("/UpdateMemberServlet")
//public class UpdateMemberServlet extends HttpServlet {
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        int memberId = Integer.parseInt(request.getParameter("MemberID"));
////        int groupId = Integer.parseInt(request.getParameter("GroupID"));
//        String memberName = request.getParameter("MemberName");
//        String memberPosition = request.getParameter("MemberPosition");
//        String mobileNumber = request.getParameter("MobileNumber");
//        String aadharNumber = request.getParameter("AadharNumber");
//        String maddress = request.getParameter("MAddress");
//       
//
//        try (Connection conn = DBConnection.getConnection()) {
//            String query = "UPDATE member1 SET MemberName=?, MemberPosition=?, MobileNumber=?, AadharNumber=?,MAddress=? WHERE MemberID=?";
//            PreparedStatement ps = conn.prepareStatement(query);
////            ps.setInt(1, groupId);GroupID=?
//            ps.setString(1, memberName);
//            ps.setString(2, memberPosition);
//            ps.setString(3, mobileNumber);
//            ps.setString(4, aadharNumber);
//            ps.setString(5, maddress);
//            ps.setInt(6,memberId);
//            
//            int rowsUpdated = ps.executeUpdate();
//
//            if (rowsUpdated > 0) {
//                response.sendRedirect("ViewMemberServlet");
//            } else {
//                response.getWriter().println("<script>alert('Update Failed!'); window.location='EditMemberServlet?GroupID=" + memberId + "';</script>");
//            }
//
//            ps.close();
//            conn.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.getWriter().println("<script>alert('Error: " + e.getMessage() + "'); window.location='ViewmemberServlet';</script>");
//        }
//    }
//}
