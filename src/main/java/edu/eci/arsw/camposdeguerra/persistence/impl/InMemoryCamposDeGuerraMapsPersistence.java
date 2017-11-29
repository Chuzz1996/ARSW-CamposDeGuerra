/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.camposdeguerra.persistence.impl;

import edu.eci.arsw.camposdeguerra.logic.leerMapas;
import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraMapsPersistence;
import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraNotFoundException;

/**
 *
 * @author 2107713
 */
public class InMemoryCamposDeGuerraMapsPersistence implements CamposDeGuerraMapsPersistence{

    @Override
    public String[][] findMap(String id) throws CamposDeGuerraNotFoundException {
        return leerMapas.muestraContenido(id);
    }
}
