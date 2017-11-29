/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.camposdeguerra.persistence;


import edu.eci.arsw.camposdeguerra.model.Usuario;
import java.util.Set;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


public interface CamposDeGuerraUsuarioPersistenceMongoRepository extends MongoRepository<Usuario, String>{
    
    
    public Usuario findById(String id) throws CamposDeGuerraNotFoundException;   
    
    @Query("{}")
    public Set<Usuario> getAllUsers() throws CamposDeGuerraNotFoundException;
    
}
