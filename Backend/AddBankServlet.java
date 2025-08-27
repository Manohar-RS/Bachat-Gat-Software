import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AddBankServlet")
public class AddBankServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/html");
        PrintWriter out = response.getWriter();
 
        String bankName = request.getParameter("BankName");
        String baddress = request.getParameter("BAddress");

        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO bank ( BankName, BAddress) VALUES ( ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, bankName);
            ps.setString(2, baddress);

            
            int result = ps.executeUpdate();

            if (result > 0) {
            	out.println("<script>");
                out.println("alert('Bank Details Add Successfully!'); window.location='home.html';  ");
                out.println("</script>");
            } else {
            	 out.println("<script>alert('Error adding Bank Details. Try again.'); window.location='add_bank.html';</script>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<script>alert('Error: " + e.getMessage() + "');</script>");
        }
    }
}




//<label for="GroupID">Group ID</label>
//<input type="number" id="GroupID" name="GroupID" placeholder="Enter The Group ID" required>

//ps.setDouble(3, approvedLoan);
//ps.setString(4, repaymentPeriod);
//ps.setString(5, dateOfLoanReceipt);
//double approvedLoan = Double.parseDouble(request.getParameter("ApprovedBankLoan"));
//String repaymentPeriod = request.getParameter("LoanRepaymentPeriod");
//String dateOfLoanReceipt = request.getParameter("DateOfLoanReceipt");
//

//<label for="ApprovedBankLoan">Approved Bank Loan</label>
//<input type="number" id="ApprovedBankLoan" name="ApprovedBankLoan" placeholder="Enter The Approved Bank Loan" required>
//
//<label for="LoanRepaymentPeriod">Loan Repayment Period</label>
//<input type="text" id="LoanRepaymentPeriod" name="LoanRepaymentPeriod" placeholder="Enter The Loan Repayment Period" required>
//
//<label for="DateOfLoanReceipt">Date Of Loan Receipt</label>
//<input type="date" id="DateOfLoanReceipt" name="DateOfLoanReceipt" placeholder="Enter The Date Of Loan Receipt" required>

