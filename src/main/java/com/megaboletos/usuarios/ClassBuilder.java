package com.megaboletos.usuarios;

public interface ClassBuilder<T> {
    T crear();
    boolean camposValidos();
}
