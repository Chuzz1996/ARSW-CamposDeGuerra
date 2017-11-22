/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.camposdeguerra.persistence;


import edu.eci.arsw.camposdeguerra.model.Usuario;
import java.util.Set;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface CamposDeGuerraUsuarioPersistence{
    
    /**
     * 
     * @param bp
     * @throws CamposDeGuerraPersistenceException 
     */
    public void saveUsuario(Usuario bp) throws CamposDeGuerraPersistenceException;
    
    /**
     * 
     * @param user
     * @return
     * @throws CamposDeGuerraNotFoundException 
     */
    public Usuario getUsuario(String user) throws CamposDeGuerraNotFoundException;
    
    /**
     * 
     * @return
     * @throws CamposDeGuerraNotFoundException 
     */
    public Set<Usuario> getAllUsuarios() throws CamposDeGuerraNotFoundException;
    
    /**
     * 
     * @param u
     * @throws CamposDeGuerraPersistenceException 
     */
    public void updateUsuario(Usuario u) throws CamposDeGuerraPersistenceException;
    
    /**
     * 
     * @param user
     * @throws CamposDeGuerraPersistenceException 
     */
    public void deleteUsuario(String user) throws CamposDeGuerraPersistenceException;
    
}
