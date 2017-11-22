/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.camposdeguerra.cache;


import edu.eci.arsw.camposdeguerra.model.Room;
import edu.eci.arsw.camposdeguerra.model.Usuario;
import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraNotFoundException;
import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraPersistenceException;
import java.util.List;
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
     * @param idSala
     * @throws CamposDeGuerraPersistenceException 
     */
    public void addRoom(Room room)throws CamposDeGuerraPersistenceException;
    
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
    
    
    /**
     * 
     * @param user
     * @param room
     * @return
     * @throws CamposDeGuerraNotFoundException 
     */
    public String getTeamOfMyRoom(String user,Integer room) throws CamposDeGuerraNotFoundException;
 
    /**
     * 
     * @param user
     * @param room
     * @throws CamposDeGuerraNotFoundException 
     */
    public void setFlagARoom(String user,Integer room) throws CamposDeGuerraNotFoundException;
    
    /**
     * 
     * @param user
     * @param room
     * @throws CamposDeGuerraNotFoundException 
     */
    public void setFlagBRoom(String user,Integer room) throws CamposDeGuerraNotFoundException;
    
    /**
     * 
     * @return
     * @throws CamposDeGuerraNotFoundException 
     */
    public List<Room> getAllRooms()throws CamposDeGuerraNotFoundException;
    
    /**
     * 
     * @param user
     * @param room
     * @throws CamposDeGuerraNotFoundException 
     */
    public  void puntuarA(String user,Integer room) throws CamposDeGuerraNotFoundException;

    /**
     * 
     * @param user
     * @param room
     * @throws CamposDeGuerraNotFoundException 
     */
    public  void puntuarB(String user,Integer room) throws CamposDeGuerraNotFoundException;

    /**
     * 
     * @param user
     * @param room
     * @throws CamposDeGuerraNotFoundException 
     */
    public  void soltarBanderaB(String user,Integer room) throws CamposDeGuerraNotFoundException;
    
    /**
     * 
     * @param user
     * @param room
     * @throws CamposDeGuerraNotFoundException 
     */
    public  void soltarBanderaA(String user,Integer room) throws CamposDeGuerraNotFoundException;
    
    /**
     * 
     * @param room
     * @return
     * @throws CamposDeGuerraNotFoundException 
     */
    public List<Integer> obtenerScorer(Integer room) throws CamposDeGuerraNotFoundException;

}
