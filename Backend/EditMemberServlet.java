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

@WebServlet("/EditMemberServlet")
public class EditMemberServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String memberID = request.getParameter("memberID");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            String query = "SELECT * FROM member WHERE MemberID=?";
            ps = conn.prepareStatement(query);
            ps.setString(1, memberID);
            rs = ps.executeQuery();

            if (rs.next()) {
                request.setAttribute("MemberID", rs.getInt("MemberID"));
//                request.setAttribute("GroupID", rs.getInt("GroupID"));
                request.setAttribute("MemberName", rs.getString("MemberName"));
                request.setAttribute("MemberPosition", rs.getString("MemberPosition"));
                request.setAttribute("MobileNumber", rs.getString("MobileNumber"));
                request.setAttribute("AadharNumber", rs.getString("AadharNumber"));
                request.setAttribute("MAddress", rs.getString("MAddress"));

                RequestDispatcher dispatcher = request.getRequestDispatcher("edit_member.jsp");
                dispatcher.forward(request, response);
            } else {
                response.getWriter().println("<script>alert('Member not found!'); window.location='ViewMemberServlet';</script>");
            }
        } catch (Exception e) {
            response.getWriter().println("<script>alert('Error: " + e.getMessage() + "'); window.location='ViewMemberServlet';</script>");
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.RequestDispatcher;
//
//@WebServlet("/EditMemberServlet")
//public class EditMemberServlet extends HttpServlet {
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String memberID = request.getParameter("MemberID");
////        String groupID = request.getParameter("GroupID");
////        String mobileNumber = request.getParameter("MobileNumber");
////        String aadharNumber = request.getParameter("AadharNumber");
//        
//        try (Connection conn = DBConnection.getConnection()) {
//            String query = "SELECT * FROM member1 WHERE MemberID=?";
//            PreparedStatement ps = conn.prepareStatement(query);
//            ps.setInt(1, Integer.parseInt(memberID));
//            ResultSet rs = ps.executeQuery();
//
//            if (rs.next()) {
//                request.setAttribute("MemberID", rs.getInt("MemberID"));
////                request.setAttribute("GroupID", rs.getInt("GroupID"));
//                request.setAttribute("MemberName", rs.getString("MemberName"));
//                request.setAttribute("MemberPosition", rs.getString("MemberPosition"));
//                request.setAttribute("MobileNumber", rs.getString("MobileNumber"));
//                request.setAttribute("AadharNumber", rs.getString("AadharNumber"));
//                request.setAttribute("MAddress", rs.getString("MAddress"));
//                
//                RequestDispatcher dispatcher = request.getRequestDispatcher("edit_member.jsp");
//                dispatcher.forward(request, response);
//            } else {
//                response.getWriter().println("<script>alert('Member not found!'); window.location='ViewMemberServlet';</script>");
//            }
//
//            ps.close();
//            conn.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.getWriter().println("<script>alert('Error: " + e.getMessage() + "'); window.location='ViewMemberServlet';</script>");
//        }
//    }
//}
//
//
//
////<label for="GroupID">Group ID</label>
////<input type="nummber" id="GroupID" name="GroupID" value="${GroupID}" placeholder="Enter The Group ID" readonly>
//
//
