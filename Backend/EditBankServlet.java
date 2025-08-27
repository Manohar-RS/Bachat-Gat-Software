import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/EditBankServlet")
public class EditBankServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        int bankID = Integer.parseInt(request.getParameter("BankID"));
        
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM bank WHERE BankID = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, bankID);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                request.setAttribute("BankID", rs.getInt("BankID"));
                //request.setAttribute("GroupID", rs.getInt("GroupID"));
                request.setAttribute("BankName", rs.getString("BankName"));
                request.setAttribute("BAddress", rs.getString("BAddress"));
               
                RequestDispatcher dispatcher = request.getRequestDispatcher("edit_bank.jsp");
                dispatcher.forward(request, response);
            } else {
                out.println("<script>alert('Bank details not found'); window.location='ViewBankServlet';</script>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<script>alert('Error: " + e.getMessage() + "');</script>");
        }
    }
}




//request.setAttribute("ApprovedBankLoan", rs.getDouble("ApprovedBankLoan"));
//request.setAttribute("LoanRepaymentPeriod", rs.getString("LoanRepaymentPeriod"));
//request.setAttribute("DateOfLoanReceipt", rs.getString("DateOfLoanReceipt"));
//
//<label for="GroupID">Group ID</label>
//<input type="number" id="GroupID" name="GroupID" value="${GroupID}"  readonly>
//<label for="ApprovedBankLoan">Approved Bank Loan</label>
//<input type="number" id="ApprovedBankLoan" name="ApprovedBankLoan" value="${ApprovedBankLoan}"  required>
//
//<label for="LoanRepaymentPeriod">Loan Repayment Period</label>
//<input type="text" id="LoanRepaymentPeriod" name="LoanRepaymentPeriod" value="${LoanRepaymentPeriod}"  required>
//
//<label for="DateOfLoanReceipt">Date Of Loan Receipt</label>
//<input type="date" id="DateOfLoanReceipt" name="DateOfLoanReceipt" value="${DateOfLoanReceipt}"  required>

