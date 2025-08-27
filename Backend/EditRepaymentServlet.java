import java.io.IOException;
import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;

@WebServlet("/EditRepaymentServlet")
public class EditRepaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        int repaymentID = Integer.parseInt(request.getParameter("repaymentID"));
       // Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try (Connection conn = DBConnection.getConnection()){
             
            String sql = "SELECT * FROM installment WHERE RepaymentID = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, repaymentID);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                request.setAttribute("RepaymentID", rs.getInt("RepaymentID"));
                request.setAttribute("LoanID", rs.getInt("LoanID"));
                request.setAttribute("MemberID", rs.getInt("MemberID"));
                request.setAttribute("MemberName", rs.getString("MemberName"));
                request.setAttribute("RepaymentDate", rs.getString("RepaymentDate"));
                request.setAttribute("BankAmount", rs.getDouble("BankAmount"));
                request.setAttribute("InternalAmount", rs.getDouble("InternalAmount"));
                request.setAttribute("InternalInterest", rs.getDouble("InternalInterest"));
                request.setAttribute("SavingInstallment", rs.getDouble("SavingInstallment"));
                
                RequestDispatcher dispatcher = request.getRequestDispatcher("edit_repayment.jsp");
                dispatcher.forward(request, response);
            } else {
                response.getWriter().println("<script>alert('Repayment record not found!'); window.location='ViewRepaymentServlet';</script>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<script>alert('Error: " + e.getMessage() + "'); window.location='ViewRepaymentServlet';</script>");
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
               // if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
