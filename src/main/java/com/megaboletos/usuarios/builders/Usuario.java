package com.megaboletos.usuarios.builders;
import java.sql.Connection;
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
    Usuario (Connection conexion, String correo, String claveAcceso) {}
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
