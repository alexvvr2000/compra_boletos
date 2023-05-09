package com.megaboletos.usuarios;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Administrador extends Usuario{
    Administrador(Connection conexion, String correo, String claveAcceso) throws Exception{
        super(conexion, correo, claveAcceso);
    }
    public static boolean existeUsuario(Connection conexion, int idUsuario) throws Exception {
        PreparedStatement query = conexion.prepareStatement(
                "select exists(" +
                        "select nombre from usuario where idusuario = ?" +
                ") as existe;"
        );
        query.setInt(1, idUsuario);
        ResultSet conjunto = query.executeQuery();
        conjunto.next();
        return conjunto.getBoolean("existe");
    }
    public static boolean esAdmin(Connection conexion, int idUsuario) throws Exception {
        if(!Administrador.existeUsuario(conexion, idUsuario)) {
            throw new Exception("No existe en base");
        }
        PreparedStatement query = conexion.prepareStatement(
                "select esadmin from usuario where idusuario = ?;"
        );
        query.setInt(1, idUsuario);
        ResultSet conjunto = query.executeQuery();
        conjunto.next();
        return conjunto.getBoolean("esadmin");
    }
}
