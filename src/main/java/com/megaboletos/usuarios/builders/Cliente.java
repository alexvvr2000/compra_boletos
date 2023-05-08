package com.megaboletos.usuarios.builders;
import com.megaboletos.usuarios.PermisoUsuario;
import java.sql.Connection;
public class Cliente extends Usuario implements PermisoUsuario {
    private Cliente(final Builder instancia){
        super(instancia.conexion);
        this.nombre = instancia.nombre;
        this.apellidoPaterno = instancia.apellidoPaterno;
        this.apellidoMaterno = instancia.apellidoMaterno;
        this.correo = instancia.correo;
        this.conexionBase = instancia.conexion;
    }
    public Cliente(Connection connection, String correo, String claveAcceso) {
        super(connection, correo, claveAcceso);
    }
    public boolean modificarMetodoPago(int idMetodoPago) {
        return false;
    }
    public boolean eliminarMetodoPago(int idMetodoPago) {
        return false;
    }
    public PermisoUsuario.Permiso obtenerPermisosUsuario() {
        return Permiso.CLIENTE;
    }
    public static class Builder {
        private String nombre;
        private String apellidoPaterno;
        private String apellidoMaterno;
        private String correo;
        private Connection conexion;
        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
        public void setApellidoPaterno(String apellidoPaterno) {
            this.apellidoPaterno = apellidoPaterno;
        }
        public void setApellidoMaterno(String apellidoMaterno) {
            this.apellidoMaterno = apellidoMaterno;
        }
        public void setCorreo(String correo) {
            this.correo = correo;
        }
        public void setConexion(Connection conexion) {
            this.conexion = conexion;
        }
        public Cliente crear(String claveAcceso) {
            return new Cliente(this);
        }
    }
}
