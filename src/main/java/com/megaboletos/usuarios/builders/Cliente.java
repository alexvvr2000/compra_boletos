package com.megaboletos.usuarios.builders;
import com.megaboletos.usuarios.builders.Usuario;
import java.sql.Connection;
class Cliente extends Usuario{
    Cliente(Connection conexion, String correo, String claveAcceso) {
        super(conexion, correo, claveAcceso);
    }
}
