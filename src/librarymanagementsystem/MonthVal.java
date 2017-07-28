/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

/**
 *
 * @author justine
 */
public class MonthVal {
    public String getReportMonth(String repMonth){
        String monthReportVal = null;
        if(repMonth.equals("January")){
            monthReportVal = "01";
        }
        if(repMonth.equals("February")){
            monthReportVal = "02";
        }
        if(repMonth.equals("March")){
            monthReportVal = "03";
        }
        if(repMonth.equals("April")){
            monthReportVal = "04";
        }
        if(repMonth.equals("May")){
            monthReportVal = "05";
        }
        if(repMonth.equals("June")){
            monthReportVal = "06";
        }
        if(repMonth.equals("July")){
            monthReportVal = "07";
        }
        if(repMonth.equals("August")){
            monthReportVal = "08";
        }
        if(repMonth.equals("September")){
            monthReportVal = "09";
        }
        if(repMonth.equals("October")){
            monthReportVal = "10";
        }
        if(repMonth.equals("November")){
            monthReportVal = "11";
        }
        if(repMonth.equals("December")){
            monthReportVal = "12";
        }
        return(monthReportVal);
    }
    public String getMonthVal(String repMonth){
        String monthReportVal = null;
        if(repMonth.equals("01")){
            monthReportVal = "January";
        }
        if(repMonth.equals("02")){
            monthReportVal = "February";
        }
        if(repMonth.equals("03")){
            monthReportVal = "March";
        }
        if(repMonth.equals("04")){
            monthReportVal = "April";
        }
        if(repMonth.equals("05")){
            monthReportVal = "May";
        }
        if(repMonth.equals("06")){
            monthReportVal = "June";
        }
        if(repMonth.equals("07")){
            monthReportVal = "July";
        }
        if(repMonth.equals("08")){
            monthReportVal = "August";
        }
        if(repMonth.equals("09")){
            monthReportVal = "September";
        }
        if(repMonth.equals("10")){
            monthReportVal = "October";
        }
        if(repMonth.equals("11")){
            monthReportVal = "November";
        }
        if(repMonth.equals("12")){
            monthReportVal = "December";
        }
        return(monthReportVal);
    }
    
}
