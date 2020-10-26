/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.lecordonbleu.pagoenlinea.sqlserverdao;


import edu.lecordonbleu.pagoenlinea.dao.EstudianteDAO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author eduardo
 */
public class EstudianteSqlServerDAO implements EstudianteDAO {

    @Override
    public long esEstudiante(String codigo, String unidad) throws Exception {
        Connection cnx = null;
        PreparedStatement preparedStatement = null;

        String selectSQL = "SELECT id_alumno FROM AI_AlumnoSeguridad WHERE usuario=?;";

        long id = 0;

        try {
            if (unidad.equals("I")) {
                cnx = SqlServerDAOFactory.obtenerConexion("SGAILCB");
            } else if (unidad.equals("U")) {
                cnx = SqlServerDAOFactory.obtenerConexion("SGAULCB");
            }

            preparedStatement = cnx.prepareStatement(selectSQL);

            preparedStatement.setString(1, codigo);

            // execute select SQL stetement
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                id = Long.parseLong(rs.getString("id_alumno"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            id = 0;
        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (cnx != null) {
                cnx.close();
            }
        }

        return id;
    }

    @Override
    public JSONObject datosEstudiante(String codigo, int carrera, String unidad) throws Exception {
        Connection cnx = null;
        ResultSet rs = null;

        if (unidad.equals("I")) {
            cnx = SqlServerDAOFactory.obtenerConexion("SGAILCB");
        } else if (unidad.equals("U")) {
            cnx = SqlServerDAOFactory.obtenerConexion("SGAULCB");
        }

        CallableStatement proc_stmt = cnx.prepareCall("{ call pro_pago_linea_01(?,?,'01') }");
        proc_stmt.setString(1, codigo);
        proc_stmt.setInt(2, carrera);

        rs = proc_stmt.executeQuery();

        JSONObject jo = new JSONObject();

        while (rs.next()) {

            jo.put("codigo", rs.getString("ES_CODIGO"));
            jo.put("paterno", rs.getString("ES_APE_PATERNO"));
            jo.put("materno", rs.getString("ES_APE_MATERNO"));
            jo.put("nombre", rs.getString("ES_NOMBRE"));
            jo.put("tipo_documento", rs.getString("ES_TIPO_DOCUMENTO"));
            jo.put("nro_documento", rs.getString("ES_NRO_DOCUMENTO"));
            jo.put("nombreCompleto", rs.getString("ES_NOMBRE_COMPLETO"));
            jo.put("carrera", rs.getString("PR_NOMBRE"));
            jo.put("correo", rs.getString("ES_EMAIL_INSTITUCIONAL"));
            jo.put("telefono", rs.getString("ES_TELEF_CELULAR"));
            jo.put("direccion", rs.getString("ES_DIRECCION"));

        }

        cnx.close();
        return jo;
    }

}
