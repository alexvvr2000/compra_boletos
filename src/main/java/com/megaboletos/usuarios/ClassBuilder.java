package com.megaboletos.usuarios;
public interface ClassBuilder<T>{
    T crear() throws Exception;
    boolean camposValidos();
}
