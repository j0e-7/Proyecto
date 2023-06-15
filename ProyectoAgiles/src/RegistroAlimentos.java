/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;


/**
 *
 * @author Personal
 */
public class RegistroAlimentos extends javax.swing.JInternalFrame {
DefaultTableModel modelo;
    ArrayList alimentos=new ArrayList();
    /**
     * Creates new form RegistroEstudiante
     */
    public RegistroAlimentos() {
        initComponents();
        cargarTablaAlimentos();
        cargarDatos();
        bloquearBotonesInicio();
        bloquearTextosInicio();
    }

    public void cargarDatos() {
        jtblAlimentos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (jtblAlimentos.getSelectedRow() != -1) {

                    int fila = jtblAlimentos.getSelectedRow();
                    jtxtCod_ali.setText(jtblAlimentos.getValueAt(fila, 0).toString());
                    jtxtTipoAlimento.setText(jtblAlimentos.getValueAt(fila, 1).toString());
                    jtxtNombre.setText(jtblAlimentos.getValueAt(fila, 2).toString());
                    jtxtCalorias.setText(jtblAlimentos.getValueAt(fila, 3).toString());
                    jtxtCantgr.setText(jtblAlimentos.getValueAt(fila, 4).toString());

                    desbloquearBotonesconFila();
                    desbloquearTextosFila();
                }
            }
        });
    }

    public void borrarTextos() {
        jtxtTipoAlimento.setText("");
        jtxtNombre.setText("");
        jtxtCalorias.setText("");
        jtxtCantgr.setText("");
        jtxtCod_ali.setText("");
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
        jtxtTipoAlimento.setEnabled(false);
        jtxtNombre.setEnabled(false);
        jtxtCalorias.setEnabled(false);
        jtxtCantgr.setEnabled(false);
        jtxtCod_ali.setEnabled(false);
    }

    public void desbloquearTextos() {
        jtxtTipoAlimento.setEnabled(true);
        jtxtNombre.setEnabled(true);
        jtxtCalorias.setEnabled(true);
        jtxtCantgr.setEnabled(true);
        jtxtCod_ali.setEnabled(true);
    }

    public void desbloquearTextosFila() {
        jtxtTipoAlimento.setEnabled(true);
        jtxtNombre.setEnabled(true);
        jtxtCalorias.setEnabled(true);
        jtxtCantgr.setEnabled(true);
        jtxtCod_ali.setEnabled(false);
    }

    public boolean validarIngresos() {
        if (jtxtCod_ali.getText().contains(" ")|| jtxtNombre.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Ingrese el codigo del alimento");
            jtxtCod_ali.requestFocus();
            return false;
        }
        if (jtxtTipoAlimento.getText().contains(" ")|| jtxtNombre.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Ingrese un tipo de alimento");
            jtxtTipoAlimento.requestFocus();
            return false;
        }
        if (jtxtNombre.getText().contains(" ") || jtxtNombre.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Ingrese correctamente el Nombre");
            jtxtNombre.requestFocus();
            return false;
        }
        if (jtxtCalorias.getText().contains(" ") || jtxtCalorias.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Ingrese correctamente las Calorias");
            jtxtCalorias.requestFocus();
            return false;
        }
        if (jtxtCantgr.getText().contains(" ") || jtxtCantgr.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Ingrese correctamente la cantidad en gramos");
            jtxtCantgr.requestFocus();
            return false;
        }
        return true;
    }

    public void guardar() {
        if (validarIngresos()==false) {
            
        }
        controlAlimentos();
        int indice = alimentos.indexOf(jtxtCod_ali.getText());
        int indice2= alimentos.indexOf(jtxtNombre.getText());
            if (indice == -1) {
                if(indice2== -1){
        Conexion cc=new Conexion();
                Connection cn=cc.conectar();
                cc.conectar();
                String sql="insert into alimentos (Codigo_alimento,Tipo_alimento,nombre,Calorias,Cantidad_gramos,Estado) values(?,?,?,?,?,?)";
                try {
                    PreparedStatement psd= cn.prepareStatement(sql);
                    psd.setString(1, jtxtCod_ali.getText());
                    psd.setString(2, jtxtTipoAlimento.getText());
                    psd.setString(3, jtxtNombre.getText());
                psd.setString(4, jtxtCalorias.getText());
                psd.setString(5, jtxtCantgr.getText());
                    psd.setString(6,"1");
                    int n=psd.executeUpdate();
                    if (n>0) {
                        JOptionPane.showMessageDialog(null, "Se inserto correctamente");
                        cargarTablaAlimentos();
                    borrarTextos();
                    bloquearTextosInicio();
                    bloquearBotonesInicio();
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Fallo al insertar");
                }
    }else{
                JOptionPane.showMessageDialog(null,"Este alimento ya existe porfavor ingrese correctamente");
            
    }
            }else{
            JOptionPane.showMessageDialog(null,"Este codigo ya existe porfavor ingrese correctamente");
            }
    }
    public boolean modificarAlimento() {
        if (jtxtTipoAlimento.getText().contains(" ")) {
            JOptionPane.showMessageDialog(this, "Ingrese el Tipo de Alimento");
            jtxtTipoAlimento.requestFocus();
            return false;
        }
        if (jtxtNombre.getText().contains(" ") || jtxtNombre.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Ingrese el Nombre");
            jtxtNombre.requestFocus();
            return false;
        }
        if (jtxtCalorias.getText().contains(" ") || jtxtCalorias.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Ingrese las Calorias");
            jtxtCalorias.requestFocus();
            return false;
        }
        if (jtxtCantgr.getText().contains(" ") || jtxtCantgr.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Ingrese la Cantidad en gramos");
            jtxtCantgr.requestFocus();
            return false;
        }
        else {
            try {

                Conexion cc = new Conexion();
                Connection cn = cc.conectar();
                String SQL,sql, Sql, sQl, sqL;
                SQL = "update alimentos set Tipo_alimento='"
                        + jtxtTipoAlimento.getText()
                        + "' where Codigo_alimento='" + jtxtCod_ali.getText() + "'";
                sql = "update alimentos set nombre='"
                        + jtxtNombre.getText()
                        + "' where Codigo_alimento='" + jtxtCod_ali.getText() + "'";
                Sql = "update alimentos set Calorias='"
                        + jtxtCalorias.getText()
                        + "' where Codigo_alimento='" + jtxtCod_ali.getText() + "'";
                sQl = "update alimentos set Cantidad_gramos='"
                        + jtxtCantgr.getText()
                        + "' where Codigo_alimento='" + jtxtCod_ali.getText() + "'";

                PreparedStatement psd = cn.prepareStatement(sql);
                PreparedStatement ps = cn.prepareStatement(Sql);
                PreparedStatement p = cn.prepareStatement(sQl);
                PreparedStatement psdd = cn.prepareStatement(SQL);

                int a = psd.executeUpdate();
                int b = ps.executeUpdate();
                int c = p.executeUpdate();
                int d = psdd.executeUpdate();
                if (a > 0 || b > 0 || c > 0 || d > 0) {
                    JOptionPane.showMessageDialog(null, "Se actualizó correctamente");
                    cargarTablaAlimentos();
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

    public void eliminarAlimento() {
        if (JOptionPane.showConfirmDialog(new JInternalFrame(),
                "Esta seguro de esta acción",
                "Borrar registro", JOptionPane.WARNING_MESSAGE,
                JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                //String est_cedula;
                Conexion cc = new Conexion();
                Connection cn = cc.conectar();
                String Sql = "";
                Sql = "UPDATE alimentos set Estado= '0' WHERE Codigo_alimento='" + jtxtCod_ali.getText() + "'";
                //est_cedula = jtxtCedula.getText();
                //Sql = "delete from universidad where EST_CEDULA='" + est_cedula + "'";
                PreparedStatement psd = cn.prepareStatement(Sql);
                int a = psd.executeUpdate();
                if (a > 0) {
                    JOptionPane.showMessageDialog(null, "Se borró correctamente");
                    cargarTablaAlimentos();
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

    public void cargarTablaAlimentos() {
        String[] titulos = {"Codigo","Tipo de Alimento", "Nombre", "Calorias", "Cantidad en gramos"};
        String[] registros = new String[7];
        try {
            Conexion cc = new Conexion();
            Connection cn = cc.conectar();
            String Sql = "";
            Sql = "select * from alimentos where Estado = 1";
            //redefinir la variable modelo
            modelo = new DefaultTableModel(null, titulos);
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(Sql);
            while (rs.next()) {
                registros[0]=rs.getString("Codigo_alimento");
                registros[1] = rs.getString("Tipo_alimento");
                registros[2] = rs.getString("nombre");
                registros[3] = rs.getString("Calorias");
                registros[4] = rs.getString("Cantidad_gramos");
                

                modelo.addRow(registros);

                jtblAlimentos.setModel(modelo);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);

        }

    }

       public void controlAlimentos() {
        String[] registros = new String[2];
        try {
            Conexion cc = new Conexion();
            Connection cn = cc.conectar();
            String Sql = "select Codigo_alimento,nombre from alimentos";
            //redefinir la variable modelo
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(Sql);
            while (rs.next()) {
                registros[0] = rs.getString("Codigo_alimento");
                registros[1]=rs.getNString("nombre");
                alimentos.add(rs.getString("nombre"));
                alimentos.add(rs.getString("Codigo_alimento"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MetodosCreacionCuenta.class.getName()).log(Level.SEVERE, null, ex);
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
        jtxtTipoAlimento = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jtxtNombre = new javax.swing.JTextField();
        jtxtCalorias = new javax.swing.JTextField();
        jtxtCantgr = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jtxtCod_ali = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jbtnNuevo = new javax.swing.JButton();
        jbtnGuardar = new javax.swing.JButton();
        jbtnEliminar = new javax.swing.JButton();
        jbtnActualizar = new javax.swing.JButton();
        jbtnSalir = new javax.swing.JButton();
        jbtnCancelar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblAlimentos = new javax.swing.JTable();
        jtxtbuscar = new javax.swing.JTextField();

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setForeground(new java.awt.Color(102, 0, 153));

        jLabel1.setForeground(new java.awt.Color(102, 0, 153));
        jLabel1.setText("Tipo de alimento");

        jLabel2.setForeground(new java.awt.Color(102, 0, 153));
        jLabel2.setText("Nombre");

        jLabel3.setForeground(new java.awt.Color(102, 0, 153));
        jLabel3.setText("Calorias");

        jLabel4.setForeground(new java.awt.Color(102, 0, 153));
        jLabel4.setText("Cantidad en gramos");

        jtxtCalorias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtCaloriasActionPerformed(evt);
            }
        });

        jLabel6.setForeground(new java.awt.Color(102, 0, 153));
        jLabel6.setText("Codigo del Alimento");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6))
                .addGap(85, 85, 85)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jtxtCantgr, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                    .addComponent(jtxtTipoAlimento, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtxtNombre)
                    .addComponent(jtxtCalorias)
                    .addComponent(jtxtCod_ali))
                .addGap(31, 31, 31))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jtxtCod_ali, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtxtTipoAlimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jtxtCalorias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jtxtCantgr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28))
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
                            .addComponent(jbtnEliminar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbtnCancelar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jbtnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbtnActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbtnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                .addGap(40, 40, 40)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jbtnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(jbtnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jtblAlimentos);

        jtxtbuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtxtbuscarKeyReleased(evt);
            }
        });

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
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jtxtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addComponent(jtxtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jtxtCaloriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtCaloriasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtCaloriasActionPerformed

    private void jbtnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNuevoActionPerformed
        // TODO add your handling code here:
        desbloquearTextos();
        desbloquearBotones();
    }//GEN-LAST:event_jbtnNuevoActionPerformed

    private void jbtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnGuardarActionPerformed
        guardar();
    }//GEN-LAST:event_jbtnGuardarActionPerformed

    private void jbtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnEliminarActionPerformed
        // TODO add your handling code here:
        eliminarAlimento();
    }//GEN-LAST:event_jbtnEliminarActionPerformed

    private void jbtnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnActualizarActionPerformed
        // TODO add your handling code here:

        modificarAlimento();
    }//GEN-LAST:event_jbtnActualizarActionPerformed

    private void jbtnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSalirActionPerformed

        dispose();
    }//GEN-LAST:event_jbtnSalirActionPerformed

    private void jbtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCancelarActionPerformed
        // TODO add your handling code here:
        borrarTextos();
        bloquearTextosInicio();
        bloquearBotonesInicio();
    }//GEN-LAST:event_jbtnCancelarActionPerformed

    private void jtxtbuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtbuscarKeyReleased
        // TODO add your handling code here:
        String [] registros = new String[5];
        try {
            String[] titulos = {"Codigo","Tipo de Alimento", "Nombre", "Calorias", "Cantidad en gramos"};
            modelo=new DefaultTableModel(titulos, 0);
            Conexion cc = new Conexion();
            Connection cn = cc.conectar();
            String Sql="SELECT * FROM alimentos WHERE Codigo_alimento LIKE '"+jtxtbuscar.getText()+"%'";
            //redefinir la variable modelo
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(Sql);
            while(rs.next()){
                registros[0]=rs.getString("Codigo_alimento");
                registros[1] = rs.getString("Tipo_alimento");
                registros[2] = rs.getString("nombre");
                registros[3] = rs.getString("Calorias");
                registros[4] = rs.getString("Cantidad_gramos");
                
                modelo.addRow(registros);
                
            }
                jtblAlimentos.setModel(modelo);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
          
        }
        if(jtxtbuscar.getText().isEmpty()){
            cargarDatos();
        }
    }//GEN-LAST:event_jtxtbuscarKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton jbtnActualizar;
    private javax.swing.JButton jbtnCancelar;
    private javax.swing.JButton jbtnEliminar;
    private javax.swing.JButton jbtnGuardar;
    private javax.swing.JButton jbtnNuevo;
    private javax.swing.JButton jbtnSalir;
    private javax.swing.JTable jtblAlimentos;
    private javax.swing.JTextField jtxtCalorias;
    private javax.swing.JTextField jtxtCantgr;
    private javax.swing.JTextField jtxtCod_ali;
    private javax.swing.JTextField jtxtNombre;
    private javax.swing.JTextField jtxtTipoAlimento;
    private javax.swing.JTextField jtxtbuscar;
    // End of variables declaration//GEN-END:variables
}
