
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Cargua
 */
public class Conexion {
    Connection con;
  public Connection conectar(){
      try{
      Class.forName("com.mysql.cj.jdbc.Driver");
       con=DriverManager.getConnection("jdbc:mysql://localhost/adminagiles", "root", "");
//       JOptionPane.showMessageDialog(null, "Conectado");
      }catch (ClassNotFoundException | SQLException ex){
          JOptionPane.showMessageDialog(null, ex);
      }
      return con;
  }     
}
