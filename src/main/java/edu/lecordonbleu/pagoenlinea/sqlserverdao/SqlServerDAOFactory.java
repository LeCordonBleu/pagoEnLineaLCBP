package edu.lecordonbleu.pagoenlinea.sqlserverdao;

import edu.lecordonbleu.pagoenlinea.dao.CuentaCorrienteDAO;
import edu.lecordonbleu.pagoenlinea.dao.DAOFactory;
import edu.lecordonbleu.pagoenlinea.dao.EstudianteDAO;
import edu.lecordonbleu.pagoenlinea.dao.FlywireDAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author EduardoVicente
 */
public class SqlServerDAOFactory extends DAOFactory {

    public static Connection obtenerConexion(String nombre_bd) throws SQLException {
        Connection conexion = null;
        String user, pwd, url;

        // realizar conexión de acuerdo al nombre de la base de datos
        if (nombre_bd.equals("SGAILCB")) {
            try {
                Class.forName("net.sourceforge.jtds.jdbc.Driver");

                user = "4dm1nSG4lcb";
                pwd = "GX0Ug*BQL%";
                url = "jdbc:jtds:sqlserver://lcbpdb.eastus2.cloudapp.azure.com/SGAILCB";

//                user = "developer";
//                pwd = "D3v3L0p3r#195";
//                url = "jdbc:jtds:sqlserver://10.10.100.140/SGAILCB";
                conexion = DriverManager.getConnection(url, user, pwd);

                if (conexion != null) {
                    System.out.println("Connection Successful!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error Trace in getConnection() : " + e.getMessage());
            }
        } else if (nombre_bd.equals("SGAULCB")) {
            try {
                Class.forName("net.sourceforge.jtds.jdbc.Driver");

                //user = "4dm1nSG4lcb";
                //pwd = "GX0Ug*BQL%";
                //url = "jdbc:jtds:sqlserver://lcbpdb.eastus2.cloudapp.azure.com/SGAULCB";
                user = "4dm1nSG4lcb";
                pwd = "GX0Ug*BQL%";
                url = "jdbc:jtds:sqlserver://lcbpdb.eastus2.cloudapp.azure.com/SGAULCB051020";
                conexion = DriverManager.getConnection(url, user, pwd);

                if (conexion != null) {
                    System.out.println("Connection Successful!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error Trace in getConnection() : " + e.getMessage());
            }
        } else if (nombre_bd.equals("LCBBI")) {
            try {

                //Class.forName("net.sourceforge.jtds.jdbc.Driver");
                //user = "sa";
                //pwd = "Lcb2k18+";
                //url = "jdbc:jtds:sqlserver://lcbpdb.eastus2.cloudapp.azure.com/LCBBI";
                Class.forName("net.sourceforge.jtds.jdbc.Driver");
                user = "developer";
                pwd = "D3v3L0p3r#195";
                url = "jdbc:jtds:sqlserver://10.10.100.140/LCBBI";

                conexion = DriverManager.getConnection(url, user, pwd);

                if (conexion != null) {
                    System.out.println("Connection Successful!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error Trace in getConnection() : " + e.getMessage());
            }
        } else if (nombre_bd.equals("LCBCENTRAL")) {
            try {

                Class.forName("net.sourceforge.jtds.jdbc.Driver");
                user = "4dm1nSG4lcb";
                pwd = "GX0Ug*BQL%";
                url = "jdbc:jtds:sqlserver://lcbpdb.eastus2.cloudapp.azure.com/LCBCENTRAL";
                conexion = DriverManager.getConnection(url, user, pwd);

                /*    
                Class.forName("net.sourceforge.jtds.jdbc.Driver");
                user = "developer";
                pwd = "D3v3L0p3r#195";
                url = "jdbc:jtds:sqlserver://10.10.100.140/LCBCENTRAL";
                conexion = DriverManager.getConnection(url, user, pwd);*/
                if (conexion != null) {
                    System.out.println("Connection Successful!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error Trace in getConnection() : " + e.getMessage());
            }
        }
        return conexion;
    }

    @Override
    public CuentaCorrienteDAO getCuentaCorrienteDao() {
        return new CuentaCorrienteSqlServerDAO();
    }

    @Override
    public EstudianteDAO getEstudianteDao() {
        return new EstudianteSqlServerDAO();
    }

    @Override
    public FlywireDAO getFlywireDao() {
        return new FlywireSqlServerDAO();
    }
}
