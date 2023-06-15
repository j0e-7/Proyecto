/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Cargua
 */
public class MetodosCreacionCuenta {

    Conexion cc = new Conexion();
    Connection con;
    Datos ds = new Datos();
    JFrame jf    = new JFrame();
    ArrayList usuario = new ArrayList();
    int indice;

    public JFrame getJf() {
        return jf;
    }

    public void setJf(JFrame jf) {
        this.jf = jf;
    }

    public void coloresIniciales(JTextField txtNombre, JTextField txtApellido, JTextField txtUsuario, JPasswordField txtClave, JPasswordField txtRepetirClave) {
        txtNombre.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        txtApellido.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        txtUsuario.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        txtClave.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        txtRepetirClave.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }
    boolean controles = false;

    public boolean controles(JTextField txtNombre, JTextField txtApellido, JTextField txtCedula, JPasswordField txtClave, JPasswordField txtRepetirClave) {
        if (txtNombre.getText().trim().isEmpty() || "".equals(txtNombre.getText())) {
            JOptionPane.showMessageDialog(null, "Ingrese un nombre", "Verificar datos", JOptionPane.ERROR_MESSAGE);
            txtNombre.requestFocus();
            txtNombre.setBorder(BorderFactory.createLineBorder(Color.decode("#EF280F")));
            return controles = false;
            }else if (txtApellido.getText().trim().isEmpty() || "".equals(txtApellido.getText())) {
            JOptionPane.showMessageDialog(null, "Ingrese un apellido", "Verificar datos", JOptionPane.ERROR_MESSAGE);
            txtApellido.requestFocus();
            txtApellido.setBorder(BorderFactory.createLineBorder(Color.decode("#EF280F")));
            return controles = false;
        }else if (txtCedula.getText().trim().isEmpty() || "".equals(txtCedula.getText())) {
            JOptionPane.showMessageDialog(null, "Ingrese su cedula", "Verificar datos", JOptionPane.ERROR_MESSAGE);
            txtCedula.requestFocus();
            txtCedula.setBorder(BorderFactory.createLineBorder(Color.decode("#EF280F")));
            return controles = false;
        
        } else if (txtClave.getText().trim().isEmpty() || "".equals(txtClave.getText())) {
            JOptionPane.showMessageDialog(null, "Ingrese una clave", "Verificar datos", JOptionPane.ERROR_MESSAGE);
            txtClave.requestFocus();
            txtClave.setBorder(BorderFactory.createLineBorder(Color.decode("#EF280F")));
            return controles = false;
        }else if (txtRepetirClave.getText().trim().isEmpty() || "".equals(txtRepetirClave.getText())) {
            JOptionPane.showMessageDialog(null, "Repita la clave", "Verificar datos", JOptionPane.ERROR_MESSAGE);
            txtRepetirClave.requestFocus();
            txtRepetirClave.setBorder(BorderFactory.createLineBorder(Color.decode("#EF280F")));
            return controles = false;
        }
        coloresIniciales(txtNombre, txtApellido, txtCedula, txtClave, txtRepetirClave);
        controles = true;
        return controles;
    }

    public Boolean crear(Boolean cn,JTextField txtNombre, JTextField txtApellido, JComboBox txtCargo, JTextField txtCedula, JPasswordField txtClave, JPasswordField txtRepetirClave) {
        controles(txtNombre, txtApellido,txtCedula, txtClave, txtRepetirClave);
        controlUsuario();
        indice = usuario.indexOf(txtCedula.getText());
        if (controles ==true) {
            if (indice == -1) {
            
                ds.setSql2("Insert into login(cedula,nombre,apellido,cargo,contrasenia,repetircontrasenia) values(?,?,?,?,?,?)");
                ds.setCedula(txtCedula.getText());
                ds.setNombre(txtNombre.getText());
                ds.setApellido(txtApellido.getText());
                ds.setCargo(txtCargo.getSelectedItem().toString());
                ds.setClave(txtClave.getText().toUpperCase());
                ds.setRepetirClave(txtRepetirClave.getText().toUpperCase());
                try {
                    //Conexion para crear y mandar a la base de datos
                    con = cc.conectar();
                    //Statement st = conec.createStatement();
                    PreparedStatement psd = con.prepareStatement(ds.getSql2());
                    psd.setString(1, ds.getCedula());
                    psd.setString(2, ds.getNombre());
                    psd.setString(3, ds.getApellido());
                    psd.setString(4, ds.getCargo());
                    psd.setString(5, ds.getClave());
                    psd.setString(6, ds.getRepetirClave());
                    if (ds.getClave().equals(ds.getRepetirClave())) {
                        JOptionPane.showMessageDialog(null, "Cuenta creada");
                        psd.executeUpdate();
                        return cn=true; 
                        
                    } else {
                        JOptionPane.showMessageDialog(null, "Las claves no coinciden");
                        return cn=false;
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
            
            } else {
                JOptionPane.showMessageDialog(null, "Ya existe este usuario, elija otro");
                return cn=false;
            }
        } 
        return cn;
    }

    public void controlUsuario() {
        String[] registros = new String[1];
        try {
            Conexion cc = new Conexion();
            Connection cn = cc.conectar();
            String Sql = "select cedula from login";
            //redefinir la variable modelo
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(Sql);
            while (rs.next()) {
                registros[0] = rs.getString("cedula");
                usuario.add(rs.getString("cedula"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MetodosCreacionCuenta.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}


