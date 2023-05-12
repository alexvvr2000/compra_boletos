package com.megaboletos.pantallas;
import com.megaboletos.usuarios.Cliente;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;
public class Main {
    private static Cliente nuevaSesion(Scanner entrada, Connection conexion) throws Exception{
        System.out.print("Introduce un correo: ");
        final String correo = entrada.next();
        System.out.print("Introduce la contraseña");
        final String clave = entrada.next();
        return new Cliente(conexion, correo, clave);
    }
    private static Cliente Registrarse(){
        return null;
    }
    public static void main(String[] args) {
        Connection conexion = null;
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/megaboletos",
                    "postgres",
                    "root"
            );
            conexion.setAutoCommit(false);
        }catch (Exception e) {
            System.out.println("No se pudo conectar a base");
        }
        do {
            System.out.print(
                    "******** BIENVENIDO A MEGABOLETOS ****** \n" +
                            "\t 1. Iniciar sesion \n" +
                            "\t 2. Registrarse \n" +
                            "\t 3. Salir del programa \n" +
                            "Introduce un numero: "
            );
            Scanner entrada = new Scanner(System.in);
            int opcionSeleccionada = entrada.nextInt();
            if (opcionSeleccionada == 3) break;
            Cliente usuario = null;
            try {
                switch (opcionSeleccionada) {
                    case 1:
                        usuario = Main.nuevaSesion(entrada, conexion);
                        break;
                    case 2:
                        usuario = Main.Registrarse();
                        break;
                    default:
                        System.out.println(
                            "Valor introducido invalido"
                    );
                    break;
                }
            } catch (Exception e) {
                System.out.println(e.getClass().getName() + ": " + e.getMessage());
            }
        } while (true);
    }
}
