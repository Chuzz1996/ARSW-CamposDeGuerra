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
   
    
    
    public void addNewUsuario(Usuario u) throws CamposDeGuerraPersistenceException{
            cdg.saveUsuario(u);
    }
    
    public void updateUsuario(Usuario u) throws CamposDeGuerraPersistenceException{
            cdg.updateUsuario(u);
    }
    
    public Set<Usuario> getAllUsuarios() throws CamposDeGuerraNotFoundException{
        return cdg.getAllUsuarios();
    }
    
    
    public Usuario getUsuario(String user) throws CamposDeGuerraNotFoundException{
        return cdg.getUsuario(user);
    }
  

    public void deleteUsuario(String user) throws CamposDeGuerraPersistenceException {
        cdg.deleteUsuario(user);
    }
    
}
