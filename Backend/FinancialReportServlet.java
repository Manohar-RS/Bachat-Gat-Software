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

@WebServlet("/FinancialReportServlet")
public class FinancialReportServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String groupId = request.getParameter("GroupId");
        String bankId = request.getParameter("BankId");
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Project1", "root", "root");

            // Fetch Group Information
            String groupQuery = "SELECT * FROM bachatgat WHERE GroupId = ?";
            PreparedStatement psGroup = conn.prepareStatement(groupQuery);
            psGroup.setString(1, groupId);
            ResultSet rsGroup = psGroup.executeQuery();

            out.println("<h2>Group Financial Report</h2>");
            if (rsGroup.next()) {
                out.println("<h3>Group Name: " + rsGroup.getString("GroupName") + "</h3>");
                out.println("<p>Group Leader: " + rsGroup.getString("GroupChairman") + "</p>");
            }

            // Fetch Bank Information
            String bankQuery = "SELECT * FROM bank WHERE BankID = ?";
            PreparedStatement psBank = conn.prepareStatement(bankQuery);
            psBank.setString(1, bankId);
            ResultSet rsBank = psBank.executeQuery();

            if (rsBank.next()) {
                out.println("<h3>Bank Details</h3>");
                out.println("<p>Bank Name: " + rsBank.getString("BankName") + "</p>");
                out.println("<p>Bank Address: " + rsBank.getString("BAddress") + "</p>");
            }

            // Fetch Bank Loan Information
            String bankLoanQuery = "SELECT * FROM bloan WHERE BankID = ? AND BankName='Bank Of Maharashtra'";
            PreparedStatement psBankLoan = conn.prepareStatement(bankLoanQuery);
            psBankLoan.setString(1, bankId);
            ResultSet rsBankLoan = psBankLoan.executeQuery();

            out.println("<h3>Bank Loan Information</h3>");
            while (rsBankLoan.next()) {
                out.println("<p>Loan Amount: " + rsBankLoan.getString("ApprovedBankLoan") + "</p>");
                out.println("<p>Date Of Loan Receipt: " + rsBankLoan.getString("DateOfLoanReceipt") + "%</p>");
            }

            // Fetch Group Loan and Installment Details using Nested Query
            String groupLoanQuery = "SELECT l.bloan, l.ApprovesBankLoan, l.DateOfLoanReceipt, r.installment_amount, r.payment_date "
                    + "FROM bloan l "
                    + "JOIN installment r ON l.bloanID = r.bloanID "
                    + "WHERE l.BankID = ? AND l.BankID='Bank Of Maharashtra'";

            PreparedStatement psGroupLoan = conn.prepareStatement(groupLoanQuery);
            psGroupLoan.setString(1, groupId);
            ResultSet rsGroupLoan = psGroupLoan.executeQuery();

            out.println("<h3>Group Loan & Installment Details</h3>");
            while (rsGroupLoan.next()) {
                out.println("<p>Loan Amount: " + rsGroupLoan.getString("ApprovedBankLoan") + "</p>");            
                out.println("<p>Date Of Loan Receipt: " + rsGroupLoan.getString("DateOfLoanReceipt") + "%</p>");
                out.println("<p>Installment Amount: " + rsGroupLoan.getDouble("installment_amount") + "</p>");
                out.println("<p>Payment Date: " + rsGroupLoan.getDate("payment_date") + "</p>");
            }

            conn.close();
        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
