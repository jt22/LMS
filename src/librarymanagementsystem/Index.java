/*
 * To change this license header, cho    @Override
    public int tabForCoordinate(JTabbedPane pane, int x, int y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Rectangle getTabBounds(JTabbedPane pane, int index) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getTabRunCount(JTabbedPane pane) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
ose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

import com.sun.java.swing.plaf.windows.WindowsTabbedPaneUI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import javax.swing.plaf.TabbedPaneUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.plaf.synth.SynthTabbedPaneUI;
import net.proteanit.sql.DbUtils;
/**
 *
 * @author justine
 */
public class Index extends javax.swing.JFrame {
String isbnBrw, isbnRtrn,studentId,brrwDate,rtrnDate,studRtrn,brwID,mval;
    /**
     * Creates new form Index
     */
    public Index() {
        initComponents();
        alltble();
        id();
        isbnNotice.setVisible(false);
        
    }
    
    public void alltble(){
        tbleMnagebooks();
        tblesearch();
        tblebrw();
        tblertrn();
        reportble();
    }
    
    public void isbnForm(){
            if(tISBN.getText().length()<10){
                isbnNotice.setVisible(true);
            }
            if(tISBN.getText().length()==0){
                isbnNotice.setVisible(false);
            }
            if(tISBN.getText().length()==10){
                isbnNotice.setVisible(false);
            }
            if(tISBN.getText().length()>10){
                isbnNotice.setVisible(true);
            }
            
    }
    public void id(){
        try {
            // Interacting Database
            Class.forName("com.mysql.jdbc.Driver");
            Connection conDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/appdev", "root", "");
            Statement statement = conDB.createStatement();
            ResultSet rs = statement.executeQuery("SELECT `auto_increment` FROM INFORMATION_SCHEMA.TABLES WHERE table_name = 'library' and table_schema = 'appdev'");
            rs.first();
            isbnadd.setText(rs.getString(1));
            
        }   
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    

    public void crudrowtable(){
        int row = jTable2.getSelectedRow();
        String isbnVal = jTable2.getModel().getValueAt(row, 0).toString();
        String titleVal = jTable2.getModel().getValueAt(row, 1).toString();
        String authorVal = jTable2.getModel().getValueAt(row, 2).toString();
        String pubVal = jTable2.getModel().getValueAt(row, 3).toString();
        String categVal = jTable2.getModel().getValueAt(row, 4).toString();
        String tISBNVal = jTable2.getModel().getValueAt(row, 5).toString();
        isbnadd.setText(isbnVal);
        titleadd.setText(titleVal);
        authoradd.setText(authorVal);
        pubadd.setText(pubVal);
        categoryCmbo.setSelectedItem(categVal);
        tISBN.setText(tISBNVal);
    }
    public void tblesearch(){
        try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/appdev","root","");
                Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery("Select ISBN AS 'BookId',Title,Author,Publisher,Category,ISBN1 AS 'ISBN',Status  from library where "+srchcombo.getSelectedItem()+" like '%"+isbnsrch.getText()+"%' ");
                jTable1.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    public void tbleMnagebooks(){
        try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/appdev","root","");
                Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery("Select ISBN AS 'BookId',Title,Author,Publisher,Category,ISBN1 AS 'ISBN',Status  from library where "+srchcombo.getSelectedItem()+" like '%"+isbnsrch.getText()+"%' ");
                jTable2.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void tblebrw(){

        try {

                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/appdev","root","");
                Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery("Select ISBN AS 'BookId',Title,Author,Publisher,Category,ISBN1 AS 'ISBN',Status from library where "+brwcmbo.getSelectedItem()+" like '%"+brwtxt.getText()+"%' and Status = 'Available' ");
                jTable3.setModel(DbUtils.resultSetToTableModel(rs));
                
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
//        }s
    }
    
    public void tblertrn(){

        try {
                String selecteditem = rtrncmbo.getSelectedItem().toString();
                if(selecteditem.equals("Borrow ID")){
                    selecteditem = "borrowId";
                }
                if(selecteditem.equals("Student ID")){
                    selecteditem = "studId";
                }
                if(selecteditem.equals("Date Borrowed")){
                    selecteditem = "dateBrw";
                }
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/appdev","root","");
                Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery("SELECT borrw_tble.borrowId AS 'Borrow ID',borrw_tble.stuId AS 'Student ID',borrw_tble.ISBN AS 'BookId',library.Title,borrw_tble.dateBrw AS 'Date Borrowed' FROM borrw_tble INNER JOIN library ON borrw_tble.ISBN=library.ISBN WHERE "+selecteditem+" LIKE '%"+rtrntxt.getText()+"%' AND dateRtrn IS NULL");
                jTable4.setModel(DbUtils.resultSetToTableModel(rs));                
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
//        }
    }
    
    
    public void reportble(){
        
        try {
                String reportSel = reportDate.getSelectedItem().toString();
                MonthVal mv = new MonthVal();
                mval = mv.getReportMonth(reportSel);
//                getReportMonth(reportSel);
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/appdev","root","");
                Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery("SELECT borrw_tble.borrowId AS 'Borrow ID',borrw_tble.stuId AS 'Student ID',borrw_tble.ISBN AS 'BookId',library.Title,borrw_tble.dateBrw AS 'Date Borrowed',borrw_tble.dateRtrn AS 'Date Returned', borrw_tble.Penalty FROM borrw_tble INNER JOIN library ON borrw_tble.ISBN=library.ISBN WHERE dateBrw LIKE '"+mval+"%' ");
                jTable5.setModel(DbUtils.resultSetToTableModel(rs));                
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    public boolean checkID(String student){
         try {
            // Interacting Database
            Class.forName("com.mysql.jdbc.Driver");
            Connection conDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/appdev", "root", "");
            Statement state = conDB.createStatement();
            ResultSet getData = state.executeQuery( "SELEcT * FROM borrower WHERE studId = '" + student + "'");

            String getUsername = "";

            if ( getData.next() ){
                getUsername = getData.getString("studId");
            }
            if ( student.equals(getUsername)) {
                return true;
            }
            else {
                JOptionPane.showMessageDialog( null, "Invalid Student ID", "Error", JOptionPane.ERROR_MESSAGE );
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    return false;
    }
    
    public int penaltyfun(){
    try {
        int penalty = 0,x;
        SimpleDateFormat df = new SimpleDateFormat("MM dd yyyy");
        Date date1 = new Date();
        String dateStr = df.format(date1);
        Date dateBrw = df.parse(brrwDate);
        Date dateRtrn = df.parse(dateStr);
        long diff = (dateRtrn.getTime()-dateBrw.getTime());
        int penDays = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        if (penDays>=2){
            for(x=2;x<=penDays;x++){
                penalty += 10;
            }
        }
        return penalty;
    } catch (ParseException ex) {
        Logger.getLogger(Index.class.getName()).log(Level.SEVERE, null, ex);
    }
    catch (NullPointerException e){
        JOptionPane.showMessageDialog(null, "No Data Selected");
    }
    return 0;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        entityManager = java.beans.Beans.isDesignTime() ? null : javax.persistence.Persistence.createEntityManagerFactory("appdev?zeroDateTimeBehavior=convertToNullPU").createEntityManager();
        libraryQuery = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT l FROM Library l");
        libraryList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : libraryQuery.getResultList();
        libraryQuery1 = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT l FROM Library l");
        libraryList1 = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : libraryQuery1.getResultList();
        libraryQuery2 = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT l FROM Library l");
        libraryList2 = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : libraryQuery2.getResultList();
        jTabbedPane6 = new javax.swing.JTabbedPane();
        jTabbedPane6.setUI(new WindowsTabbedPaneUI());
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        isbnsrch = new javax.swing.JTextField();
        srchcombo = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        brwtxt = new javax.swing.JTextField();
        borrwbut = new javax.swing.JButton();
        brwcmbo = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        studIdTxt = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        rtrntxt = new javax.swing.JTextField();
        rtrnbut = new javax.swing.JButton();
        rtrncmbo = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        Add = new javax.swing.JButton();
        isbnadd = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        titleadd = new javax.swing.JTextField();
        authoradd = new javax.swing.JTextField();
        pubadd = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        Delete = new javax.swing.JButton();
        Edit = new javax.swing.JButton();
        mngSrchTxt = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        mngCmbo = new javax.swing.JComboBox<>();
        mngClear = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        tISBN = new javax.swing.JTextField();
        categoryCmbo = new javax.swing.JComboBox<>();
        isbnNotice = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        reportDate = new javax.swing.JComboBox<>();
        getReportBut = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        logout = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(300, 100));
        setPreferredSize(new java.awt.Dimension(680, 520));
        getContentPane().setLayout(null);

        jTabbedPane6.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTabbedPane6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane6MouseClicked(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setText("Search By:");

        isbnsrch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                isbnsrchKeyReleased(evt);
            }
        });

        srchcombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ISBN", "Title", "Author", "Publisher", "Category" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(srchcombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(isbnsrch, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(srchcombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(isbnsrch, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(147, Short.MAX_VALUE))
        );

        jTabbedPane6.addTab("Home", new javax.swing.ImageIcon(getClass().getResource("/librarymanagementsystem/rsz_home1.png")), jPanel2); // NOI18N

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable3);

        jLabel2.setText("Search by:");

        brwtxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                brwtxtKeyReleased(evt);
            }
        });

        borrwbut.setText("Borrow");
        borrwbut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrwbutActionPerformed(evt);
            }
        });

        brwcmbo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ISBN", "Title", "Author", "Publisher", "Category" }));

        jLabel10.setText("Student ID");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(brwcmbo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(brwtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(studIdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(borrwbut)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(brwtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(brwcmbo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(studIdTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(borrwbut))
                .addGap(60, 60, 60))
        );

        jTabbedPane6.addTab("Borrow Books", new javax.swing.ImageIcon(getClass().getResource("/librarymanagementsystem/rsz_borrow1.png")), jPanel3); // NOI18N

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable4MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTable4);

        jLabel3.setText("Search by:");

        rtrntxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rtrntxtActionPerformed(evt);
            }
        });
        rtrntxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                rtrntxtKeyReleased(evt);
            }
        });

        rtrnbut.setText("Return");
        rtrnbut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rtrnbutActionPerformed(evt);
            }
        });

        rtrncmbo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Borrow ID", "Student ID", "ISBN", "Date Borrowed", "Category" }));
        rtrncmbo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rtrncmboActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(rtrnbut))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(rtrncmbo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(rtrntxt, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rtrntxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(rtrncmbo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(rtrnbut)
                .addContainerGap(63, Short.MAX_VALUE))
        );

        jTabbedPane6.addTab("Return Books", new javax.swing.ImageIcon(getClass().getResource("/librarymanagementsystem/rsz_return1.png")), jPanel4); // NOI18N

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setText("Book Id");

        Add.setText("Add");
        Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddActionPerformed(evt);
            }
        });

        isbnadd.setEnabled(false);

        jLabel6.setText("Title");

        jLabel7.setText("Author");

        jLabel8.setText("Publisher");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        Delete.setText("Delete");
        Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteActionPerformed(evt);
            }
        });

        Edit.setText("Edit");
        Edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditActionPerformed(evt);
            }
        });

        mngSrchTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mngSrchTxtActionPerformed(evt);
            }
        });
        mngSrchTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                mngSrchTxtKeyReleased(evt);
            }
        });

        jLabel11.setText("Search By:");

        mngCmbo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ISBN", "Title", "Author", "Publisher", "Category" }));

        mngClear.setText("Clear");
        mngClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mngClearActionPerformed(evt);
            }
        });

        jLabel12.setText("Category");

        jLabel14.setText("ISBN");

        tISBN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tISBNActionPerformed(evt);
            }
        });
        tISBN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tISBNKeyReleased(evt);
            }
        });

        categoryCmbo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Math", "English", "Arts", "Science", "Computer", "Engineering", "Education", "Accounting" }));

        isbnNotice.setForeground(new java.awt.Color(255, 51, 51));
        isbnNotice.setText("Must be in 10 digit");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
                                .addGap(25, 25, 25)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(authoradd, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(isbnadd, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(titleadd, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(56, 56, 56)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel14)
                                            .addComponent(jLabel12)))))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGap(24, 24, 24)
                                        .addComponent(Add))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(10, 10, 10)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(Edit)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(Delete)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(mngClear))
                                    .addComponent(pubadd, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(mngCmbo, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(mngSrchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(tISBN, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(isbnNotice, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(categoryCmbo, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(isbnadd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(categoryCmbo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(titleadd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(tISBN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(isbnNotice))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(authoradd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pubadd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Add)
                    .addComponent(Edit)
                    .addComponent(Delete)
                    .addComponent(mngSrchTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(mngCmbo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mngClear))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        jTabbedPane6.addTab("Manage Books", new javax.swing.ImageIcon(getClass().getResource("/librarymanagementsystem/rsz_manage1.png")), jPanel5); // NOI18N

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane5.setViewportView(jTable5);

        jLabel13.setText("Month");

        reportDate.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));
        reportDate.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                reportDateItemStateChanged(evt);
            }
        });

        getReportBut.setText("Get Report");
        getReportBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getReportButActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(reportDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(getReportBut)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(reportDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(getReportBut)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jTabbedPane6.addTab("Report", new javax.swing.ImageIcon(getClass().getResource("/librarymanagementsystem/rsz_yhxzkn-reported.png")), jPanel1); // NOI18N

        getContentPane().add(jTabbedPane6);
        jTabbedPane6.setBounds(40, 30, 585, 425);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setText("Library Management System");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(40, 0, 350, 30);

        logout.setText("Logout");
        logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutActionPerformed(evt);
            }
        });
        getContentPane().add(logout);
        logout.setBounds(550, 10, 73, 23);

        jLabel4.setIcon(new javax.swing.ImageIcon("C:\\Users\\justine\\Desktop\\School\\AppDEv\\AppDevProj\\a-book-a-week-image.jpg")); // NOI18N
        getContentPane().add(jLabel4);
        jLabel4.setBounds(0, 0, 680, 480);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddActionPerformed
        try {
            // Interacting Database
            Class.forName("com.mysql.jdbc.Driver");
            Connection conDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/appdev", "root", "");
            String defval = "Available";
            int isbn = Integer.parseInt(tISBN.getText());
            PreparedStatement statement = conDB.prepareStatement("Insert into library (ISBN,Title,Author,Publisher,Category,ISBN1,Status) values ('"+isbnadd.getText()+"','"+titleadd.getText()+"','"+authoradd.getText()+"','"+pubadd.getText()+"','"+categoryCmbo.getSelectedItem().toString()+"','"+tISBN.getText()+"',"+defval+"')" );
            if(tISBN.getText().length()==10){
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(null, "Successfully Saved"); 
                       alltble();
                }
                Integer.parseInt(tISBN.getText());
                titleadd.setText("");
                authoradd.setText("");
                pubadd.setText("");
                categoryCmbo.setSelectedItem(ABORT);
                tISBN.setText("");
                id();
            }
            else{
                JOptionPane.showMessageDialog(null, "Improper ISBN length");
            }
            
        }   
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        } catch (ClassNotFoundException ex) {
        Logger.getLogger(Index.class.getName()).log(Level.SEVERE, null, ex);
        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "ISBN must be numbers");
        }
        
    }//GEN-LAST:event_AddActionPerformed

    private void EditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditActionPerformed
        try {
            // Interacting Database
            Class.forName("com.mysql.jdbc.Driver");
            Connection conDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/appdev", "root", "");
            int isbn = Integer.parseInt(tISBN.getText());
            
            PreparedStatement statement = conDB.prepareStatement("Update library set Title = '"+titleadd.getText()+"',Author = '"+authoradd.getText()+"',Publisher = '"+pubadd.getText()+"', Category = '"+categoryCmbo.getSelectedItem().toString()+"', ISBN1 = '"+tISBN.getText()+"' where ISBN = '"+isbnadd.getText()+"'" );
            if(tISBN.getText().length()==10){
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(null, "Successfully Edited");
                        alltble();
                }
                id();
                titleadd.setText("");
                authoradd.setText("");
                pubadd.setText("");
                categoryCmbo.setSelectedItem(ABORT);
                tISBN.setText("");
            }
            else{
                JOptionPane.showMessageDialog(null, "Improper ISBN length");
            }
        }   
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        } catch (ClassNotFoundException ex) {
        Logger.getLogger(Index.class.getName()).log(Level.SEVERE, null, ex);
        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "ISBN must be numbers");
        }
        

    }//GEN-LAST:event_EditActionPerformed

    private void DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteActionPerformed
         try {
            // Interacting Database
            Class.forName("com.mysql.jdbc.Driver");
            Connection conDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/appdev", "root", "");
 
            PreparedStatement statement = conDB.prepareStatement("Delete from library where ISBN = '"+isbnadd.getText()+"'" );
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(null, "Successfully Deleted");
                    alltble();
            }
            id();
            titleadd.setText("");
            authoradd.setText("");
            pubadd.setText("");
            categoryCmbo.setSelectedItem(ABORT);
            tISBN.setText("");
        }   
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_DeleteActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        crudrowtable();
    }//GEN-LAST:event_jTable2MouseClicked

    private void rtrnbutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rtrnbutActionPerformed
        
        int pen = penaltyfun();
        if(pen>0){
            int confirm = JOptionPane.showConfirmDialog(null,"Does the "+pen+" penalty paid?","Penalty",JOptionPane.YES_NO_OPTION);
            if(confirm == JOptionPane.YES_OPTION){
                JOptionPane.showMessageDialog(null, "Penalty has paid");
                try {
                    // Interacting Database
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection conDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/appdev", "root", "");
                    SimpleDateFormat df = new SimpleDateFormat("MM dd yyyy");
                    Date now = new Date();
                    String dateRtrn = df.format(now);
                    PreparedStatement statement = conDB.prepareStatement("Update borrw_tble set dateRtrn = '"+dateRtrn+"', Penalty = '"+pen+"' where borrowID = '"+brwID+"'" );
                    PreparedStatement statement1 = conDB.prepareStatement("Update library set Status = 'Available' where ISBN = '"+isbnRtrn+"'" );
                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(null, "Successfully Returned");
                        alltble();
                        int rowsInserted1 = statement1.executeUpdate();
                        if (rowsInserted1 > 0) {
                            alltble();
                        }
                    }

                }   
                catch(Exception e){
                    JOptionPane.showMessageDialog(null, e);
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "You need to pay the penalty");
            }
        }
        else{
                try {
                // Interacting Database
                Class.forName("com.mysql.jdbc.Driver");
                Connection conDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/appdev", "root", "");
                SimpleDateFormat df = new SimpleDateFormat("MM dd yyyy");
                Date now = new Date();
                String dateRtrn = df.format(now);
                PreparedStatement statement = conDB.prepareStatement("Update borrw_tble set dateRtrn = '"+dateRtrn+"' where borrowID = '"+brwID+"'" );
                PreparedStatement statement1 = conDB.prepareStatement("Update library set Status = 'Available' where ISBN = '"+isbnRtrn+"'" );
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(null, "Successfully Returned");
                    alltble();
                    int rowsInserted1 = statement1.executeUpdate();
                    if (rowsInserted1 > 0) {
                        alltble();
                    }
                }

            }   
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }//GEN-LAST:event_rtrnbutActionPerformed

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        int row = jTable3.getSelectedRow();
        isbnBrw = jTable3.getModel().getValueAt(row, 0).toString();
        
    }//GEN-LAST:event_jTable3MouseClicked

    private void borrwbutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrwbutActionPerformed
       if(studIdTxt.getText().equals("") || isbnBrw.equals("")){
            JOptionPane.showMessageDialog(null, "Please select a book and enter Student Id");
        }
        else{
           if(checkID(studIdTxt.getText())==true)
           {
           try {
                // Interacting Database
                Class.forName("com.mysql.jdbc.Driver");
                Connection conDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/appdev", "root", "");
                Date today = new Date();
                SimpleDateFormat df = new SimpleDateFormat("MM dd yyyy");
                String now = df.format(today);
                PreparedStatement statement = conDB.prepareStatement("Insert into borrw_tble (stuId,ISBN,dateBrw) values ('"+studIdTxt.getText()+"','"+isbnBrw+"','"+now+"')" );
                PreparedStatement statement1 = conDB.prepareStatement("Update library set Status = 'Borrowed' where ISBN = '"+isbnBrw+"'" );
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(null, "Successfully Borrowed"); 
                       alltble();
                       int rowsInserted1 = statement1.executeUpdate();
                         if (rowsInserted1 > 0) {
                        alltble();
                }
                }
                studIdTxt.setText("");
                
            }   
            catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
           }
