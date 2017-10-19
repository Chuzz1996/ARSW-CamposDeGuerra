/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.camposdeguerra.persistence;


import edu.eci.arsw.camposdeguerra.model.Usuario;
import java.util.Set;


public interface CamposDeGuerraRoomPersistence {
    
    
    /**
     * 
     * @return
     * @throws CamposDeGuerraNotFoundException 
     */
    public Integer getRoomFree() throws CamposDeGuerraNotFoundException;
    
    
    /**
     * 
     * @param us
     * @param room 
     * @throws CamposDeGuerraPersistenceException 
     */
    public void addUserToRoom(Usuario us, Integer room) throws CamposDeGuerraPersistenceException;
    
    /**
     * 
     * @param room
     * @return
     * @throws CamposDeGuerraNotFoundException 
     */
    public Set<Usuario> getAllUsuariosFromRoom(Integer room) throws CamposDeGuerraNotFoundException;
    
    /**
     * 
     * @param room
     * @return
     * @throws CamposDeGuerraNotFoundException 
     */
    public Set<Usuario> getAllUsuariosFromTeamARoom(Integer room) throws CamposDeGuerraNotFoundException;
    
    /**
     * 
     * @param room
     * @return
     * @throws CamposDeGuerraNotFoundException 
     */
    public Set<Usuario> getAllUsuariosFromTeamBRoom(Integer room) throws CamposDeGuerraNotFoundException;
    
    /**
     * 
     * @param us
     * @param room
     * @throws CamposDeGuerraNotFoundException 
     */
    public void deleteUsuarioFromRoom(Usuario us, Integer room) throws CamposDeGuerraNotFoundException;
    
    
    /**
     * 
     * @param room
     * @throws CamposDeGuerraNotFoundException 
     */
    public void deleteAllUsuariosFromRoom(Integer room) throws CamposDeGuerraNotFoundException;
    

    
}
