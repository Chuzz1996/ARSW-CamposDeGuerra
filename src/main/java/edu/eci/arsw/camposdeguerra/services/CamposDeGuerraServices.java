/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.camposdeguerra.services;



import edu.eci.arsw.camposdeguerra.model.Usuario;
import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraNotFoundException;
import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraPersistence;
import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraPersistenceException;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CamposDeGuerraServices {
   
    @Autowired
    private CamposDeGuerraPersistence cdg;
    
    /**
     * 
     * @return 
     */
    public CamposDeGuerraPersistence getCamposDeGuerraPersistence(){
        return cdg;
    }
    
    /**
     * 
     * @param cdg 
     */
    public void setCamposDeGuerraPersistence(CamposDeGuerraPersistence cdg){
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
    
}
