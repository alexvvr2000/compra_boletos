package com.megaboletos.usuarios;
import java.sql.*;
class Usuario {
    String nombre = "";
    String apellidoPaterno = "";
    String apellidoMaterno = "";
    String correo = "";
    int idUsuario = 0;
    Connection conexionBase;
    boolean sesionCerrada = false;
    Usuario (Connection conexion, String correo, String claveAcceso) throws Exception{
        this.conexionBase = conexion;
        PreparedStatement query = this.conexionBase.prepareStatement(
                "select idusuario ,nombre, apellidopaterno, apellidomaterno " +
                        " from usuario " +
                        " where claveiniciosesion = ? and correo = ?;"
        );
        query.setString(1, claveAcceso);
        query.setString(2, correo);
        ResultSet resultado = query.executeQuery();
        resultado.next();
        this.correo = correo;
        this.idUsuario = resultado.getInt("idusuario");
        this.nombre = resultado.getString("nombre");
        this.apellidoPaterno = resultado.getString("apellidopaterno");
        this.apellidoMaterno = resultado.getString("apellidomaterno");
    }
    protected Usuario (Connection conexion){
        this.conexionBase = conexion;
    }
    public void cerrarSesion() {
        this.sesionCerrada = true;
    }
    public String getNombre() {
        return this.nombre;
    }
    public String getApellidoPaterno() {
        return this.apellidoPaterno;
    }
    public String getApellidoMaterno() {
        return this.apellidoMaterno;
    }
    public String getCorreo() {
        return this.correo;
    }
    public int getIdUsuario() {
        return this.idUsuario;
    }
    public boolean estaCerradaSesion() {
        return this.sesionCerrada;
    }
}
