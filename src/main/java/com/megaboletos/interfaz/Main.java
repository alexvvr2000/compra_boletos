package com.megaboletos.interfaz;
import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    public static void main(String args[]) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection conexion = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/megaboletos",
                    "postgres",
                    "root"
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}