/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.lecordonbleu.pagoenlinea.servlets;

import edu.lecordonbleu.pagoenlinea.services.EstudianteServices;
import edu.lecordonbleu.pagoenlinea.services.FlywireServices;
import edu.lecordonbleu.pagoenlinea.utils.Codificador;
import edu.lecordonbleu.pagoenlinea.utils.Tokens;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 *
 * @author Eduardo
 */
public class ValidacionServlet extends HttpServlet {

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion.equals("validarVariables")) {
            validarVariables(request, response);
        } else if (accion.equals("validarRespuesta")) {
            validarRespuesta(request, response);
        }

    }

    private void validarVariables(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Codificador codificador = new Codificador();

        String codigo = codificador.decode(request.getParameter("txt_v1"));
        int carrera = Integer.parseInt(codificador.decode(request.getParameter("txt_v2")));
        String periodo = codificador.decode(request.getParameter("txt_v3"));
        String unidad = codificador.decode(request.getParameter("txt_v4"));

        EstudianteServices servicio = new EstudianteServices();

        long id = 0;

        id = servicio.esEstudiante(codigo, unidad);

        JSONObject JSONObject = new JSONObject();
        if (id == 0) {
            JSONObject.put("existe", 0);
        } else {
            Tokens token = new Tokens();

            String generado = token.generaToken();

            JSONObject.put("existe", 1);
            JSONObject.put("codigo", codigo);
            JSONObject.put("carrera", carrera);
            JSONObject.put("periodo", periodo);
            JSONObject.put("unidad", unidad);
            JSONObject.put("token", generado);
        }

        response.setContentType("application/json");
        PrintWriter pw = response.getWriter();
        pw.println(JSONObject);

    }

    private void validarRespuesta(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);

        Codificador codificador = new Codificador();
        String concatenado = codificador.decode(request.getParameter("txt_v1"));
        String idLEP = request.getParameter("txt_v2");
        String estado = request.getParameter("txt_v3");
        String data = request.getParameter("txt_data");

        JSONObject jData = new JSONObject(data);

        String[] parts = concatenado.split("#");

        FlywireServices servicio = new FlywireServices();

        for (int a = 0; a < parts.length; a++) {
            int idPreMatricula = Integer.parseInt(parts[a]);

            servicio.registraHistorico(jData.getString("codigo"), idPreMatricula, idLEP, estado, jData.getString("unidad"));

        }

        JSONObject JSONObject = new JSONObject();

        if (estado.equals("success")) {
            JSONObject.put("estado", 1);
        } else if (estado.equals("initiated")) {
            JSONObject.put("estado", 2);
        } else if (estado.equals("error")) {
            JSONObject.put("estado", 0);
        }

        JSONObject.put("existe", 1);
        response.setContentType("application/json");
        PrintWriter pw = response.getWriter();
        pw.println(JSONObject);

    }

}
