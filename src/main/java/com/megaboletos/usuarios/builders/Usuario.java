package com.megaboletos.usuarios.builders;
import java.sql.Connection;
import org.json.JSONObject;
class Usuario {
    String nombre;
    String apellidoPaterno;
    String apellidoMaterno;
    String correo;
    Connection conexionBase;
    boolean estaBaneado = false;
    boolean estaEliminado = false;
    boolean sesionCerrada = false;
    Usuario (Connection conexion, String correo, String claveAcceso) {}
    Usuario (Connection conexion) {}
    public boolean sincronizarConBase() {
        return true;
    }
    public boolean actualizarDatos(JSONObject datos) {
        return false;
    }
    public boolean bajaCuenta() {
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
