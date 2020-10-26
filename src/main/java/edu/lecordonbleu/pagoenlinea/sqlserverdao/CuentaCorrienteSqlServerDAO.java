/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.lecordonbleu.pagoenlinea.sqlserverdao;

import edu.lecordonbleu.pagoenlinea.dao.CuentaCorrienteDAO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author eduardo
 */
public class CuentaCorrienteSqlServerDAO implements CuentaCorrienteDAO {

    @Override
    public JSONArray listarCuentaCorrientexCodigo(String codigo, int carrera, String unidad) throws Exception {
        Connection cnx = null;
        ResultSet rs = null;

        if (unidad.equals("I")) {
            cnx = SqlServerDAOFactory.obtenerConexion("SGAILCB");
        } else if (unidad.equals("U")) {
            cnx = SqlServerDAOFactory.obtenerConexion("SGAULCB");
        }

        CallableStatement proc_stmt = cnx.prepareCall("{ call pro_pago_linea_01(?,?,'02') }");
        proc_stmt.setString(1, codigo);
        proc_stmt.setInt(2, carrera);

        rs = proc_stmt.executeQuery();

        JSONArray JSONArray = new JSONArray();

        while (rs.next()) {
            JSONObject jo = new JSONObject();

            jo.put("periodo", rs.getString("PA_SGA_CODIGO"));
            jo.put("nro_cuota", rs.getString("CCT_CUOTA_NRO"));
            jo.put("item", rs.getString("CCT_ITEM"));
            jo.put("prefactura", rs.getString("CCT_NRO_PREFACTURA"));
            jo.put("concepto", rs.getString("CCT_CPTO_NOMBRE"));
            jo.put("mes", rs.getString("CCT_MES_FEC_VENC"));
            jo.put("fecha_vencimiento", rs.getString("CCT_FORMATO_CUOTA_FEC_VENC"));
            jo.put("monto_inicial", rs.getString("CCT_CUOTA_MONT_INIC"));
            jo.put("monto_descuento", rs.getString("CCT_CUOTA_MONT_DESC"));
            jo.put("monto_pendiente", rs.getString("CCT_CUOTA_MONT_PEND"));

            jo.put("codigo_estudiante", rs.getString("ES_CODIGO"));
            jo.put("nombre_completo", rs.getString("ES_NOMBRE_COMPLETO"));
            jo.put("carrera", rs.getString("PR_NOMBRE"));

            jo.put("id_cct", rs.getString("CCT_CUOTA_ID"));

            JSONArray.put(jo);
        }

        cnx.close();
        return JSONArray;
    }

}
