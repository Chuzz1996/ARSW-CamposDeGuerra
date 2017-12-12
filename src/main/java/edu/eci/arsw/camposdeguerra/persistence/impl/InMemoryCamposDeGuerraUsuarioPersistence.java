/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.camposdeguerra.persistence.impl;

import edu.eci.arsw.camposdeguerra.model.Usuario;
import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraNotFoundException;
import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraPersistenceException;
import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraUsuarioPersistence;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Set;
import org.springframework.stereotype.Service;


//@Service
public  class InMemoryCamposDeGuerraUsuarioPersistence implements CamposDeGuerraUsuarioPersistence{
    
    private final ConcurrentHashMap<String, Usuario> users = new ConcurrentHashMap<>();

    
    
    public InMemoryCamposDeGuerraUsuarioPersistence() {
        Usuario u1 = new Usuario("martin", null, 99,100,"");
        Usuario u2= new Usuario("cristian", null, 100,100,"");
        Usuario u4= new Usuario("NMCC", null, 8888,100,"");
        Usuario u3 = new Usuario("felipe", null, 123,100,"");
        users.putIfAbsent("martin", u1);
        users.putIfAbsent("cristian", u2);
        users.putIfAbsent("felipe", u3);
        users.putIfAbsent("NMCC", u4);
    }
    
    @Override
    public void saveUsuario(Usuario u) throws CamposDeGuerraPersistenceException {
        if(!users.containsKey(u.getId())){users.putIfAbsent(u.getId(), u);}
        else{throw new CamposDeGuerraPersistenceException("El nombre de usuario ya se encuentra registrado,elija otro nombre");}
    }




    @Override
    public void updateUsuario(Usuario u) throws CamposDeGuerraPersistenceException {
        if(users.containsKey(u.getId())){users.replace(u.getId(),u);}
        else{users.putIfAbsent(u.getId(), u);}
    }

    @Override
    public void deleteUsuario(String user) throws CamposDeGuerraPersistenceException {
        if(users.containsKey(user)){users.remove(user);}
        else{throw new CamposDeGuerraPersistenceException("El usuario "+user+" no puede ser borrado porque no existe.");}
    }

    @Override
    public Usuario findById(String id) throws CamposDeGuerraNotFoundException {
        if(users.containsKey(id)){return users.get(id);}
        else{throw new CamposDeGuerraNotFoundException("El usuario "+id+" no existe.");}
    }

    @Override
    public Set<Usuario> getAllUsers() throws CamposDeGuerraNotFoundException {
        return new HashSet<>(users.values());
    }

}
