package com.megaboletos;
import org.json.JSONObject;
public interface ObjetoBase {
    public default boolean actualizarDatos(JSONObject datos) {
        return true;
    }
    public boolean baja();
}
