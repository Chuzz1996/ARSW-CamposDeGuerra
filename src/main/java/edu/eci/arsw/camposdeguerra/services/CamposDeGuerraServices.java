/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.camposdeguerra.services;



import edu.eci.arsw.camposdeguerra.model.Usuario;
import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraNotFoundException;
import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraUsuarioPersistence;
import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraPersistenceException;
import edu.eci.arsw.camposdeguerra.cache.CamposDeGuerraRoomPersistence;
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
        return cdg.getAllUsuarios();
    }
    
    /**
     * 
     * @param user
     * @return
     * @throws CamposDeGuerraNotFoundException 
     */
    public Usuario getUsuario(String user) throws CamposDeGuerraNotFoundException{
        return cdg.getUsuario(user);
    }
  
    /**
     * 
     * @param user
     * @throws CamposDeGuerraPersistenceException 
     */
    public void deleteUsuario(String user) throws CamposDeGuerraPersistenceException {
        cdg.deleteUsuario(user);
    }
    
    public Integer getRoomFree() throws CamposDeGuerraNotFoundException {
        
        return cdgr.getRoomFree();
    }

    public void addUserToRoom(Usuario us, Integer room) throws CamposDeGuerraPersistenceException {
        cdgr.addUserToRoom(us, room);
    }


    public Set<Usuario> getAllUsuariosFromRoom(Integer room) throws CamposDeGuerraNotFoundException {
        return cdgr.getAllUsuariosFromRoom(room);
    }

    public Set<Usuario> getAllUsuariosFromTeamARoom(Integer room) throws CamposDeGuerraNotFoundException {
        return cdgr.getAllUsuariosFromTeamARoom(room);
    }
    
    public Set<Usuario> getAllUsuariosFromTeamBRoom(Integer room) throws CamposDeGuerraNotFoundException {
        return cdgr.getAllUsuariosFromTeamBRoom(room);
    }

    public void deleteUsuarioFromRoom(Usuario us, Integer room) throws CamposDeGuerraNotFoundException {
        cdgr.deleteUsuarioFromRoom(us, room);
    }


    public void deleteAllUsuariosFromRoom(Integer room) throws CamposDeGuerraNotFoundException {
        cdgr.deleteAllUsuariosFromRoom(room);
    }
    
    public String getMyTeam(String u,Integer r) throws CamposDeGuerraNotFoundException {
        return cdgr.getTeamOfMyRoom(u,r);
    }
    
    public void setFlagARoom(String user, Integer room) throws CamposDeGuerraNotFoundException {
        cdgr.setFlagARoom(user, room);
    }

    public void setFlagBRoom(String user, Integer room) throws CamposDeGuerraNotFoundException {
        cdgr.setFlagBRoom(user, room);
    }
    
    public void puntuarA(String user,Integer room) throws CamposDeGuerraNotFoundException {
      cdgr.puntuarA(user, room);
    }

   
    public void puntuarB(String user,Integer room) throws CamposDeGuerraNotFoundException {
        cdgr.puntuarB(user, room);
    }

  
    public void soltarBanderaB(String user,Integer room) throws CamposDeGuerraNotFoundException {
       cdgr.soltarBanderaB(user, room);
    }

    
    public void soltarBanderaA(String user,Integer room) throws CamposDeGuerraNotFoundException {
        cdgr.soltarBanderaA(user, room);
    }
    
    public List<Integer> obtenerScorer(Integer room) throws CamposDeGuerraNotFoundException {
        return cdgr.obtenerScorer(room);
    }
    
}
