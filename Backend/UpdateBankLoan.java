import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/UpdateBankLoan")
public class UpdateBankLoan extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

    
        int bloanID = Integer.parseInt(request.getParameter("BloanID"));
        int bankID = Integer.parseInt(request.getParameter("BankID"));
        String bankName = request.getParameter("BankName");
        String approvedBankLoan = request.getParameter("ApprovedBankLoan");
        String loanRepaymentPeriod = request.getParameter("LoanRepaymentPeriod");
        String dateOfLoanReceipt = request.getParameter("DateOfLoanReceipt");
        

        try (Connection conn = DBConnection.getConnection()) {
           
            String query = "UPDATE bloan SET BankID=?, BankName=?, ApprovedBankLoan=?, LoanRepaymentPeriod=?, DateOfLoanReceipt=? WHERE BloanID=?";
            PreparedStatement ps = conn.prepareStatement(query);
            
            ps.setInt(1,bankID);
            ps.setString(2,bankName);
            ps.setString(3,approvedBankLoan);
            ps.setString(4,loanRepaymentPeriod);
            ps.setString(5,dateOfLoanReceipt);
            ps.setInt(6,bloanID);
            

            int result = ps.executeUpdate();

            if (result > 0) {
                out.println("<script>");
                out.println("alert('Bank Loan Details Updated Successfully!'); window.location='ViewBankLoan';");
                out.println("</script>");
            } else {
                out.println("<script>alert('Error updating loan details. Try again.'); window.location='edit_bloan.jsp?BloanID=" + bloanID + "';</script>");
            }

            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<script>alert('Error: " + e.getMessage() + "'); window.location='edit_bloan.jsp?BloanID=" + bloanID + "';</script>");
        }
    }
}
