/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.lecordonbleu.pagoenlinea.dao;

import edu.lecordonbleu.pagoenlinea.sqlserverdao.SqlServerDAOFactory;


/**
 *
 * @author EduardoVicente
 */
public abstract class DAOFactory {

    public static final int MYSQL = 1;
    public static final int SQLSERVER = 2;
    public static final int ORACLE = 3;

    public static DAOFactory getDAOFactory(int bd) {
        switch (bd) {
            /*case ORACLE:
                return new OracleDAOFactory();
            case MYSQL:
                return new MySqlDAOFactory();*/
            case SQLSERVER:
                return new SqlServerDAOFactory();
            default:
                return null;
        }
    }

    public abstract CuentaCorrienteDAO getCuentaCorrienteDao();

    public abstract EstudianteDAO getEstudianteDao();
    
    public abstract FlywireDAO getFlywireDao();

}
