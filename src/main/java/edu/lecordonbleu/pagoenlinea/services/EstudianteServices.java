/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.lecordonbleu.pagoenlinea.services;


import edu.lecordonbleu.pagoenlinea.dao.DAOFactory;
import edu.lecordonbleu.pagoenlinea.dao.EstudianteDAO;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author eduardo
 */
public class EstudianteServices {

    DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.SQLSERVER);
    EstudianteDAO DAO = factory.getEstudianteDao();

    public long esEstudiante(String codigo, String unidad) {

        long id = 0;
        try {
            id = DAO.esEstudiante(codigo, unidad);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return id;

    }

    public JSONObject datosEstudiante(String codigo, int carrera, String unidad) {
        JSONObject JSONObject = new JSONObject();
        try {
            JSONObject = DAO.datosEstudiante(codigo, carrera, unidad);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return JSONObject;
    }

}
