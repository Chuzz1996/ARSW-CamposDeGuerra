/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.camposdeguerra.persistence;

import edu.eci.arsw.camposdeguerra.model.Mapa;

/**
 *
 * @author 2107713
 */
public interface CamposDeGuerraMapsPersistence {
    
    public String[][] findMap(String id) throws CamposDeGuerraNotFoundException;
    public void saveMapa(Mapa m) throws CamposDeGuerraNotFoundException;
    
}
