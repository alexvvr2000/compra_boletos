package com.megaboletos;
import org.json.JSONObject;

import java.sql.SQLException;

public interface ObjetoBase {
    public void actualizarDatos(JSONObject datos) throws Exception;
    public void baja() throws Exception;
}
