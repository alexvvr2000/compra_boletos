package com.megaboletos.usuarios.builders;
import com.megaboletos.usuarios.PermisoUsuario;
import java.sql.Connection;
public class Cliente extends Usuario implements PermisoUsuario {
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
        public Builder setNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }
        public Builder setApellidoPaterno(String apellidoPaterno) {
            this.apellidoPaterno = apellidoPaterno;
            return this;
        }
        public Builder setApellidoMaterno(String apellidoMaterno) {
            this.apellidoMaterno = apellidoMaterno;
            return this;
        }
        public Builder setCorreo(String correo) {
            this.correo = correo;
            return this;
        }
        public Builder setConexion(Connection conexion) {
            this.conexion = conexion;
            return this;
        }
        public Cliente crear(String claveAcceso) {
            return new Cliente(this);
        }
    }
    private Cliente(Builder instancia){
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
}
