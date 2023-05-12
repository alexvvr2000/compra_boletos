package com.megaboletos.pantallas;
import com.megaboletos.usuarios.Cliente;

import java.util.Scanner;

public class PantallaOpciones {
    public static void opciones(Cliente cliente) {
        int opcionSeleccionada = 0;
        do {
            try {
                System.out.println(
                    String.format(
                        "**** Bienvenido %s selecciona una opcion **** \n"
                        , cliente.getNombre()
                    ) +
                    "\t 1. Ver metodos de pago \n" +
                    "\t 2. Ver compras \n" +
                    "\t 3. Ver eventos disponibles \n" +
                    "\n 4. Salir de este menu \n"
                );
                Scanner entrada = new Scanner(System.in);
                opcionSeleccionada = entrada.nextInt();
                switch (opcionSeleccionada) {
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
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
        } while (opcionSeleccionada == 4);
    }
}
