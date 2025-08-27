import java.io.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.itextpdf.html2pdf.HtmlConverter;


@WebServlet("/DownloadLoanPDF")
public class DownloadLoanPDF extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=output.pdf");
        
        try {
            // HTML file ka path jo aapko convert karna hai
            String inputHtmlPath = getServletContext().getRealPath("/webpage.html");
            String outputPdfPath = getServletContext().getRealPath("/output.pdf");

            // HTML to PDF conversion
            HtmlConverter.convertToPdf(new FileInputStream(inputHtmlPath), new FileOutputStream(outputPdfPath));
            
            // PDF ko response me write karna
            File pdfFile = new File(outputPdfPath);
            FileInputStream fis = new FileInputStream(pdfFile);
            OutputStream os = response.getOutputStream();
            
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            
            fis.close();
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}




//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Element;
//import com.itextpdf.text.Font;
//import com.itextpdf.text.FontFactory;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.Phrase;
//import com.itextpdf.text.pdf.PdfPCell;
//import com.itextpdf.text.pdf.PdfPTable;
//import com.itextpdf.text.pdf.PdfWriter;
//
//@WebServlet("/DownloadLoanPDF")
//public class DownloadLoanPDF extends HttpServlet {
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.setContentType("application/pdf");
//        response.setHeader("Content-Disposition", "attachment; filename=\"LoanDetails.pdf\"");
//
//        System.out.println("PDF Generation Started...");
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        Document document = new Document();
//
//        try (Connection conn = DBConnection.getConnection()) {
//            PdfWriter writer = PdfWriter.getInstance(document, baos);
//            document.open();
//            
//            System.out.println("Document Opened Successfully...");
//
//            // Title
//            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD);
//            Paragraph title = new Paragraph("Loan Details Report", titleFont);
//            title.setAlignment(Element.ALIGN_CENTER);
//            document.add(title);
//            document.add(new Paragraph("\n"));
//
//            // Table
//            PdfPTable table = new PdfPTable(6);
//            table.setWidthPercentage(100);
//
//            // Table Header
//            String[] headers = {"Loan ID", "Member Name", "Bank Loan", "Bank Loan Date", "Internal Loan", "Internal Loan Interest"};
//            for (String header : headers) {
//                PdfPCell cell = new PdfPCell(new Phrase(header));
//                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                table.addCell(cell);
//            }
//
//            // Fetch Data from Database
//            String query = "SELECT * FROM loan";
//            PreparedStatement ps = conn.prepareStatement(query);
//            ResultSet rs = ps.executeQuery();
//
//            boolean hasData = false;
//
//            while (rs.next()) {
//                hasData = true;
//                System.out.println("Fetching Data: LoanID = " + rs.getInt("LoanID"));
//                
//                table.addCell(String.valueOf(rs.getInt("LoanID")));
//                table.addCell(rs.getString("MemberName"));
//                table.addCell(String.valueOf(rs.getDouble("BankLoan")));
//                table.addCell(rs.getString("BankLoanDate"));
//                table.addCell(String.valueOf(rs.getDouble("InternalLoan")));
//               // table.addCell(String.valueOf(rs.getDouble("InternalLInterest")));
//                
//                String interestRateStr = rs.getString("InternalLInterest"); // Assuming the column name
//                double interestRate = 0.0;
//                if (interestRateStr != null) {
//                    interestRateStr = interestRateStr.replace("%", "").trim(); // Remove percentage sign
//                    interestRate = Double.parseDouble(interestRateStr); // Convert to double
//                }
//            }
//
//            if (!hasData) {
//                System.out.println("No data found in database!");
//                PdfPCell emptyCell = new PdfPCell(new Phrase("No Loan Details Found"));
//                emptyCell.setColspan(6);
//                emptyCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//                table.addCell(emptyCell);
//            }
//
//            document.add(table);
//            document.close();
//            writer.close();
//
//            System.out.println("Document Created Successfully!");
//
//            byte[] pdfBytes = baos.toByteArray();
//            System.out.println("PDF Size: " + pdfBytes.length + " bytes");
//
//            if (pdfBytes.length == 0) {
//                System.out.println("⚠️ ERROR: Generated PDF size is 0 bytes!");
//            }
//
//            response.setContentLength(pdfBytes.length);
//            OutputStream out = response.getOutputStream();
//            out.write(pdfBytes);
//            out.flush();
//            out.close();
//
//        } catch (DocumentException e) {
//            System.out.println("DocumentException: " + e.getMessage());
//            throw new ServletException("Error generating PDF", e);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
