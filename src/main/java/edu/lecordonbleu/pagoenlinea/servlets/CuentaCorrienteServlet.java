/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.lecordonbleu.pagoenlinea.servlets;

import edu.lecordonbleu.pagoenlinea.services.CuentaCorrienteServices;
import edu.lecordonbleu.pagoenlinea.services.EstudianteServices;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Eduardo
 */
public class CuentaCorrienteServlet extends HttpServlet {

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion.equals("listarCuentaCorriente")) {
            listarCuentaCorriente(request, response);
        }

    }

    private void listarCuentaCorriente(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);
        String data = request.getParameter("txt_data");

        JSONObject jData = new JSONObject(data);

        EstudianteServices servicioEstudiante = new EstudianteServices();
        JSONObject JEstudiante = new JSONObject();
        JEstudiante = servicioEstudiante.datosEstudiante(jData.getString("codigo"), jData.getInt("carrera"), jData.getString("unidad"));

        CuentaCorrienteServices servicioCuentaCorriente = new CuentaCorrienteServices();
        JSONArray JArrayCuentaCorriente = new JSONArray();
        JArrayCuentaCorriente = servicioCuentaCorriente.listarCuentaCorrientexCodigo(jData.getString("codigo"), jData.getInt("carrera"), jData.getString("unidad"));

        JSONObject JObject = new JSONObject();
        JObject.put("estudiante", JEstudiante);
        JObject.put("cuentaCorriente", JArrayCuentaCorriente);

        response.setContentType("application/json");
        PrintWriter pw = response.getWriter();
        pw.println(JObject);
    }

}
