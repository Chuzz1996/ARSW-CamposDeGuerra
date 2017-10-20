/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.camposdeguerra.persistence.impl;

import edu.eci.arsw.camposdeguerra.model.Usuario;
import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraNotFoundException;
import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraUsuarioPersistence;
import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraPersistenceException;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Set;
import org.springframework.stereotype.Service;


@Service
public class InMemoryCamposDeGuerraUsuarioPersistence implements CamposDeGuerraUsuarioPersistence {
    
    private final ConcurrentHashMap<String, Usuario> users = new ConcurrentHashMap<>();

    
    
    public InMemoryCamposDeGuerraUsuarioPersistence() {
        Usuario u1 = new Usuario("martin", null, 99999,100);
        Usuario u2= new Usuario("cristian", null, 0,100);
        Usuario u3 = new Usuario("felipe", null, 123,100);
        users.putIfAbsent("martin", u1);
        users.putIfAbsent("cristian", u2);
        users.putIfAbsent("felipe", u3);
    }
    
    @Override
    public void saveUsuario(Usuario u) throws CamposDeGuerraPersistenceException {
        if(!users.containsKey(u.getUserName())){users.putIfAbsent(u.getUserName(), u);}
        else{throw new CamposDeGuerraPersistenceException("El nombre de usuario ya se encuentra registrado,elija otro nombre");}
    }

    @Override
    public Usuario getUsuario(String user) throws CamposDeGuerraNotFoundException {
        if(users.containsKey(user)){return users.get(user);}
        else{throw new CamposDeGuerraNotFoundException("El usuario "+user+" no existe.");}
    }

    @Override
    public Set<Usuario> getAllUsuarios() throws CamposDeGuerraNotFoundException {
        return new HashSet<>(users.values());
    }

    @Override
    public void updateUsuario(Usuario u) throws CamposDeGuerraPersistenceException {
        if(users.containsKey(u.getUserName())){users.replace(u.getUserName(),u);}
        else{users.putIfAbsent(u.getUserName(), u);}
    }

    @Override
    public void deleteUsuario(String user) throws CamposDeGuerraPersistenceException {
        if(users.containsKey(user)){users.remove(user);}
        else{throw new CamposDeGuerraPersistenceException("El usuario "+user+" no puede ser borrado porque no existe.");}
    }
    
}
