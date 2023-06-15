/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import jdk.nashorn.internal.ir.CatchNode;


/**
 *
 * @author HP
 */
public class estudiante extends javax.swing.JFrame {

    /**
     * Creates new form estudiante
     */
    DefaultTableModel modelo;

    public estudiante() {
        initComponents();
        setLocationRelativeTo(this);
        cargarTablaEstudiante();
        cargarDatos();
        bloquearBotonesInicio();
        bloquearTextosInicio();

    }

    public void cargarDatos() {
        jtblEstudiantes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (jtblEstudiantes.getSelectedRow() != -1) {

                    int fila = jtblEstudiantes.getSelectedRow();
                    jtxtCedula.setText(jtblEstudiantes.getValueAt(fila, 0).toString());
                    jtxtNombre.setText(jtblEstudiantes.getValueAt(fila, 1).toString());
                    jtxtApellido.setText(jtblEstudiantes.getValueAt(fila, 2).toString());
                    jtxtDireccion.setText(jtblEstudiantes.getValueAt(fila, 3).toString());
                    jtxtTelefono.setText(jtblEstudiantes.getValueAt(fila, 4).toString());

                    desbloquearBotonesconFila();
                    desbloquearTextosFila();
                }
            }
        });
    }

    public void borrarTextos() {
        jtxtCedula.setText("");
        jtxtNombre.setText("");
        jtxtApellido.setText("");
        jtxtDireccion.setText("");
        jtxtTelefono.setText("");
    }

    public void bloquearBotonesInicio() {
        jbtnNuevo.setEnabled(true);
        jbtnGuardar.setEnabled(false);
        jbtnEliminar.setEnabled(false);
        jbtnActualizar.setEnabled(false);
        jbtnCancelar.setEnabled(false);
        jbtnSalir.setEnabled(true);
    }

    public void desbloquearBotones() {
        jbtnNuevo.setEnabled(false);
        jbtnGuardar.setEnabled(true);
        jbtnEliminar.setEnabled(false);
        jbtnActualizar.setEnabled(false);
        jbtnCancelar.setEnabled(true);
        jbtnSalir.setEnabled(true);
    }

    public void desbloquearBotonesconFila() {
        jbtnNuevo.setEnabled(false);
        jbtnGuardar.setEnabled(false);
        jbtnEliminar.setEnabled(true);
        jbtnActualizar.setEnabled(true);
        jbtnCancelar.setEnabled(true);
        jbtnSalir.setEnabled(true);
    }

    public void bloquearTextosInicio() {
        jtxtCedula.setEnabled(false);
        jtxtNombre.setEnabled(false);
        jtxtApellido.setEnabled(false);
        jtxtDireccion.setEnabled(false);
        jtxtTelefono.setEnabled(false);
    }

    public void desbloquearTextos() {
        jtxtCedula.setEnabled(true);
        jtxtNombre.setEnabled(true);
        jtxtApellido.setEnabled(true);
        jtxtDireccion.setEnabled(true);
        jtxtTelefono.setEnabled(true);
    }

    public void desbloquearTextosFila() {
        jtxtCedula.setEnabled(false);
        jtxtNombre.setEnabled(true);
        jtxtApellido.setEnabled(true);
        jtxtDireccion.setEnabled(true);
        jtxtTelefono.setEnabled(true);
    }

    public boolean validarIngresos() {
        if (jtxtCedula.getText().contains(" ")) {
            JOptionPane.showMessageDialog(this, "Ingrese correctamente la Cedula");
            jtxtCedula.requestFocus();
            return false;
        }
        if (jtxtNombre.getText().contains(" ") || jtxtNombre.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Ingrese correctamente el Nombre");
            jtxtNombre.requestFocus();
            return false;
        }
        if (jtxtApellido.getText().contains(" ") || jtxtApellido.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Ingrese correctamente el Apellido");
            jtxtApellido.requestFocus();
            return false;
        }
        if (jtxtDireccion.getText().contains(" ") || jtxtDireccion.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Ingrese correctamente el Dirección");
            jtxtDireccion.requestFocus();
            return false;
        }
        if (jtxtTelefono.getText().contains(" ") || jtxtTelefono.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Ingrese correctamente el Teléfono");
            jtxtTelefono.requestFocus();
            return false;
        }
        return false;
    }

    private void iniciarFormato() {
        if (jtxtCedula.getText().length() < 10 || jtxtCedula.getText().length() > 10) {
            JOptionPane.showMessageDialog(rootPane, "Cedula contiene 10 digitos, excede o requiere valores");
            JOptionPane.showMessageDialog(rootPane, "Vuelva a intentarlo");
            jtxtCedula.requestFocus();
        }
        if (jtxtTelefono.getText().length() < 10 || jtxtTelefono.getText().length() > 10) {
            JOptionPane.showMessageDialog(rootPane, "Los digitos de un número telefonico son 10, excede o requiere valores");
            JOptionPane.showMessageDialog(rootPane, "Vuelva a intentarlo");
            jtxtTelefono.requestFocus();
        }
    }

    public boolean guardar() {
        if (jtxtCedula.getText().length() < 10 || jtxtCedula.getText().length() > 10) {
            JOptionPane.showMessageDialog(rootPane, "Cedula contiene 10 digitos, excede o requiere valores");
            JOptionPane.showMessageDialog(rootPane, "Vuelva a intentarlo");
            jtxtCedula.requestFocus();
            return false;
        }
        if (jtxtCedula.getText().contains(" ")) {
            jtxtCedula.requestFocus();
            return false;
        }
        if (jtxtNombre.getText().contains(" ") || jtxtNombre.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Ingrese el Nombre");
            jtxtNombre.requestFocus();
            return false;
        }
        if (jtxtApellido.getText().contains(" ") || jtxtApellido.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Ingrese el Apellido");
            jtxtApellido.requestFocus();
            return false;
        }
        if (jtxtDireccion.getText().contains(" ") || jtxtDireccion.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Ingrese el Dirección");
            jtxtDireccion.requestFocus();
            return false;
        }
        if (jtxtTelefono.getText().length() < 10 || jtxtTelefono.getText().length() > 10) {
            JOptionPane.showMessageDialog(rootPane, "Los digitos de un número telefonico son 10, excede o requiere valores");
            JOptionPane.showMessageDialog(rootPane, "Vuelva a intentarlo");
            jtxtTelefono.requestFocus();
            return false;

        }
        if (jtxtTelefono.getText().contains(" ") || jtxtTelefono.getText().equals("")) {
            jtxtTelefono.requestFocus();
            return false;

        } else {
            try {
                validarIngresos();
                Conexion cc = new Conexion();
                Connection cn = cc.conectar();
                String sql = "insert into estudiantes(est_cedula,est_nombre,est_apellido,est_direccion,est_telefono,est_estado) values(?,?,?,?,?,0)";
                PreparedStatement psd = cn.prepareStatement(sql);

                psd.setString(1, jtxtCedula.getText());
                psd.setString(2, jtxtNombre.getText());
                psd.setString(3, jtxtApellido.getText());
                psd.setString(4, jtxtDireccion.getText());
                psd.setString(5, jtxtTelefono.getText());
                //psd.setString(6, sql);
                int n = psd.executeUpdate();
                if (n > 0) {
                    //JOptionPane.showMessageDialog(null, "Se inserto correctamente");
                    cargarTablaEstudiante();
                    borrarTextos();
                    bloquearTextosInicio();
                    bloquearBotonesInicio();
                }

            } catch (SQLException ex) {
                //JOptionPane.showMessageDialog(null, ex);
                JOptionPane.showMessageDialog(rootPane, "Hubo un error inesperado, cancele ingreso y vuelva a intentar");
            }

        }
        return false;
    }

    public boolean modificarEstudiante() {
        if (jtxtNombre.getText().contains(" ") || jtxtNombre.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Ingrese el Nombre");
            jtxtNombre.requestFocus();
            return false;
        }
        if (jtxtApellido.getText().contains(" ") || jtxtApellido.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Ingrese el Apellido");
            jtxtApellido.requestFocus();
            return false;
        }
        if (jtxtDireccion.getText().contains(" ") || jtxtDireccion.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Ingrese la Dirección de Domicilio");
            jtxtDireccion.requestFocus();
            return false;
        }
        if (jtxtTelefono.getText().length() < 10 || jtxtTelefono.getText().length() > 10) {
            JOptionPane.showMessageDialog(rootPane, "Los digitos de un número telefonico son 10, excede o requiere valores");
            JOptionPane.showMessageDialog(rootPane, "Vuelva a intentarlo");
            jtxtTelefono.requestFocus();
            return false;

        }
        if (jtxtTelefono.getText().contains(" ") || jtxtTelefono.getText().equals("")) {
            jtxtTelefono.requestFocus();
            return false;

        } else {
            try {

                Conexion cc = new Conexion();
                Connection cn = cc.conectar();
                String sql, Sql, sQl, sqL;
                sql = "update estudiantes set est_nombre='"
                        + jtxtNombre.getText()
                        + "' where est_cedula='" + jtxtCedula.getText() + "'";
                Sql = "update estudiantes set EST_APELLIDO='"
                        + jtxtApellido.getText()
                        + "' where est_cedula='" + jtxtCedula.getText() + "'";
                sQl = "update estudiantes set EST_DIRECCION='"
                        + jtxtDireccion.getText()
                        + "' where est_cedula='" + jtxtCedula.getText() + "'";
                sqL = "update estudiantes set EST_TELEFONO='"
                        + jtxtTelefono.getText()
                        + "' where est_cedula='" + jtxtCedula.getText() + "'";

                PreparedStatement psd = cn.prepareStatement(sql);
                PreparedStatement ps = cn.prepareStatement(Sql);
                PreparedStatement p = cn.prepareStatement(sQl);
                PreparedStatement psdd = cn.prepareStatement(sqL);

                int a = psd.executeUpdate();
                int b = ps.executeUpdate();
                int c = p.executeUpdate();
                int d = psdd.executeUpdate();
                if (a > 0 || b > 0 || c > 0 || d > 0) {
                    JOptionPane.showMessageDialog(null, "Se actualizó correctamente");
                    cargarTablaEstudiante();
                    borrarTextos();
                    bloquearTextosInicio();
                    bloquearBotonesInicio();
                }
            } catch (SQLException ex) {
                //JOptionPane.showMessageDialog(null, ex);
                JOptionPane.showMessageDialog(rootPane, "Hubo un error inesperado, cancele ingreso y vuelva a intentar");

            }
        }
        return false;
    }

    public void eliminarEstudiante() {
        if (JOptionPane.showConfirmDialog(new JInternalFrame(),
                "Esta seguro de esta acción",
                "Borrar registro", JOptionPane.WARNING_MESSAGE,
                JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                //String est_cedula;
                Conexion cc = new Conexion();
                Connection cn = cc.conectar();
                String Sql = "";
                Sql = "UPDATE estudiantes set est_estado= '1' WHERE est_cedula='" + jtxtCedula.getText() + "'";
                //est_cedula = jtxtCedula.getText();
                //Sql = "delete from universidad where EST_CEDULA='" + est_cedula + "'";
                PreparedStatement psd = cn.prepareStatement(Sql);
                int a = psd.executeUpdate();
                if (a > 0) {
                    JOptionPane.showMessageDialog(null, "Se borró correctamente");
                    cargarTablaEstudiante();
                    borrarTextos();
                    bloquearTextosInicio();
                    bloquearBotonesInicio();
                }
            } catch (SQLException ex) {
                //JOptionPane.showMessageDialog(null, ex);
                JOptionPane.showMessageDialog(rootPane, "Hubo un error inesperado, cancele ingreso y vuelva a intentar");
            }
        }
    }

    public void cargarTablaEstudiante() {
        String[] titulos = {"CEDULA", "NOMBRE", "APELLIDO", "DIRECCION", "TELEFONO"};
        String[] registros = new String[6];
        try {
            Conexion cc = new Conexion();
            Connection cn = cc.conectar();
            String Sql = "";
            //Sql = "select * from estudiantes";
            Sql = "select * from estudiantes where est_estado = 0";
            //redefinir la variable modelo
            modelo = new DefaultTableModel(null, titulos);
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(Sql);
            while (rs.next()) {
                registros[0] = rs.getString("est_cedula");
                registros[1] = rs.getString("est_nombre");
                registros[2] = rs.getString("est_apellido");
                registros[3] = rs.getString("est_direccion");
                registros[4] = rs.getString("est_telefono");
                //registros[5] = rs.getString("est_estado");

                modelo.addRow(registros);

                jtblEstudiantes.setModel(modelo);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);

        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jtxtCedula = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jtxtNombre = new javax.swing.JTextField();
        jtxtApellido = new javax.swing.JTextField();
        jtxtDireccion = new javax.swing.JTextField();
        jtxtTelefono = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jbtnNuevo = new javax.swing.JButton();
        jbtnGuardar = new javax.swing.JButton();
        jbtnEliminar = new javax.swing.JButton();
        jbtnActualizar = new javax.swing.JButton();
        jbtnSalir = new javax.swing.JButton();
        jbtnCancelar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jbtnReporte = new javax.swing.JButton();
        jbtnListado = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblEstudiantes = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setForeground(new java.awt.Color(102, 0, 153));

        jLabel1.setForeground(new java.awt.Color(102, 0, 153));
        jLabel1.setText("Cedula");

        jLabel2.setForeground(new java.awt.Color(102, 0, 153));
        jLabel2.setText("Nombre");

        jLabel3.setForeground(new java.awt.Color(102, 0, 153));
        jLabel3.setText("Apellido");

        jLabel4.setForeground(new java.awt.Color(102, 0, 153));
        jLabel4.setText("Dirección");

        jLabel5.setForeground(new java.awt.Color(102, 0, 153));
        jLabel5.setText("Telefono");

        jtxtApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtApellidoActionPerformed(evt);
            }
        });

        jtxtTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtTelefonoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(82, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel5)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(54, 54, 54)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jtxtDireccion, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtxtCedula, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                    .addComponent(jtxtNombre)
                    .addComponent(jtxtApellido)
                    .addComponent(jtxtTelefono, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(31, 31, 31))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setForeground(new java.awt.Color(102, 0, 153));

        jbtnNuevo.setText("Nuevo");
        jbtnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnNuevoActionPerformed(evt);
            }
        });

        jbtnGuardar.setText("Guardar");
        jbtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnGuardarActionPerformed(evt);
            }
        });

        jbtnEliminar.setText("Eliminar");
        jbtnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnEliminarActionPerformed(evt);
            }
        });

        jbtnActualizar.setText("Actualizar");
        jbtnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnActualizarActionPerformed(evt);
            }
        });

        jbtnSalir.setText("Salir");
        jbtnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSalirActionPerformed(evt);
            }
        });

        jbtnCancelar.setText("Cancelar");
        jbtnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCancelarActionPerformed(evt);
            }
        });

        jbtnReporte.setText("Reporte");
        jbtnReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnReporteActionPerformed(evt);
            }
        });

        jbtnListado.setText("Listado");
        jbtnListado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnListadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jbtnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jbtnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jbtnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE))
                            .addComponent(jbtnReporte, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jbtnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                            .addComponent(jbtnActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbtnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbtnListado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnNuevo)
                    .addComponent(jbtnGuardar))
                .addGap(33, 33, 33)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnEliminar)
                    .addComponent(jbtnActualizar))
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jbtnReporte, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jbtnListado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jbtnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(jbtnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jtblEstudiantes);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(35, 35, 35))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtxtApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtApellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtApellidoActionPerformed

    private void jtxtTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtTelefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtTelefonoActionPerformed

    private void jbtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCancelarActionPerformed
        // TODO add your handling code here:
        borrarTextos();
        bloquearTextosInicio();
        bloquearBotonesInicio();
    }//GEN-LAST:event_jbtnCancelarActionPerformed

    private void jbtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSalirActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jbtnSalirActionPerformed

    private void jbtnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnActualizarActionPerformed
        // TODO add your handling code here:

        modificarEstudiante();
    }//GEN-LAST:event_jbtnActualizarActionPerformed

    private void jbtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnEliminarActionPerformed
        // TODO add your handling code here:
        eliminarEstudiante();
    }//GEN-LAST:event_jbtnEliminarActionPerformed

    private void jbtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnGuardarActionPerformed
        guardar();
    }//GEN-LAST:event_jbtnGuardarActionPerformed

    private void jbtnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNuevoActionPerformed
        // TODO add your handling code here:
        desbloquearTextos();
        desbloquearBotones();
    }//GEN-LAST:event_jbtnNuevoActionPerformed

    private void jbtnReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnReporteActionPerformed
        // TODO add your handling code here:
