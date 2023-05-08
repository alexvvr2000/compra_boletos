package com.megaboletos.usuarios.builders;
import java.sql.Connection;
import org.json.JSONObject;
class Usuario {
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correo;
    private Connection conexionBase;
    private boolean estaBaneado = false;
    private boolean estaEliminado = false;
    private boolean sesionCerrada = false;
    Usuario (Connection conexion, String correo, String claveAcceso) {}
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
