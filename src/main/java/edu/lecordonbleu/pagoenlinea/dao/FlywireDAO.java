/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.lecordonbleu.pagoenlinea.dao;

/**
 *
 * @author eduardo
 */
public interface FlywireDAO {

    public void registraHistorico(String codigo, int idPrematricula, String identificador, String estado, String unidad) throws Exception;
}
