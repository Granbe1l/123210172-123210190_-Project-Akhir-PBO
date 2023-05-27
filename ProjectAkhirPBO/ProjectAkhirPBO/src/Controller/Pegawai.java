package Controller;

import Model.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Pegawai {

    Connection con;


    PreparedStatement ps = null;
    public Pegawai() {
        con = MyConnection.getConnection();
    }

    //table row
    public int getMax() {
        int IdPegawai = 0;
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select max(id_pegawai) from pegawai");
            while (rs.next()) {
                IdPegawai = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Pegawai.class.getName()).log(Level.SEVERE, null, ex);
        }
        return IdPegawai + 1;
    }

    //insert data
    public void insert(String nama, String date, String kelamin, String email, String telp, String alamat) {
       
        try {
            ps = con.prepareStatement("INSERT INTO `pegawai` (`nama_pegawai`, `tanggal_lahir`, `jenis_kelamin`, `email`, `no_telp`, `alamat`) VALUES (?,?,?,?,?,?);");

            ps.setString(1, nama);
            ps.setString(2, date);
            ps.setString(3, kelamin);
            ps.setString(4, email);
            ps.setString(5, telp);
            ps.setString(6, alamat);

            ps.execute();

            JOptionPane.showMessageDialog(null, "Pegawai baru telah ditambahkan");

        } catch (SQLException ex) {
            System.out.println("Error :" + ex.getMessage());
        
    } 
        
    }
    public boolean isEmailExist(String email){
        
        try {
            ps = con.prepareStatement("SELECT * from pegawai where email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Pegawai.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
     public boolean isTlpExist(String telp){
        
        try {
            ps = con.prepareStatement("SELECT * from pegawai where no_telp = ?");
            ps.setString(1, telp);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Pegawai.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
     public boolean isIdExist(int id){
        
        try {
            ps = con.prepareStatement("SELECT * from pegawai where id_pegawai = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Pegawai.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
     public void getPegawaiValue(JTable table , String searchValue){
       String sql = "select * from pegawai where concat(id_pegawai, nama_pegawai,tanggal_lahir, jenis_kelamin, email, no_telp, alamat) like ? order by id_pegawai desc";
     
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%"+searchValue+"%");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while(rs.next()){
                row = new Object[7];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);
                row[5] = rs.getString(6);
                row[6] = rs.getString(7);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
     }
       public void update(int id, String nama, String date, String kelamin, String email, String telp, String alamat){
           String sql = "update pegawai set nama_pegawai=?,tanggal_lahir=?,jenis_kelamin=?,email=?,no_telp=?,alamat=? where id_pegawai=? ";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, nama);
            ps.setString(2, date);
            ps.setString(3, kelamin);
            ps.setString(4, email);
            ps.setString(5, telp);
            ps.setString(6, alamat);
            ps.setInt(7,id);
            
            if(ps.executeUpdate()>0){
             JOptionPane.showMessageDialog(null, "Data Berhasil Di Update");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Pegawai.class.getName()).log(Level.SEVERE, null, ex);
        }
       }
           public void delete(int id){
             int yesOrNo =  JOptionPane.showConfirmDialog(null, "Hati hati nanti data lain ikut terhapus","Pegawai Terhapus",JOptionPane.OK_CANCEL_OPTION,0);
             if(yesOrNo == JOptionPane.OK_OPTION){
                 try {
                     ps = con.prepareStatement("delete from pegawai where id_pegawai = ?");
                     ps.setInt(1, id);
                     if(ps.executeUpdate()>0){
                         JOptionPane.showMessageDialog(null, "Data Berhasil Di Hapus");
                     }
                 } catch (SQLException ex) {
                     Logger.getLogger(Pegawai.class.getName()).log(Level.SEVERE, null, ex);
                 }
                 
           }
       }
           
  

}
