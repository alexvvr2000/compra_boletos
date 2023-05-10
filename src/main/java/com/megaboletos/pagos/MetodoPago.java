package com.megaboletos.pagos;

import com.megaboletos.Compra;

public interface MetodoPago {
    boolean pagar(int idCompra, int CVV);
}
