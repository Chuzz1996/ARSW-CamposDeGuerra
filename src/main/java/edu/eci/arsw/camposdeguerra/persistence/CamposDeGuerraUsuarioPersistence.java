/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.camposdeguerra.persistence;


import edu.eci.arsw.camposdeguerra.model.Usuario;
import java.util.Set;


public interface CamposDeGuerraUsuarioPersistence {
    
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
    
    /**
     * 
     * @param user
     * @param team
     * @throws CamposDeGuerraPersistenceException 
     */
    public void asignedTeam(String user, int team)throws CamposDeGuerraPersistenceException;
    
}
