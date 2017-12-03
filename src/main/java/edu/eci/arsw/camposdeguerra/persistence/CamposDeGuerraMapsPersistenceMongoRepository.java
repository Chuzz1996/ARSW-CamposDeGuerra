/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.camposdeguerra.persistence;


import edu.eci.arsw.camposdeguerra.model.Mapa;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;


@Service
public interface CamposDeGuerraMapsPersistenceMongoRepository extends MongoRepository<Mapa, String>{
    
    
    public Mapa findById(String id) throws CamposDeGuerraNotFoundException;   
    
    
    
}
