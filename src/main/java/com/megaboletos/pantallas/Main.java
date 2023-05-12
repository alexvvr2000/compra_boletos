package com.megaboletos.pantallas;
import com.megaboletos.usuarios.Cliente;
import java.util.Scanner;
public class Main {
    private static Cliente nuevaSesion(){
        return null;
    }
    private static Cliente Registrarse(){
        return null;
    }
    public static void main(String[] args) {
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
            switch (opcionSeleccionada) {
                case 1:
                    usuario = Main.nuevaSesion();
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
        } while (true);
    }
}
