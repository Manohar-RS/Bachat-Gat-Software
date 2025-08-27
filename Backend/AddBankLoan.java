import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AddBankLoan")
public class AddBankLoan extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, 
    IOException {
    	response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        int bankID = Integer.parseInt(request.getParameter("BankID"));
        String bankName = request.getParameter("BankName");
        String approvedLoan = request.getParameter("ApprovedBankLoan");
        String repaymentPeriod = request.getParameter("LoanRepaymentPeriod");
        String dateOfLoanReceipt = request.getParameter("DateOfLoanReceipt");

        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO bloan ( BankId,BankName,ApprovedBankLoan,"
            		+ " LoanRepaymentPeriod,DateOfLoanReceipt) VALUES ( ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, bankID);
            ps.setString(2, bankName);
            ps.setString(3, approvedLoan);
            ps.setString(4, repaymentPeriod);
            ps.setString(5, dateOfLoanReceipt);

            
            int result = ps.executeUpdate();

            if (result > 0) {
            	out.println("<script>");
                out.println("alert('Bank Loan Details Add Successfully!'); window.location='ViewBankServlet';  ");
                out.println("</script>");
            } else {
            	 out.println("<script>alert('Error adding Bank Loan Details. Try again.'); window.location='ViewBankServlet';</script>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<script>alert('Error: " + e.getMessage() + "');</script>");
        }
    }
}


