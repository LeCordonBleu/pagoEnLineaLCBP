/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.lecordonbleu.pagoenlinea.sqlserverdao;

import edu.lecordonbleu.pagoenlinea.dao.FlywireDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author eduardo
 */
public class FlywireSqlServerDAO implements FlywireDAO {

    @Override
    public void registraHistorico(String codigo, int idPrematricula, String identificador, String estado, String unidad) throws Exception {
        Connection cnx = null;
        PreparedStatement preparedStatement = null;

        String selectSQL = "INSERT INTO flyware_historico (codigo, id_prematricula_cuota, identificador,estado,fecha)\n" +
                            " VALUES (?, ?, ?,?,GETDATE()); ";

        try {
            if (unidad.equals("I")) {
                cnx = SqlServerDAOFactory.obtenerConexion("SGAILCB");
            } else if (unidad.equals("U")) {
                cnx = SqlServerDAOFactory.obtenerConexion("SGAULCB");
            }

            preparedStatement = cnx.prepareStatement(selectSQL);

            preparedStatement.setString(1, codigo);
            preparedStatement.setInt(2, idPrematricula);
            preparedStatement.setString(3, identificador);
            preparedStatement.setString(4, estado);
            

            // execute select SQL stetement
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (cnx != null) {
                cnx.close();
            }
        }

    }
    
}
