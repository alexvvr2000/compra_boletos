package com.megaboletos.usuarios.builders;
import java.sql.*;

import com.megaboletos.usuarios.ObjetoBase;
import org.json.JSONObject;
class Usuario implements ObjetoBase {
    String nombre;
    String apellidoPaterno;
    String apellidoMaterno;
    String correo;
    Connection conexionBase;
    boolean estaBaneado = false;
    boolean estaEliminado = false;
    boolean sesionCerrada = false;
    Usuario (Connection conexion, String correo, String claveAcceso) {
        try{
            PreparedStatement query = conexion.prepareStatement(
                    "select nombre, apellidopaterno, apellidomaterno \n" +
                            "from usuario\n" +
                            "where claveiniciosesion = ? and correo = ?;"
            );
            query.setString(1, claveAcceso);
            query.setString(2, correo);
            ResultSet resultado = query.executeQuery();
            if(resultado.next()) {
                throw new Exception("No existe usuario");
            }
            this.correo = correo;
            this.nombre = resultado.getString("nombre");
            this.apellidoPaterno = resultado.getString("apellidopaterno");
            this.apellidoMaterno = resultado.getString("apellidomaterno");
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    Usuario (Connection conexion) {}
    @Override
    public boolean sincronizarConBase() {
        return true;
    }
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
        return nombre;
    }
    public String getApellidoPaterno() {
        return apellidoPaterno;
    }
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }
    public String getCorreo() {
        return correo;
    }
}