//            try {
//                // Interacting Database
//                Class.forName("com.mysql.jdbc.Driver");
//                Connection conDB = DriverManager.getConnection("jdbc:mysql://localhost:3306/appdev", "root", "");
//                
//                
//            }   
//            catch(Exception e){
//                JOptionPane.showMessageDialog(null, e);
//            }
             
        }
    }//GEN-LAST:event_borrwbutActionPerformed

    private void jTable4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable4MouseClicked
        int row = jTable4.getSelectedRow();
        brwID = jTable4.getModel().getValueAt(row, 0).toString();
        isbnRtrn = jTable4.getModel().getValueAt(row, 2).toString();
        brrwDate = jTable4.getModel().getValueAt(row, 4).toString();
//        rtrnDate = jTable4.getModel().getValueAt(row, 4).toString();
        
    }//GEN-LAST:event_jTable4MouseClicked

    private void brwtxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_brwtxtKeyReleased
        tblebrw();
    }//GEN-LAST:event_brwtxtKeyReleased

    private void rtrntxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rtrntxtKeyReleased
        tblertrn();
    }//GEN-LAST:event_rtrntxtKeyReleased

    private void isbnsrchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_isbnsrchKeyReleased
        tblesearch();
    }//GEN-LAST:event_isbnsrchKeyReleased

    private void mngSrchTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mngSrchTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mngSrchTxtActionPerformed

    private void mngSrchTxtKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mngSrchTxtKeyReleased
        tbleMnagebooks();
    }//GEN-LAST:event_mngSrchTxtKeyReleased

    private void mngClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mngClearActionPerformed
        id();
        titleadd.setText("");
        authoradd.setText("");
        pubadd.setText("");
        categoryCmbo.setSelectedItem(ABORT);
    }//GEN-LAST:event_mngClearActionPerformed

    private void jTabbedPane6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane6MouseClicked
       
    }//GEN-LAST:event_jTabbedPane6MouseClicked

    private void reportDateItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_reportDateItemStateChanged
        reportble();
    }//GEN-LAST:event_reportDateItemStateChanged

    private void getReportButActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getReportButActionPerformed
        Report rp = new Report();
        rp.insertReport(mval);
        rp.openReport();
    }//GEN-LAST:event_getReportButActionPerformed

    private void logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutActionPerformed
        login page = new login();
        page.setVisible(true);
        setVisible(false);
    }//GEN-LAST:event_logoutActionPerformed

    private void tISBNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tISBNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tISBNActionPerformed

    private void tISBNKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tISBNKeyReleased
        isbnForm();
    }//GEN-LAST:event_tISBNKeyReleased

    private void rtrntxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rtrntxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rtrntxtActionPerformed

    private void rtrncmboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rtrncmboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rtrncmboActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Index.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Index.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Index.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Index.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Index().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Add;
    private javax.swing.JButton Delete;
    private javax.swing.JButton Edit;
    private javax.swing.JTextField authoradd;
    private javax.swing.JButton borrwbut;
    private javax.swing.JComboBox<String> brwcmbo;
    private javax.swing.JTextField brwtxt;
    private javax.swing.JComboBox<String> categoryCmbo;
    private javax.persistence.EntityManager entityManager;
    private javax.swing.JButton getReportBut;
    private javax.swing.JLabel isbnNotice;
    private javax.swing.JTextField isbnadd;
    private javax.swing.JTextField isbnsrch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane6;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private java.util.List<librarymanagementsystem.Library> libraryList;
    private java.util.List<librarymanagementsystem.Library> libraryList1;
    private java.util.List<librarymanagementsystem.Library> libraryList2;
    private javax.persistence.Query libraryQuery;
    private javax.persistence.Query libraryQuery1;
    private javax.persistence.Query libraryQuery2;
    private javax.swing.JButton logout;
    private javax.swing.JButton mngClear;
    private javax.swing.JComboBox<String> mngCmbo;
    private javax.swing.JTextField mngSrchTxt;
    private javax.swing.JTextField pubadd;
    private javax.swing.JComboBox<String> reportDate;
    private javax.swing.JButton rtrnbut;
    private javax.swing.JComboBox<String> rtrncmbo;
    private javax.swing.JTextField rtrntxt;
    private javax.swing.JComboBox<String> srchcombo;
    private javax.swing.JTextField studIdTxt;
    private javax.swing.JTextField tISBN;
    private javax.swing.JTextField titleadd;
    // End of variables declaration//GEN-END:variables
}
