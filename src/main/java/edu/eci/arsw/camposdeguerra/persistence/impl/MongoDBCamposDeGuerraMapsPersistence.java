/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.camposdeguerra.persistence.impl;


import edu.eci.arsw.camposdeguerra.logic.leerMapas;
import edu.eci.arsw.camposdeguerra.model.Mapa;
import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraMapsPersistence;
import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraNotFoundException;
import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraMapsPersistenceMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MongoDBCamposDeGuerraMapsPersistence implements CamposDeGuerraMapsPersistence {
    
    @Autowired CamposDeGuerraMapsPersistenceMongoRepository me;
    
    public MongoDBCamposDeGuerraMapsPersistence() {
    }

    @Override
    public String[][] findMap(String id) throws CamposDeGuerraNotFoundException {
        return leerMapas.muestraContenido(me.findById(id));
    }

    @Override
    public void saveMapa(Mapa m) throws CamposDeGuerraNotFoundException {
        me.save(m);
    }
    
    
}
