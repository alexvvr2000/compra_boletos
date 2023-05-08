package com.megaboletos.usuarios;
import org.json.JSONObject;
public interface ObjetoBase {
    public boolean sincronizarConBase();
    public boolean actualizarDatos(JSONObject datos);
    public boolean baja();
}
