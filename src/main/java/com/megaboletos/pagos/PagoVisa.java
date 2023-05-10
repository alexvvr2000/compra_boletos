package com.megaboletos.pagos;
import com.megaboletos.usuarios.Cliente;
public class PagoVisa implements MetodoPago{
    PagoVisa(Cliente clienteAComprar) {
    }
    @Override
    public boolean pagar(int cantidadDinero, int CVV) {
        return false;
    }
}
