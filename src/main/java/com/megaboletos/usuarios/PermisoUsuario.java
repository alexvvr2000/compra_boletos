package com.megaboletos.usuarios;
public interface PermisoUsuario {
    public enum Permiso {
        ADMIN,
        CLIENTE
    }
    Permiso obtenerPermisosUsuario();
}
