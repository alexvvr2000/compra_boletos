package com.megaboletos.pagos;
import com.megaboletos.usuarios.Cliente;
public class PagoMastercard implements MetodoPago{
    PagoMastercard(Cliente clienteAComprar) {
    }
    @Override
    public boolean pagar(int cantidadDinero, int CVV) {
        return false;
    }
}
