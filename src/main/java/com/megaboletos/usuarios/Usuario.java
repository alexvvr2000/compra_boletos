package com.megaboletos.usuarios;
import java.sql.*;
class Usuario {
    String nombre = "";
    String apellidoPaterno = "";
    String apellidoMaterno = "";
    String correo = "";
    int idUsuario = 0;
    Connection conexionBase = null;
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
        boolean existeUsuario = resultado.next();
        if (!existeUsuario) throw new Exception("Usuario no existe");
        this.correo = correo;
        this.idUsuario = resultado.getInt("idusuario");
        this.nombre = resultado.getString("nombre");
        this.apellidoPaterno = resultado.getString("apellidopaterno");
        this.apellidoMaterno = resultado.getString("apellidomaterno");
    }
    protected Usuario(){}
    public void cerrarSesion() throws Exception {
        if (this.sesionCerrada) throw new Exception("Sesion esta cerrada");
        if (!Administrador.existeUsuario(this.conexionBase, this.idUsuario))
            throw new Exception("El usuario ya no existe");
        this.sesionCerrada = true;
    }
    public String getNombre() throws Exception{
        if (this.sesionCerrada) throw new Exception("Sesion esta cerrada");
        if (!Administrador.existeUsuario(this.conexionBase, this.idUsuario))
            throw new Exception("El usuario ya no existe");
        return this.nombre;
    }
    public String getApellidoPaterno() throws Exception{
        if (this.sesionCerrada) throw new Exception("Sesion esta cerrada");
        if (!Administrador.existeUsuario(this.conexionBase, this.idUsuario))
            throw new Exception("El usuario ya no existe");
        return this.apellidoPaterno;
    }
    public String getApellidoMaterno() throws Exception{
        if (this.sesionCerrada) throw new Exception("Sesion esta cerrada");
        if (!Administrador.existeUsuario(this.conexionBase, this.idUsuario))
            throw new Exception("El usuario ya no existe");
        return this.apellidoMaterno;
    }
    public String getCorreo() throws Exception {
        if (this.sesionCerrada) throw new Exception("Sesion esta cerrada");
        if (!Administrador.existeUsuario(this.conexionBase, this.idUsuario))
            throw new Exception("El usuario ya no existe");
        return this.correo;
    }
    public int getIdUsuario() throws Exception {
        if (this.sesionCerrada) throw new Exception("Sesion esta cerrada");
        if (!Administrador.existeUsuario(this.conexionBase, this.idUsuario))
            throw new Exception("El usuario ya no existe");
        return this.idUsuario;
    }
    public boolean estaCerradaSesion() throws Exception{
        if (!Administrador.existeUsuario(this.conexionBase, this.idUsuario))
            throw new Exception("El usuario ya no existe");
        return this.sesionCerrada;
    }
}
