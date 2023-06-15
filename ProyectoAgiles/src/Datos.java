/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Cargua
 */
public class Datos {
    
    private String Cedula,Nombre,Apellido,Cargo;
    private String Clave,repetirClave;
    private String sql,sql2,sql3;
    private Statement st1,st2;
    private ResultSet rs1,rs2;

    public String getCedula() {
        return Cedula;
    }

    public void setCedula(String Cedula) {
        this.Cedula = Cedula;
    }

    public String getClave() {
        return this.Clave;
    }

    public void setClave(String clave) {
        this.Clave = clave;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String Apellido) {
        this.Apellido = Apellido;
    }

    public String getCargo() {
        return Cargo;
    }

    public void setCargo(String Cargo) {
        this.Cargo = Cargo;
    }

    public String getRepetirClave() {
        return repetirClave;
    }

    public void setRepetirClave(String repetirClave) {
        this.repetirClave = repetirClave;
    }

    

    public String getSql() {
        return this.sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getSql2() {
        return this.sql2;
    }

    public void setSql2(String sql2) {
        this.sql2 = sql2;
    }

    public String getSql3() {
        return this.sql3;
    }

    public void setSql3(String sql3) {
        this.sql3 = sql3;
    }

    public Statement getSt2() {
        return this.st2;
    }

    public void setSt2(Statement st2) {
        this.st2 = st2;
    }

    public ResultSet getRs2() {
        return this.rs2;
    }

    public void setRs2(ResultSet rs2) {
        this.rs2 = rs2;
    }
    
    

    public Statement getSt1() {
        return this.st1;
    }

    public void setSt1(Statement st1) {
        this.st1 = st1;
    }

    public ResultSet getRs1() {
        return this.rs1;
    }

    public void setRs1(ResultSet rs1) {
        this.rs1 = rs1;
    }    
    
}
