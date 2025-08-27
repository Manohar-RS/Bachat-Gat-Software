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

@WebServlet("/EditBankLoan")
public class EditBankLoan extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bloanID = request.getParameter("BloanID");
        
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM bloan WHERE BloanID = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, bloanID);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                request.setAttribute("bloanID", rs.getInt("BloanID"));
                request.setAttribute("bankID", rs.getInt("BankID"));
                request.setAttribute("bankName", rs.getString("BankName"));
                request.setAttribute("approvedBankLoan", rs.getString("ApprovedBankLoan"));
                request.setAttribute("loanRepaymentPeriod", rs.getString("LoanRepaymentPeriod"));
                request.setAttribute("dateOfLoanReceipt", rs.getString("DateOfLoanReceipt"));
             
                RequestDispatcher dispatcher = request.getRequestDispatcher("edit_bloan.jsp");
                dispatcher.forward(request, response);
            } else {
                response.getWriter().println("<script>alert('Loan record not found!'); window.location='ViewBankLoan';</script>");
            }
            ps.close();
        } catch (Exception e) {
            response.getWriter().println("<script>alert('Error fetching loan data: " + e.getMessage() + "'); window.location='ViewBankLoan';</script>");
        }
    }
}


