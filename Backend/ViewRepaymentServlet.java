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

@WebServlet("/ViewRepaymentServlet")
public class ViewRepaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String monthFilter = request.getParameter("month"); 
        String memberFilter = request.getParameter("member");

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM installment WHERE 1=1";
            if (monthFilter != null && !monthFilter.isEmpty()) {
                sql += " AND DATE_FORMAT(RepaymentDate, '%Y-%m') = ?";
            }
            if (memberFilter != null && !memberFilter.isEmpty()) {
                sql += " AND MemberName LIKE ?";
            }

            PreparedStatement ps = conn.prepareStatement(sql);
            int paramIndex = 1;
            if (monthFilter != null && !monthFilter.isEmpty()) {
                ps.setString(paramIndex++, monthFilter);
            }
            if (memberFilter != null && !memberFilter.isEmpty()) {
                ps.setString(paramIndex++, "%" + memberFilter + "%");
            }

            ResultSet rs = ps.executeQuery();

            out.println("<html><head><title>Loan Repayment Details</title>");
            out.println("<link rel='stylesheet' type='text/css' href='styles.css'>"); 
            out.println("<script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>");
            out.println("<link href='https://cdn.jsdelivr.net/npm/remixicon@4.5.0/fonts/remixicon.css' rel='stylesheet' />");
            out.println("</head><body>");
            
            out.println("<h1>Pragati Mahila Bachat Gat</h1>");
            out.println("<h3>Bank Of Maharashtra</h3>");
            
            out.println("<form method='GET' action='ViewRepaymentServlet'>");
            out.println("Search by Month: <input style='height:35px;' type='month' name='month' value='" + (monthFilter != null ? monthFilter : "") + "' placeholder='---- ---'>");
            out.println(" Search by Member Name: <input style='height:35px;' type='text' name='member' value='" + (memberFilter != null ? memberFilter : "") + "' placeholder='Enter Member Name'>");
            out.println("<button type='submit'>Search</button>");
            out.println("<a href='ViewRepaymentServlet'><button type='button'>Clear</button></a>");
            out.println("</form><br>");

            out.println("<br><h2>Loan Installment Details</h2>");
            out.println("<table border='1'>");
            out.println("<tr><th>Installment ID</th><th>Loan ID</th><th>Member Name</th><th>Installment Date</th><th>Bank Loan</th><th>Internal Loan</th><th>Internal Loan Interest</th><th>Saving</th><th>Actions</th></tr>");

            double totalBankLoan = 0, totalInternalLoan = 0, totalInternalInterest = 0, totalSaving = 0;
            boolean hasData = false;

            while (rs.next()) {
                hasData = true;
                int repaymentID = rs.getInt("RepaymentID");
                double bankAmount = rs.getDouble("BankAmount");
                double internalAmount = rs.getDouble("InternalAmount");
                double internalInterest = rs.getDouble("InternalInterest");
                double savingInstallment = rs.getDouble("SavingInstallment");

                totalBankLoan += bankAmount;
                totalInternalLoan += internalAmount;
                totalInternalInterest += internalInterest;
                totalSaving += savingInstallment;
            
                out.println("<tr>");
                out.println("<td>" + repaymentID + "</td>");
                out.println("<td>" + rs.getInt("LoanID") + "</td>");
                out.println("<td>" + rs.getString("MemberName") + "</td>");
                out.println("<td>" + rs.getString("RepaymentDate") + "</td>");
                out.println("<td>" + bankAmount + "</td>");
                out.println("<td>" + internalAmount + "</td>");
                out.println("<td>" + internalInterest + "</td>");
                out.println("<td>" + savingInstallment + "</td>");
                out.println("<td>");
                out.println("<a href='EditRepaymentServlet?repaymentID=" + repaymentID + "'><i class=\"ri-edit-2-line\"></i></a>");
                out.println("&nbsp;&nbsp;");
                out.println("<a href='DeleteRepaymentServlet?repaymentID=" + repaymentID + "' onclick='return confirm(\"Are you sure?\")'><i class=\"ri-delete-bin-6-line\"></i></a>");
               out.println("</td>");
                out.println("</tr>");
            }
            
          //  totalSaving = (totalSaving + totalInternalInterest) - totalInternalLoan;
            totalSaving = (totalSaving + totalInternalInterest);
              
            if (hasData) {
                out.println("<tr style='font-weight:bold; background-color:#f2f2f2;'>");
                out.println("<td colspan='4' align='right'>Total:</td>");
                out.println("<td>" + totalBankLoan + "</td>");
                out.println("<td>" + totalInternalLoan + "</td>");
                out.println("<td>" + totalInternalInterest + "</td>");
                out.println("<td>" + totalSaving + "</td>");
                out.println("<td></td>");
                out.println("</tr>");
            } else {
                out.println("<tr><td colspan='9' align='center'>No data found for this search.</td></tr>");
            }

            out.println("</table>");
            out.println("<br><a href='home.html'>Back to Home</a>");
            out.println("</body></html>");

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
        }
    }
}






