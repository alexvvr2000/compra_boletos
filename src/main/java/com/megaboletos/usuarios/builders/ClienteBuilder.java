package com.megaboletos.usuarios.builders;
import com.megaboletos.usuarios.PermisoUsuario;
import java.sql.Connection;
public class ClienteBuilder {
}
class Cliente extends Usuario implements PermisoUsuario {
    Cliente(Connection conexion, String correo, String claveAcceso) {
        super(conexion, correo, claveAcceso);
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
}
