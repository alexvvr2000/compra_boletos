package com.megaboletos;
import org.json.JSONObject;

import java.sql.SQLException;

public interface ObjetoBase {
    public boolean actualizarDatos(JSONObject datos) throws Exception;
    public boolean baja() throws Exception;
}
