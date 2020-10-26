/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.lecordonbleu.pagoenlinea.dao;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author eduardo
 */
public interface EstudianteDAO {

    public long esEstudiante(String codigo, String unidad) throws Exception;

    public JSONObject datosEstudiante(String codigo, int carrera, String unidad) throws Exception;
}
