package com.megaboletos.usuarios.builders;
import com.megaboletos.usuarios.PermisoUsuario;
import com.megaboletos.usuarios.builders.Usuario;
import java.sql.Connection;
public class Administrador extends Usuario implements PermisoUsuario{
    Administrador(Connection conexion, String correo, String claveAcceso){
        super(conexion, correo, claveAcceso);
    }
    @Override
    public Permiso obtenerPermisosUsuario() {
        return Permiso.ADMIN;
    }
}
