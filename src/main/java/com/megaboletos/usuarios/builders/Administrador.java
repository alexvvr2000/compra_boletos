package com.megaboletos.usuarios.builders;
import java.sql.Connection;
public class Administrador extends Usuario{
    Administrador(Connection conexion, String correo, String claveAcceso){
        super(conexion, correo, claveAcceso);
    }
}
