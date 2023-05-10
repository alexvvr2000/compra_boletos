package com.megaboletos.pagos;
import com.megaboletos.Compra;
import com.megaboletos.usuarios.Cliente;
public class PagoVisa implements MetodoPago{
    public PagoVisa(Cliente clienteAComprar) {
    }
    @Override
    public boolean pagar(int idCompra, int CVV) {
        return false;
    }
}
