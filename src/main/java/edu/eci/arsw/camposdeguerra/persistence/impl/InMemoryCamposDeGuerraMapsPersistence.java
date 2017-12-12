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
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

/**
 *
 * @author 2107713
 */
//@Service
public class InMemoryCamposDeGuerraMapsPersistence implements CamposDeGuerraMapsPersistence{
    
    private final ConcurrentHashMap<String, Mapa> mapas = new ConcurrentHashMap<>();
    
    String a="20 33 I I I I I I I I I I I I I I I I I I I I I I I I I I I I I I I I I I . . . . . . . . . . . . . . P P P . . . . . . . . . . . . . . I I . . M . M . . . . . . . . . P P P . . . . . . . P P P . . . . I I . . M M M . . . . . . . . . . . . . . . . . . . P P P . . . . I I . . M . M . . . . M L . . . . . . . . . . . . . P . . . . . . I I . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . I I . . . . . . . L L . . . . . M M M . . . . . . . . . . . . . . I I . . . . . . . L L . . . . . M M M . . . . . . . . . . . . . . I I L L L . . . . . . . . . . . M M M . . . . . . . . . . . L L L I I L A L . . . . . . . . . . . M M M . . . . . . . . . . . L R L I I L L L . . . . . . . . . . . M M M . . . . . . . . . . . L L L I I . . . . . . . . . . . . . . M M M . . . . P P . . . . . . . . I I . . . . . . . . . . . . . . M M M . . . . M M . . . . . . . . I I . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . I I . . M P L . . . . . . . . . . . . . . . . . . L . . . . . . . I I . . . . P . . . . . . . . . . . . . . . . . . L . . . . . . . I I . . M P L . . . . . . . . . P P P . . . . . . L L L . . . . . I I . . . . . . . . . . . . . . P P P . . . . . . . . . . . . . . I I . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . I I I I I I I I I I I I I I I I I I I I I I I I I I I I I I I I I I";
    public InMemoryCamposDeGuerraMapsPersistence(){
        mapas.putIfAbsent("mapa1", new Mapa("mapa1",a));
    }
    
    @Override
    public String[][] findMap(String id) throws CamposDeGuerraNotFoundException {
        return leerMapas.muestraContenido(mapas.get(id));
    }
    
    @Override
    public void saveMapa(Mapa m) throws CamposDeGuerraNotFoundException {
        mapas.putIfAbsent(m.getId(), m);
    }
}
