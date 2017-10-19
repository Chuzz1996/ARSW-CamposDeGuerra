/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.camposdeguerra.model;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author crist
 */
public class Room {

    private ConcurrentLinkedQueue<Usuario> equipoA = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<Usuario> equipoB = new ConcurrentLinkedQueue<>();
    private Integer id;

    public Room(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
    
    public boolean addCompetidor(Usuario us) {
        boolean agregoUser = true;
        if (equipoA.size() < 3) {
            equipoA.add(us);
        } else if (equipoB.size() < 3) {
            equipoB.add(us);
        } else {
            agregoUser = false;
        }
        return agregoUser;
    }
    
    public boolean isFull() {
        return equipoA.size() + equipoB.size() == 6;
    }

    public boolean deleteUser(Usuario us) {
        boolean borroUser=true;
        if (equipoA.contains(us)) {
            equipoA.remove(us);
        } else if (equipoB.contains(us)) {
            equipoB.remove(us);
        }else{
            borroUser=false;
        }
        return borroUser;
    }

    public void clear() {
        equipoA.clear();equipoB.clear();
    }
    
    public String TeamOfUser(Usuario us){
        String team="Ninguno";
        if (equipoA.contains(us)) {
            team="A";
        }else if(equipoB.contains(us)){
            team= "B";
        }
        return team;
    }
    
    public HashSet<Usuario> getAllCompetitors(){
        HashSet<Usuario> allCompetitors = new HashSet<>();
        for (Usuario us:equipoA) {
            allCompetitors.add(us);
        };
        for (Usuario us:equipoB) {
            allCompetitors.add(us);
        }
        return allCompetitors;
    }
    public HashSet<Usuario> getAllCompetitorsTeamA(){
        HashSet<Usuario> allCompetitorsA = new HashSet<>();
        for (Usuario us:equipoA) {
            allCompetitorsA.add(us);
        };
        return allCompetitorsA;
    }
    public HashSet<Usuario> getAllCompetitorsTeamB(){
        HashSet<Usuario> allCompetitorsB = new HashSet<>();
        for (Usuario us:equipoA) {
            allCompetitorsB.add(us);
        };
        return allCompetitorsB;
    }
}
