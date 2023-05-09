package com.megaboletos.usuarios;
import java.sql.*;
class Usuario {
    String nombre = "";
    String apellidoPaterno = "";
    String apellidoMaterno = "";
    String correo = "";
    int idUsuario = 0;
    Connection conexionBase;
    boolean estaEliminado = false;
    boolean sesionCerrada = false;
    Usuario (Connection conexion, String correo, String claveAcceso) throws SQLException{
        PreparedStatement query = conexion.prepareStatement(
                "select idusuario ,nombre, apellidopaterno, apellidomaterno " +
                        "from usuario " +
                        "where claveiniciosesion = ? and correo = ?;"
        );
        query.setString(1, claveAcceso);
        query.setString(2, correo);
        ResultSet resultado = query.executeQuery();
        resultado.next();
        this.correo = correo;
        this.nombre = resultado.getString("nombre");
        this.apellidoPaterno = resultado.getString("apellidopaterno");
        this.apellidoMaterno = resultado.getString("apellidomaterno");
        this.idUsuario = resultado.getInt("idusuario");
    }
    protected Usuario (Connection conexion) {
        this.conexionBase = conexion;
    }
    public boolean cerrarSesion() {
        return false;
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
}
