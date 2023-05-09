package com.megaboletos;
import com.megaboletos.usuarios.builders.Cliente;

import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    public static void main(String args[]) {
        Connection conexion = null;
        Cliente cliente = null;
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/megaboletos",
                    "postgres",
                    "root"
            );
            cliente = new Cliente(conexion, "alejandro@gmail.com", "1234");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.printf(cliente.getApellidoPaterno());
    }
}