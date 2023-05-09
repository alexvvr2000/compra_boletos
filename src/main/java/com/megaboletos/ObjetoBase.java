package com.megaboletos;
import org.json.JSONObject;

import java.sql.SQLException;

public interface ObjetoBase {
    public boolean actualizarDatos(JSONObject datos) throws SQLException;
    public boolean baja() throws SQLException;
}
