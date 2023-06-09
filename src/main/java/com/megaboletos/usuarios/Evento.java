package com.megaboletos.usuarios;
import org.json.JSONObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
public class Evento {
    private String nombre = "";
    private String lugar = "";
    private Date fecha = null;
    private Connection conexion = null;
    private int idEvento = 0;
    public Evento(Connection conexion,int idEvento) throws Exception {
        if(!Evento.existeEvento(conexion,idEvento))
            throw new Exception("Evento no existe");
        PreparedStatement query = conexion.prepareStatement(
                "select nombre, lugar, fecha from evento where idevento = ?"
        );
        query.setInt(1, idEvento);
        ResultSet resultado = query.executeQuery();
        resultado.next();
        this.conexion = conexion;
        this.idEvento = idEvento;
        this.nombre = resultado.getString("nombre");
        this.fecha = resultado.getTimestamp("fecha");
        this.lugar = resultado.getString("lugar");
    }
    public String getNombre() {
        return nombre;
    }
    public String getLugar() {
        return lugar;
    }
    public Date getFecha() {
        return fecha;
    }
    public int getIdEvento() {
        return this.idEvento;
    }
    public boolean eventoCancelado() throws Exception{
        PreparedStatement query = this.conexion.prepareStatement(
            "select estacancelado from evento where idevento = ?;"
        );
        query.setInt(1, this.idEvento);
        ResultSet resultado = query.executeQuery();
        resultado.next();
        return resultado.getBoolean("estacancelado");
    }
    public Map<String, Object> asientosFila(String fila) throws Exception {
        if(!this.existeFila(fila)) throw new Exception("Fila no existe");
        PreparedStatement query = this.conexion.prepareStatement(
                "select filasdisponibles from capacidad where idevento = ?"
        );
        query.setInt(1, this.idEvento);
        ResultSet resultado = query.executeQuery();
        resultado.next();
        String jsonAsientos = resultado.getString("filasdisponibles");
        JSONObject filaSeleccionada = new JSONObject(jsonAsientos).getJSONObject(fila);
        Map<String, Object> asientos = filaSeleccionada.toMap();
        asientos.remove("precio");
        return Collections.unmodifiableMap(asientos);
    }
    public static boolean existeEvento(Connection conexion, int idEvento) throws Exception {
        PreparedStatement query = conexion.prepareStatement(
            "select exists(" +
                "select nombre from evento where idevento = ?" +
            ") as existe;"
        );
        query.setInt(1, idEvento);
        ResultSet conjunto = query.executeQuery();
        conjunto.next();
        return conjunto.getBoolean("existe");
    }
    public boolean estaDisponible(String fila, int asiento) throws Exception{
        Map<String, Object> filas = this.asientosFila(fila);
        return (boolean)filas.get(Integer.toString(asiento));
    }
    public Integer precioAsiento(String fila) throws Exception{
        PreparedStatement query = conexion.prepareStatement(
            "select " +
                "(filasDisponibles -> ? -> 'precio')::numeric as precio " +
            "from capacidad where idEvento = ?;"
        );
        query.setString(1, fila);
        query.setInt(2, this.idEvento);
        ResultSet conjunto = query.executeQuery();
        conjunto.next();
        return new Integer(conjunto.getInt("precio"));
    }
    public boolean existeAsiento (String fila, int asiento) throws Exception{
        PreparedStatement query = conexion.prepareStatement(
                "select " +
                        "(filasDisponibles -> ? -> 'precio')::numeric is not null " +
                        "and " +
                        "(filasDisponibles -> ? -> ?) is not null " +
                        "as existeAsiento " +
                "from capacidad where idEvento = ?;"
        );
        query.setString(1, fila);
        query.setString(2, fila);
        query.setString(3, Integer.toString(asiento));
        query.setInt(4, this.idEvento);
        ResultSet conjunto = query.executeQuery();
        conjunto.next();
        return conjunto.getBoolean("existeAsiento");
    }
    public boolean existeFila (String fila) throws Exception{
        PreparedStatement query = conexion.prepareStatement(
                "select " +
                        "(filasDisponibles -> ? -> 'precio')::numeric is not null " +
                        " as existeFila " +
                        "from capacidad where idEvento = ?;"
        );
        query.setString(1, fila);
        query.setInt(2,this.idEvento);
        ResultSet conjunto = query.executeQuery();
        conjunto.next();
        return conjunto.getBoolean("existeFila");
    }
    public static class EventoIterator implements Iterator<Evento> {
        private int cantidadValores = 0;
        private int valorActual = 0;
        private ResultSet clavesCrudas = null;
        private Connection conexion = null;
        public EventoIterator(Connection conexion) throws Exception {
            PreparedStatement query = conexion.prepareStatement(
                    "select " +
                    "cast(count(idEvento) as integer) as cantidad " +
                    "from evento;"
            );
            ResultSet resultado = query.executeQuery();
            resultado.next();
            this.cantidadValores = resultado.getInt("cantidad");
            if(this.cantidadValores == 0) throw new Exception("No hay eventos en base");
            PreparedStatement queryMetodosPago = conexion.prepareStatement(
                "select idEvento from evento"
            );
            this.clavesCrudas = queryMetodosPago.executeQuery();
            this.conexion = conexion;
        }
        @Override
        public boolean hasNext() {
            return this.cantidadValores != this.valorActual;
        }
        @Override
        public Evento next() {
            try{
                this.clavesCrudas.next();
                int idActual = this.clavesCrudas.getInt("idEvento");
                this.valorActual++;
                return new Evento(this.conexion ,idActual);
            } catch (Exception e) {
                System.out.println(e.getClass().getName() + ": " + e.getMessage());
            }
            return null;
        }
    }
}
