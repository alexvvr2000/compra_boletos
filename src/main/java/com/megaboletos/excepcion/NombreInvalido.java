package com.megaboletos.usuarios.excepcion;

public class NombreInvalido extends Exception{
    NombreInvalido() {}
    public NombreInvalido(String mensaje) {
        super(mensaje);
    }
}
