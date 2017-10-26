/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.camposdeguerra.model;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author crist
 */
public class Room {

    private AtomicInteger puntajeEquipoA = new AtomicInteger(0);
    private AtomicInteger puntajeEquipoB = new AtomicInteger(0);
    private ConcurrentLinkedQueue<Usuario> equipoA = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<Usuario> equipoB = new ConcurrentLinkedQueue<>();
    private Integer id;
    private String banderaA = "", banderaB = "";
    private AtomicBoolean banderaATomada = new AtomicBoolean(false), banderaBTomada = new AtomicBoolean(false);

    /**
     * 
     * @param id 
     */
    public Room(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param us
     * @return 
     */
    public boolean addCompetidor(Usuario us) {
        boolean agregoUser = true;
        if (equipoA.size() >= equipoB.size() && equipoB.size() < 3) {
            equipoB.add(us);
        } else if (equipoB.size() >= equipoA.size() && equipoA.size() < 3) {
            equipoA.add(us);
        } else {
            agregoUser = false;
        }
        return agregoUser;
    }

    /**
     * 
     * @return 
     */
    public boolean isFull() {
        return equipoA.size() + equipoB.size() == 6;
    }

    /**
     * 
     * @param us
     * @return 
     */
    public boolean deleteUser(Usuario us) {
        boolean borroUser = true;
        if (equipoA.contains(us)) {
            equipoA.remove(us);
        } else if (equipoB.contains(us)) {
            equipoB.remove(us);
        } else {
            borroUser = false;
        }
        return borroUser;
    }

    /**
     * 
     */
    public void clear() {
        equipoA.clear();
        equipoB.clear();
    }

    /**
     * 
     * @param us
     * @return 
     */
    public String TeamOfUser(String us) {
        String team = "Ninguno";
        for (Usuario u : equipoA) {
            if (u.getUserName().equals(us)) {
                team = "A";
            }
        }
        for (Usuario u : equipoB) {
            if (u.getUserName().equals(us)) {
                team = "B";
            }
        }
        return team;
    }

    /**
     * 
     * @return 
     */
    public HashSet<Usuario> getAllCompetitors() {
        HashSet<Usuario> allCompetitors = new HashSet<>();
        for (Usuario us : equipoA) {
            allCompetitors.add(us);
        };
        for (Usuario us : equipoB) {
            allCompetitors.add(us);
        }
        return allCompetitors;
    }

    public HashSet<Usuario> getAllCompetitorsTeamA() {
        HashSet<Usuario> allCompetitorsA = new HashSet<>();
        for (Usuario us : equipoA) {
            allCompetitorsA.add(us);
        };
        return allCompetitorsA;
    }

    public HashSet<Usuario> getAllCompetitorsTeamB() {
        HashSet<Usuario> allCompetitorsB = new HashSet<>();
        for (Usuario us : equipoB) {
            allCompetitorsB.add(us);
        };
        return allCompetitorsB;
    }

    public synchronized boolean tomarBanderaA(String user) {
        String ans = TeamOfUser(user);
        if (!banderaATomada.get() && ans.equals("B")) {
            banderaA = user;
            banderaATomada.getAndSet(true);
        }
        return banderaATomada.get();
    }

    public synchronized boolean tomarBanderaB(String user) {
        String ans = TeamOfUser(user);
        if (!banderaBTomada.get() && ans.equals("A")) {
            banderaB = user;
            banderaBTomada.getAndSet(true);
        }
        return banderaBTomada.get();
    }

    

    public synchronized boolean puntuarA(String user) {
        boolean ans=false;
        if (!banderaATomada.get() && banderaB.equals(user)) {
            banderaB = "";
            banderaBTomada.getAndSet(false);
            puntajeEquipoA.getAndAdd(1);
            ans=true;
        }
        return ans;
    }

    public synchronized boolean puntuarB(String user) {
        boolean ans=false;
        if (!banderaBTomada.get() && banderaA.equals(user)) {
            banderaA = "";
            banderaATomada.getAndSet(false);
            puntajeEquipoB.getAndAdd(1);
            ans=true;
        }
        return ans;
    }

    public synchronized boolean soltarBanderaB(String user) {
        boolean ans=false;
        if(banderaB.equals(user)){
            banderaB = "";
            banderaBTomada.getAndSet(false);
            ans=true;
        }
        return ans;
    }
    
    public synchronized boolean soltarBanderaA(String user) {
        boolean ans=false;
        if(banderaA.equals(user)){
        banderaA = "";
        banderaATomada.getAndSet(false);
        }
        return ans;
    }
}
