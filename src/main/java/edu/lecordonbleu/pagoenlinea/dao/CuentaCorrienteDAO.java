/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.lecordonbleu.pagoenlinea.dao;

import org.json.JSONArray;

/**
 *
 * @author eduardo
 */
public interface CuentaCorrienteDAO {

    public JSONArray listarCuentaCorrientexCodigo(String codigo, int carrera, String unidad) throws Exception;

}