//        try {
//            conexion cc = new conexion();
//            Connection cn = cc.conectar();
//            Map parametro = new HashMap();
//            parametro.put("cedula", jtxtCedula.getText());
//            JasperReport reporte = JasperCompileManager.compileReport("c://reportes/reporteEstudiante.jrxml");
//            JasperPrint impresion = JasperFillManager.fillReport(reporte, parametro, cn);
//            JasperViewer.viewReport(impresion, false);
//        } catch (JRException ex) {
//            JOptionPane.showMessageDialog(null, "Reporte no esta disponible, counicate con administracion");
//        }

    }//GEN-LAST:event_jbtnReporteActionPerformed

    private void jbtnListadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnListadoActionPerformed
        // TODO add your handling code here:

//        try {
//            conexion cc = new conexion();
//            Connection cn = cc.conectar();
//            JasperReport reporte = JasperCompileManager.compileReport("c://reportes/reporteEstudiante.jrxml");
//            JasperPrint impresion = JasperFillManager.fillReport(reporte, null, cn);
//            JasperViewer.viewReport(impresion, false);
//        } catch (JRException ex) {
//            JOptionPane.showMessageDialog(null, "Reporte no esta disponible, counicate con administracion");
//        }


    }//GEN-LAST:event_jbtnListadoActionPerformed

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
            java.util.logging.Logger.getLogger(estudiante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(estudiante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(estudiante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(estudiante.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new estudiante().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton jbtnActualizar;
    private javax.swing.JButton jbtnCancelar;
    private javax.swing.JButton jbtnEliminar;
    private javax.swing.JButton jbtnGuardar;
    private javax.swing.JButton jbtnListado;
    private javax.swing.JButton jbtnNuevo;
    private javax.swing.JButton jbtnReporte;
    private javax.swing.JButton jbtnSalir;
    private javax.swing.JTable jtblEstudiantes;
    private javax.swing.JTextField jtxtApellido;
    private javax.swing.JTextField jtxtCedula;
    private javax.swing.JTextField jtxtDireccion;
    private javax.swing.JTextField jtxtNombre;
    private javax.swing.JTextField jtxtTelefono;
    // End of variables declaration//GEN-END:variables
}