//import java.io.IOException;
//import java.io.PrintWriter;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@WebServlet("/ViewRepaymentServlet")
//public class ViewRepaymentServlet extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
//            throws ServletException, IOException {
//        response.setContentType("text/html");
//        PrintWriter out = response.getWriter();
//        
//        String monthFilter = request.getParameter("month"); 
//
//        try (Connection conn = DBConnection.getConnection()) {
//            String sql = "SELECT * FROM installment";
//            if (monthFilter != null && !monthFilter.isEmpty()) {
//                sql += " WHERE DATE_FORMAT(RepaymentDate, '%Y-%m') = ?";
//            }
//
//            PreparedStatement ps = conn.prepareStatement(sql);
//            if (monthFilter != null && !monthFilter.isEmpty()) {
//                ps.setString(1, monthFilter);
//            }
//
//            ResultSet rs = ps.executeQuery();
//
//            out.println("<html><head><title>Loan Repayment Details</title>");
//            out.println("<link rel='stylesheet' type='text/css' href='styles.css'>"); 
//            out.println("<script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>");
//            out.println("<link href='https://cdn.jsdelivr.net/npm/remixicon@4.5.0/fonts/remixicon.css' rel='stylesheet' />");
//            out.println("</head><body>");
//            
//            out.println("<h1>Pragati Mahila Bachat Gat</h1>");
//            out.println("<h3>Bank Of Maharashtra</h3>");
//            
//            out.println("<form method='GET'action='ViewRepaymentServlet'>");
//            out.println("Search by Month: <input style='height:35px;' type='month' name='month' value='" + (monthFilter != null ? monthFilter : "") + "' placeholder='---- ---'>");
//            out.println("<button type='submit'>Search</button>");
//            out.println("<a href='ViewRepaymentServlet'><button type='button'>Clear</button></a>");
//            out.println("</form><br>");
//
//            out.println("<br><h2>Loan Installment Details</h2>");
//            out.println("<table border='1'>");
//            out.println("<tr><th>Installment ID</th><th>Loan ID</th><th>Member Name</th><th>Installment Date</th><th>Bank Loan</th><th>Internal Loan</th><th>Internal Loan Interest</th><th>Saving</th><th>Actions</th></tr>");
//
//            double totalBankLoan = 0, totalInternalLoan = 0, totalInternalInterest = 0, totalSaving = 0;
//            boolean hasData = false;
//
//            while (rs.next()) {
//                hasData = true;
//                int repaymentID = rs.getInt("RepaymentID");
//                double bankAmount = rs.getDouble("BankAmount");
//                double internalAmount = rs.getDouble("InternalAmount");
//                double internalInterest = rs.getDouble("InternalInterest");
//                double savingInstallment = rs.getDouble("SavingInstallment");
//
//                totalBankLoan += bankAmount;
//                totalInternalLoan += internalAmount;
//                totalInternalInterest += internalInterest;
//                totalSaving += savingInstallment;
//            
//                out.println("<tr>");
//                out.println("<td>" + repaymentID + "</td>");
//                out.println("<td>" + rs.getInt("LoanID") + "</td>");
//                out.println("<td>" + rs.getString("MemberName") + "</td>");
//                out.println("<td>" + rs.getString("RepaymentDate") + "</td>");
//                out.println("<td>" + bankAmount + "</td>");
//                out.println("<td>" + internalAmount + "</td>");
//                out.println("<td>" + internalInterest + "</td>");
//                out.println("<td>" + savingInstallment + "</td>");
//                out.println("<td>");
//                out.println("<a href='EditRepaymentServlet?repaymentID=" + repaymentID + "'><i class=\"ri-edit-2-line\"></i></a>");
//                out.println("&nbsp;&nbsp;");
//                out.println("<a href='DeleteRepaymentServlet?repaymentID=" + repaymentID + "' onclick='return confirm(\"Are you sure?\")'><i class=\"ri-delete-bin-6-line\"></i></a>");
//               out.println("</td>");
//                out.println("</tr>");
//                
//            }
//            
//            // Internal Loan ko Total Saving me se Subtract karna
//            totalSaving = (totalSaving + totalInternalInterest) - totalInternalLoan;
//
//            if (hasData) {
//                out.println("<tr style='font-weight:bold; background-color:#f2f2f2;'>");
//                out.println("<td colspan='4' align='right'>Total:</td>");
//                out.println("<td>" + totalBankLoan + "</td>");
//                out.println("<td>" + totalInternalLoan + "</td>");
//                out.println("<td>" + totalInternalInterest + "</td>");
//                out.println("<td>" + totalSaving + "</td>");
//                out.println("<td></td>");
//                out.println("</tr>");
//            } else {
//                out.println("<tr><td colspan='9' align='center'>No data found for this month.</td></tr>");
//            }
//
//            out.println("</table>");
//            out.println("<br><a href='home.html'>Back to Home</a>");
//            out.println("</body></html>");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            out.println("<h3>Error: " + e.getMessage() + "</h3>");
//        }
//    }
//}
//
//
//
//
//
