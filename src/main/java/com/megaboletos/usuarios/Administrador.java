package com.megaboletos.usuarios;
import java.sql.Connection;
public class Administrador extends Usuario{
    Administrador(Connection conexion, String correo, String claveAcceso){
        super(conexion, correo, claveAcceso);
    }
}
