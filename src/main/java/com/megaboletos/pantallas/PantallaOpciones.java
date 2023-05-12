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
                        PantallaOpciones.metodoPago(entrada, cliente);
                        break;
                    case 2:
                        PantallaOpciones.compras(entrada, cliente);
                        break;
                    case 3:
                        PantallaOpciones.eventos(entrada);
                        break;
                    case 4:
                        continue;
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
    private static void metodoPago(Scanner entrada, Cliente cliente) throws Exception{

    }
    private static void compras(Scanner entrada, Cliente cliente) throws Exception{

    }
    private static void eventos(Scanner entrada) throws Exception{

    }
}
