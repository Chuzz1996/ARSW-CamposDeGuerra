/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.camposdeguerra.persistence.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import edu.eci.arsw.camposdeguerra.model.Usuario;
import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraNotFoundException;
import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraUsuarioPersistence;
import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraPersistenceException;
import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraUsuarioPersistenceMongoRepository;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MongoDBCamposDeGuerraUsuarioPersistence implements CamposDeGuerraUsuarioPersistence {
    
    @Autowired CamposDeGuerraUsuarioPersistenceMongoRepository me;
    
    public MongoDBCamposDeGuerraUsuarioPersistence() {
    }
    
    @Override
    public void saveUsuario(Usuario u) throws CamposDeGuerraPersistenceException {
        me.save(u);
    }


    @Override
    public void updateUsuario(Usuario u) throws CamposDeGuerraPersistenceException {
        MongoClientURI uri = new MongoClientURI("mongodb://root:root@ds121896.mlab.com:21896/camposdeguerra");
        MongoClient client = new MongoClient(uri);
        DB db = client.getDB("camposdeguerra");
        DBCollection coll = db.getCollection("users");
        BasicDBObject newDocument = new BasicDBObject().append("$set", new BasicDBObject().append("_id", u.getId()).append("tipoMaquina", u.getTipoMaquina()).append("puntaje", u.getPuntaje()).append("vida", u.getVida()).append("equipo", u.getEquipo()));
        BasicDBObject searchQuery = new BasicDBObject().append("_id", u.getId());
        coll.update(searchQuery, newDocument);
        
    }

    @Override
    public void deleteUsuario(String user) throws CamposDeGuerraPersistenceException {
        me.delete(user);
    }
    
     /**
     *
     * @param id
     * @return
     * @throws edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraNotFoundException
     */
    @Override
    public  Usuario findById(String id) throws CamposDeGuerraNotFoundException{
        Usuario u = me.findById(id);
        if(u==null){throw new CamposDeGuerraNotFoundException("Player not found!");}
        return u;
    }
    
    
    /**
     *
     * @return
     * @throws edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraNotFoundException
     */
    @Override
    public  Set<Usuario> getAllUsers() throws CamposDeGuerraNotFoundException{
        Set<Usuario> players = new HashSet<>();
        players=me.getAllUsers();
        return players;
    }
}
