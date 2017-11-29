/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.camposdeguerra.services;



import edu.eci.arsw.camposdeguerra.cache.CamposDeGuerraRoomPersistence;
import edu.eci.arsw.camposdeguerra.model.Usuario;
import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraNotFoundException;
import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraUsuarioPersistence;
import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraPersistenceException;
import edu.eci.arsw.camposdeguerra.model.Room;
import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraMapsPersistence;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CamposDeGuerraServices {
   
    @Autowired
    private CamposDeGuerraUsuarioPersistence cdg;
    @Autowired
    private CamposDeGuerraRoomPersistence cdgr;
    
    @Autowired
    private CamposDeGuerraMapsPersistence cdgm;
    
    /**
     * 
     * @return 
     */
    public CamposDeGuerraUsuarioPersistence getCamposDeGuerraUsuarioPersistence(){
        return cdg;
    }
    
    /**
     * 
     * @param cdg 
     */
    public void setCamposDeGuerraUsuarioPersistence(CamposDeGuerraUsuarioPersistence cdg){
        this.cdg = cdg;
    }
   
    
    /**
     * 
     * @param u
     * @throws CamposDeGuerraPersistenceException 
     */
    public void addNewUsuario(Usuario u) throws CamposDeGuerraPersistenceException{
            cdg.saveUsuario(u);
    }
    
    /**
     * 
     * @param u
     * @throws CamposDeGuerraPersistenceException 
     */
    public void updateUsuario(Usuario u) throws CamposDeGuerraPersistenceException{
            cdg.updateUsuario(u);
    }
    
    /**
     * 
     * @return
     * @throws CamposDeGuerraNotFoundException 
     */
    public Set<Usuario> getAllUsuarios() throws CamposDeGuerraNotFoundException{
        return cdg.getAllUsers();
    }
    
    /**
     * 
     * @param user
     * @return
     * @throws CamposDeGuerraNotFoundException 
     */
    public Usuario getUsuario(String user) throws CamposDeGuerraNotFoundException{
        return cdg.findById(user);
    }
  
    /**
     * 
     * @param user
     * @throws CamposDeGuerraPersistenceException 
     */
    public void deleteUsuario(String user) throws CamposDeGuerraPersistenceException {
        cdg.deleteUsuario(user);
    }
    
    /**
     * 
     * @return
     * @throws CamposDeGuerraNotFoundException 
     */
    public Integer getRoomFree() throws CamposDeGuerraNotFoundException {
        return cdgr.getRoomFree();
    }

    /**
     * 
     * @param us
     * @param room
     * @throws CamposDeGuerraPersistenceException 
     */
    public void addUserToRoom(Usuario us, Integer room) throws CamposDeGuerraPersistenceException {
        cdgr.addUserToRoom(us, room);
    }

    /**
     * 
     * @param room
     * @return
     * @throws CamposDeGuerraNotFoundException 
     */
    public Set<Usuario> getAllUsuariosFromRoom(Integer room) throws CamposDeGuerraNotFoundException {
        return cdgr.getAllUsuariosFromRoom(room);
    }

 

    /**
     * 
     * @param us
     * @param room
     * @throws CamposDeGuerraNotFoundException 
     */
    public void deleteUsuarioFromRoom(String us, Integer room) throws CamposDeGuerraNotFoundException {
        cdgr.deleteUsuarioFromRoom(us, room);
    }

    /**
     * 
     * @param room
     * @throws CamposDeGuerraNotFoundException 
     */
    public void deleteAllUsuariosFromRoom(Integer room) throws CamposDeGuerraNotFoundException {
        cdgr.deleteAllUsuariosFromRoom(room);
    }
    
    /**
     * 
     * @param u
     * @param r
     * @return
     * @throws CamposDeGuerraNotFoundException 
     */
    public String getMyTeam(String u,Integer r) throws CamposDeGuerraNotFoundException {
        return cdgr.getTeamOfMyRoom(u,r);
    }
    
    /**
     * 
     * @param user
     * @param room
     * @throws CamposDeGuerraNotFoundException 
     */
    public void setFlagARoom(String user, Integer room) throws CamposDeGuerraNotFoundException {
        cdgr.setFlagARoom(user, room);
    }

    /**
     * 
     * @param user
     * @param room
     * @throws CamposDeGuerraNotFoundException 
     */
    public void setFlagBRoom(String user, Integer room) throws CamposDeGuerraNotFoundException {
        cdgr.setFlagBRoom(user, room);
    }
    
    /**
     * 
     * @param user
     * @param room
     * @throws CamposDeGuerraNotFoundException 
     */
    public void puntuarA(String user,Integer room) throws CamposDeGuerraNotFoundException {
      cdgr.puntuarA(user, room);
    }

    /**
     * 
     * @param user
     * @param room
     * @throws CamposDeGuerraNotFoundException 
     */
    public void puntuarB(String user,Integer room) throws CamposDeGuerraNotFoundException {
        cdgr.puntuarB(user, room);
    }

    /**
     * 
     * @param user
     * @param room
     * @throws CamposDeGuerraNotFoundException 
     */
    public void soltarBanderaB(String user,Integer room) throws CamposDeGuerraNotFoundException {
       cdgr.soltarBanderaB(user, room);
    }

    /**
     * 
     * @param user
     * @param room
     * @throws CamposDeGuerraNotFoundException 
     */
    public void soltarBanderaA(String user,Integer room) throws CamposDeGuerraNotFoundException {
        cdgr.soltarBanderaA(user, room);
    }
    
    /**
     * 
     * @param room
     * @return
     * @throws CamposDeGuerraNotFoundException 
     */
    public List<Integer> obtenerScorer(Integer room) throws CamposDeGuerraNotFoundException {
        return cdgr.obtenerScorer(room);
    }
    
    /**
     * 
     * @return
     * @throws CamposDeGuerraNotFoundException 
     */
    public List<Room> getAllRooms()throws CamposDeGuerraNotFoundException{
        return cdgr.getAllRooms();
    }
    
    /**
     * 
     * @param room
     * @throws CamposDeGuerraPersistenceException 
     */
    public void addRoom(Room room)throws CamposDeGuerraPersistenceException{
        cdgr.addRoom(room);
    }
    
    /**
     * 
     * @param idRoom 
     * @return  
     * @throws edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraNotFoundException 
     */
    public Room getRoom(Integer idRoom)throws CamposDeGuerraNotFoundException{
        return cdgr.getRoom(idRoom);
    }
    
    public String[][] getMap(String idMap)throws CamposDeGuerraNotFoundException{
        return cdgm.findMap(idMap);
    }
    
}
