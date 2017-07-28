package librarymanagementsystem;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement; 
import javax.swing.JOptionPane;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.*;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSetMetaData;

public class Report {

    public void insertReport(String month) {
        try {
            int row = 0;
            MonthVal mv = new MonthVal();
            String mval = mv.getMonthVal(month);
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/appdev","root","");
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT borrw_tble.borrowId,borrw_tble.stuId,borrw_tble.ISBN AS 'BookId',library.Title,borrw_tble.dateBrw,borrw_tble.dateRtrn,borrw_tble.Penalty FROM borrw_tble INNER JOIN library ON borrw_tble.ISBN=library.ISBN WHERE dateBrw LIKE '"+month+"%' and dateBrw LIKE '%2017' ");
            ResultSetMetaData rsmd = rs.getMetaData();
            int col = rsmd.getColumnCount();
            int count;
            while(rs.next()){
                row=row+1;
            }
            rs.first();
            
            Font title = new Font(Font.FontFamily.COURIER, 24, 5);
            Font sub = new Font(Font.FontFamily.COURIER,18,3);
            Font head = new Font(Font.FontFamily.COURIER,14,3);
            
            
            Document d = new Document();
            PdfWriter.getInstance(d, new FileOutputStream("C:\\Users\\justine\\Documents\\report.pdf"));
            
            Image img = Image.getInstance("C:\\Users\\justine\\Desktop\\School\\AppDEv\\AppDevProj\\tuplogo.gif");
            img.scaleAbsolute(100, 100);
            img.setAbsolutePosition(10, 720);
            
            Paragraph tup = new Paragraph("Technological University of the Philippines\n\n",head);
            tup.setAlignment(1);
            
            Paragraph p = new Paragraph("Library Management System\n\n",title);
            p.setAlignment(1);
            
            Paragraph p1 = new Paragraph("Report for the Month of "+mval+"\n\n",sub);
//            p.setFont(new Font(Font.FontFamily.COURIER,32));
            
            
            PdfPTable pt = new PdfPTable(col);
            pt.setTotalWidth(550);
            pt.setLockedWidth(true);
            pt.addCell("Borrower ID");
            pt.addCell("Student ID");
            pt.addCell("BookId");
            pt.addCell("Book Title");
            pt.addCell("Date Borrowed");
            pt.addCell("Date Return");
            pt.addCell("Penalty");
            
            for (count = 0; count < row; count++) {
                pt.addCell("" + rs.getString(1));
                pt.addCell("" + rs.getString(2));
                pt.addCell("" + rs.getString(3));
                pt.addCell("" + rs.getString(4));
                pt.addCell("" + rs.getString(5));
                pt.addCell("" + rs.getString(6));
                pt.addCell("" + rs.getString(7));
                rs.next();
            }
            
            d.open();
            d.setMargins(1, 1, 1, 1);
            d.add(img);
            d.add(tup);
            d.add(p);
            d.add(p1);
            d.add(pt);
            d.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unable to process another file, Please close the first one");
        }
    }
    
    public void openReport(){
	  try {

		if ((new File("C:\\Users\\justine\\Documents\\report.pdf")).exists()) {

			Process p = Runtime
			   .getRuntime()
			   .exec("rundll32 url.dll,FileProtocolHandler C:\\Users\\justine\\Documents\\report.pdf");
			p.waitFor();

		} else {

			JOptionPane.showMessageDialog(null,"File is not exists");

		}

		System.out.println("Done");

  	  } catch (Exception ex) {
		JOptionPane.showMessageDialog(null, "Unable to reopen another process while one is still pending, Please close the first one");
	  }
    }
//   public static void main(String arg[]){
//       Report rp = new Report();
//       rp.insertReport("03");
//       rp.openReport();
//   }
}