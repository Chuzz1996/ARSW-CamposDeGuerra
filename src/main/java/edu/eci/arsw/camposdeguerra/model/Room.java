/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.camposdeguerra.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

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
        if (equipoB.size() >= equipoA.size() && equipoA.size() < 3) {
            equipoA.add(us);
            us.setEquipo("A");
        } else if (equipoA.size() >= equipoB.size() && equipoB.size() < 3) {
            equipoB.add(us);
            us.setEquipo("B");
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
        puntajeEquipoA.set(0);
        puntajeEquipoB.set(0);
        banderaA = "";
        banderaB = "";
        banderaATomada.getAndSet(false);
        banderaBTomada.getAndSet(false);
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
        }
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
        boolean res = false;
        if (!banderaATomada.get() && ans.equals("B")) {
            banderaA = user;
            banderaATomada.getAndSet(true);
            res = true;
        }
        return res;
    }

    public synchronized boolean tomarBanderaB(String user) {
        boolean res = false;
        String ans = TeamOfUser(user);
        if (!banderaBTomada.get() && ans.equals("A")) {
            banderaB = user;
            banderaBTomada.getAndSet(true);
            res = true;
        }
        return res;
    }

    public synchronized boolean puntuarA(String user) {
        boolean ans = false;
        if (!banderaATomada.get() && banderaB.equals(user)) {
            puntajeEquipoA.getAndAdd(1);
            /**
             * if(puntuacionesEquipoA.containsKey(user)){puntuacionesEquipoA.putIfAbsent(user,puntuacionesEquipoA.get(user)+1);}
             * else{puntuacionesEquipoA.putIfAbsent(user,1);}*
             */
            for (Usuario u : equipoA) {
                if (u.getUserName().equals(user)) {
                    u.setPuntaje(u.getPuntaje() + 1);
                }
            }
            ans = true;
        }
        return ans;
    }

    public synchronized boolean puntuarB(String user) {
        boolean ans = false;
        if (!banderaBTomada.get() && banderaA.equals(user)) {
            puntajeEquipoB.getAndAdd(1);
            /**
             * if(puntuacionesEquipoB.containsKey(user)){puntuacionesEquipoB.putIfAbsent(user,puntuacionesEquipoB.get(user)+1);}
             * else{puntuacionesEquipoB.putIfAbsent(user,1);}*
             */
            for (Usuario u : equipoB) {
                if (u.getUserName().equals(user)) {
                    u.setPuntaje(u.getPuntaje() + 1);
                }
            }
            ans = true;
        }
        return ans;
    }

    public synchronized boolean soltarBanderaB(String user) {
        boolean ans = false;
        if (banderaB.equals(user)) {
            banderaB = "";
            banderaBTomada.getAndSet(false);
            ans = true;
        }

        return ans;
    }

    public synchronized boolean soltarBanderaA(String user) {
        boolean ans = false;
        if (banderaA.equals(user)) {
            banderaA = "";
            banderaATomada.getAndSet(false);
            ans = true;
        }

        return ans;
    }
    
    public List<Integer> obtenerScorer(){
        ArrayList<Integer> ans = new ArrayList<>();
        ans.add(puntajeEquipoA.get());
        ans.add(puntajeEquipoB.get());
        return ans;
    }
}
