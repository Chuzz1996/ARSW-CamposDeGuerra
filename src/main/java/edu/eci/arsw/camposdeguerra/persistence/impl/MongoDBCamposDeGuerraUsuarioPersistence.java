/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.camposdeguerra.persistence.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import edu.eci.arsw.camposdeguerra.model.Usuario;
import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraNotFoundException;
import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraUsuarioPersistence;
import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraPersistenceException;
import java.util.Set;
import org.springframework.stereotype.Service;



public  abstract class MongoDBCamposDeGuerraUsuarioPersistence implements CamposDeGuerraUsuarioPersistence {
   

    
    public MongoDBCamposDeGuerraUsuarioPersistence() {

    }
    
    @Override
    public void saveUsuario(Usuario u) throws CamposDeGuerraPersistenceException {
        
        MongoClientURI uri = new MongoClientURI("mongodb://root:root@ds121896.mlab.com:21896/camposdeguerra");
        MongoClient client = new MongoClient(uri);
        DB db = client.getDB("camposdeguerra");
        DBCollection coll = db.getCollection("Users");
        BasicDBObject searchQuery = new BasicDBObject().append("id", u.getId());
        DBCursor cursor = coll.find(searchQuery);
        while (cursor.hasNext()) {
            throw new CamposDeGuerraPersistenceException("Player found!");
        }
        BasicDBObject user = new BasicDBObject("id", u.getId()).append("tipoMaquina", u.getTipoMaquina()).append("puntaje", u.getPuntaje()).append("vida", u.getVida()).append("equipo", u.getEquipo());
        coll.insert(user);
        client.close();
    }


    @Override
    public void updateUsuario(Usuario u) throws CamposDeGuerraPersistenceException {
        
        MongoClientURI uri = new MongoClientURI("mongodb://root:root@ds121896.mlab.com:21896/camposdeguerra");
        MongoClient client = new MongoClient(uri);
        DB db = client.getDB("camposdeguerra");
        DBCollection coll = db.getCollection("Users");
        BasicDBObject newDocument = new BasicDBObject().append("$set", new BasicDBObject().append("id", u.getId()).append("tipoMaquina", u.getTipoMaquina()).append("puntaje", u.getPuntaje()).append("vida", u.getVida()).append("equipo", u.getEquipo()));
        BasicDBObject searchQuery = new BasicDBObject().append("id", u.getId());
        coll.update(searchQuery, newDocument);
        
    }

    @Override
    public void deleteUsuario(String user) throws CamposDeGuerraPersistenceException {
        
        MongoClientURI uri = new MongoClientURI("mongodb://root:root@ds121896.mlab.com:21896/camposdeguerra");
        MongoClient client = new MongoClient(uri);
        DB db = client.getDB("camposdeguerra");
        DBCollection coll = db.getCollection("Users");
        BasicDBObject searchQuery = new BasicDBObject().append("id", user);
        coll.remove(searchQuery);
        
    }
    
     /**
     *
     * @param id
     * @return
     * @throws edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraNotFoundException
     */
    @Override
    public abstract Usuario findById(String id) throws CamposDeGuerraNotFoundException;
    
    
    /**
     *
     * @return
     * @throws edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraNotFoundException
     */
    @Override
    public abstract Set<Usuario> getAllUsers() throws CamposDeGuerraNotFoundException;
    
    
}
