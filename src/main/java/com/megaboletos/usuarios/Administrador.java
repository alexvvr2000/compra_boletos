package com.megaboletos.usuarios;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Administrador extends Usuario{
    Administrador(Connection conexion, String correo, String claveAcceso){
        super(conexion, correo, claveAcceso);
    }
    public static boolean existeUsuario(Connection conexion, int idUsuario) throws SQLException {
        PreparedStatement query = conexion.prepareStatement(
                "select nombre as valor from usuario where idusuario = ?;"
        );
        query.setInt(1, idUsuario);
        ResultSet conjunto = query.executeQuery();
        return conjunto.next();
    }
    public static boolean esAdmin(Connection conexion, int idUsuario) throws Exception {
        return true;
    }
}
