/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.camposdeguerra.persistence;

import edu.eci.arsw.camposdeguerra.model.Usuario;
import java.util.Set;


public interface CamposDeGuerraPersistence {
    
    
    public void saveUsuario(Usuario bp) throws CamposDeGuerraPersistenceException;
    
    
    public Usuario getUsuario(String user) throws CamposDeGuerraNotFoundException;
    
    
    public Set<Usuario> getAllUsuarios() throws CamposDeGuerraNotFoundException;
    
    
    public void updateUsuario(Usuario u) throws CamposDeGuerraPersistenceException;
    
    
    public void deleteUsuario(String user) throws CamposDeGuerraPersistenceException;
}
