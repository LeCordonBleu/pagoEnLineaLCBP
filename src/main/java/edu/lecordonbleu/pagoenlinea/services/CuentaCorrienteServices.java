/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.lecordonbleu.pagoenlinea.services;


import edu.lecordonbleu.pagoenlinea.dao.CuentaCorrienteDAO;
import edu.lecordonbleu.pagoenlinea.dao.DAOFactory;
import org.json.JSONArray;

/**
 *
 * @author eduardo
 */
public class CuentaCorrienteServices {

    DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.SQLSERVER);
    CuentaCorrienteDAO DAO = factory.getCuentaCorrienteDao();

    public JSONArray listarCuentaCorrientexCodigo(String codigo, int carrera, String unidad) {
        JSONArray JSONArray = new JSONArray();
        try {
            JSONArray = DAO.listarCuentaCorrientexCodigo(codigo, carrera, unidad);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return JSONArray;
    }

}
