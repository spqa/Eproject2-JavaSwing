/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system.Mark;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import system.DBConnect;
import system.Subject.Subject;

/**
 *
 * @author c1409l3544
 */
public class Mark {
    private int StuId;
    private int SubID;
    private float mark;
    private String Grade;

    public Mark() {
    }

    public Mark(int StuId, int SubID, float mark, String Grade) {
        this.StuId = StuId;
        this.SubID = SubID;
        this.mark = mark;
        this.Grade = Grade;
    }

    public int getStuId() {
        return StuId;
    }

    public int getSubID() {
        return SubID;
    }

    public float getMark() {
        return mark;
    }

    public String getGrade() {
        return Grade;
    }

    public void setStuId(int StuId) {
        this.StuId = StuId;
    }

    public void setSubID(int SubID) {
        this.SubID = SubID;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public void setGrade(String Grade) {
        this.Grade = Grade;
    }

    @Override
    public String toString() {
        return "Mark{" + "StuId=" + StuId + ", SubID=" + SubID + ", mark=" + mark + ", Grade=" + Grade + '}';
    }
    
    public static List<Mark> getMarkByStuID(int StudentID){
        Connection conn=null;
        List<Mark> lstMark=new ArrayList<>();
        try {
            conn = DBConnect.connectDatabase();
            PreparedStatement pre=conn.prepareStatement("Select * from mark where StuId=?");
            pre.setInt(1, StudentID);
            ResultSet rs=pre.executeQuery();
            while (rs.next()) {                
                lstMark.add(new Mark(StudentID, rs.getInt(2), rs.getFloat(3),rs.getString(4) ));
            }
            return lstMark;
        } catch (SQLException ex) {
            Logger.getLogger(Mark.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if (conn!=null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Mark.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }
    
    
    public static DefaultTableModel getTableMark(int StuID){
        DefaultTableModel temp=new DefaultTableModel();
        temp.addColumn("Subject");
        temp.addColumn("Mark");
        temp.addColumn("Grade");
        String sql="SELECT Mark.Mark, Mark.Grade, Subject.Name FROM Mark INNER JOIN Subject ON Mark.SubId = Subject.SubId WHERE Mark.StuId=?";
        Connection conn=null;
        try {           
            conn = DBConnect.connectDatabase();
            PreparedStatement stmt=conn.prepareStatement(sql);
            stmt.setInt(1, StuID);
            ResultSet rs=stmt.executeQuery();
            while (rs.next()) {                
                String[] rows={rs.getString(3),rs.getFloat(1)+"",rs.getString(2)};
                temp.addRow(rows);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Mark.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            if (conn!=null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Mark.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return temp;
    }
    public static void main(String[] args) {
        getTableMark(1);
    }
}
