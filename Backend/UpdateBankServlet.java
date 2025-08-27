import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/UpdateBankServlet")
public class UpdateBankServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        int bankID = Integer.parseInt(request.getParameter("BankID"));
        //int groupID = Integer.parseInt(request.getParameter("GroupID"));
        String bankName = request.getParameter("BankName");
        //double approvedLoan = Double.parseDouble(request.getParameter("ApprovedBankLoan"));
        //String repaymentPeriod = request.getParameter("LoanRepaymentPeriod");
        //String dateOfLoanReceipt = request.getParameter("DateOfLoanReceipt");
        String baddress = request.getParameter("BAddress");
        
        try (Connection conn = DBConnection.getConnection()) {
            String query = "UPDATE bank SET  BankName=?,  BAddress=? WHERE BankID=?";
            PreparedStatement ps = conn.prepareStatement(query);
            
           // ps.setInt(1, groupID);
            ps.setString(1, bankName);
            //ps.setDouble(3, approvedLoan);
            //ps.setString(4, repaymentPeriod);
            //ps.setString(5, dateOfLoanReceipt);
            ps.setString(2, baddress);
            ps.setInt(3, bankID);
            
            int result = ps.executeUpdate();
            
            if (result > 0) {
                out.println("<script>alert('Bank details updated successfully!'); window.location='ViewBankServlet';</script>");
            } else {
                out.println("<script>alert('Error updating bank details. Try again.'); window.location='edit_bank.jsp?BankID=" + bankID + "';</script>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<script>alert('Error: " + e.getMessage() + "');</script>");
        }
    }
}


