package com.megaboletos.usuarios;
import java.sql.*;
import com.megaboletos.ObjetoBase;
import org.json.JSONObject;
class Usuario implements ObjetoBase {
    String nombre;
    String apellidoPaterno;
    String apellidoMaterno;
    String correo;
    int idUsuario;
    Connection conexionBase;
    boolean estaEliminado = false;
    boolean sesionCerrada = false;
    Usuario (Connection conexion, String correo, String claveAcceso) {
        try{
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
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    protected Usuario (Connection conexion) {}
    @Override
    public boolean actualizarDatos(JSONObject datos) {
        return false;
    }
    @Override
    public boolean baja() {
        return false;
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
