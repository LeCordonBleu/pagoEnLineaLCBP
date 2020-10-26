/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.lecordonbleu.pagoenlinea.services;

import edu.lecordonbleu.pagoenlinea.dao.DAOFactory;
import edu.lecordonbleu.pagoenlinea.dao.FlywireDAO;



/**
 *
 * @author eduardo
 */
public class FlywireServices {

    DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.SQLSERVER);
    FlywireDAO DAO = factory.getFlywireDao();

    public void registraHistorico(String codigo, int idPrematricula, String identificador, String estado, String unidad) {
        try {
            DAO.registraHistorico(codigo, idPrematricula, identificador, estado, unidad);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
