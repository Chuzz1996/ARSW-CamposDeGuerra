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
    private Integer id = 0;
    private String banderaA = "", banderaB = "";
    private AtomicBoolean banderaATomada = new AtomicBoolean(false), banderaBTomada = new AtomicBoolean(false);
    private int tiempo = 180000;
    private int cantidadJugadores = 6;
    private String potenciadores = "";
    private int capturasPartida = 2;
    private String tipoMaquina = "Destructora";
    private String estado = "No jugando";

    /**
     *
     * @return
     */
    public String getTipoMaquina() {
        return tipoMaquina;
    }

    /**
     *
     * @param tipoMaquina
     */
    public void setTipoMaquina(String tipoMaquina) {
        this.tipoMaquina = tipoMaquina;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     *
     * @return
     */
    public AtomicInteger getPuntajeEquipoA() {
        return puntajeEquipoA;
    }

    /**
     *
     * @param puntajeEquipoA
     */
    public void setPuntajeEquipoA(AtomicInteger puntajeEquipoA) {
        this.puntajeEquipoA = puntajeEquipoA;
    }

    /**
     *
     * @return
     */
    public AtomicInteger getPuntajeEquipoB() {
        return puntajeEquipoB;
    }

    /**
     *
     * @param puntajeEquipoB
     */
    public void setPuntajeEquipoB(AtomicInteger puntajeEquipoB) {
        this.puntajeEquipoB = puntajeEquipoB;
    }

    /**
     *
     * @return
     */
    public ConcurrentLinkedQueue<Usuario> getEquipoA() {
        return equipoA;
    }

    /**
     *
     * @param equipoA
     */
    public void setEquipoA(ConcurrentLinkedQueue<Usuario> equipoA) {
        this.equipoA = equipoA;
    }

    /**
     *
     * @return
     */
    public ConcurrentLinkedQueue<Usuario> getEquipoB() {
        return equipoB;
    }

    /**
     *
     * @param equipoB
     */
    public void setEquipoB(ConcurrentLinkedQueue<Usuario> equipoB) {
        this.equipoB = equipoB;
    }

    /**
     *
     * @return
     */
    public String getBanderaA() {
        return banderaA;
    }

    /**
     *
     * @param banderaA
     */
    public void setBanderaA(String banderaA) {
        this.banderaA = banderaA;
    }

    /**
     *
     * @return
     */
    public String getBanderaB() {
        return banderaB;
    }

    /**
     *
     * @param banderaB
     */
    public void setBanderaB(String banderaB) {
        this.banderaB = banderaB;
    }

    /**
     *
     * @return
     */
    public AtomicBoolean getBanderaATomada() {
        return banderaATomada;
    }

    /**
     *
     * @param banderaATomada
     */
    public void setBanderaATomada(AtomicBoolean banderaATomada) {
        this.banderaATomada = banderaATomada;
    }

    /**
     *
     * @return
     */
    public AtomicBoolean getBanderaBTomada() {
        return banderaBTomada;
    }

    /**
     *
     * @param banderaBTomada
     */
    public void setBanderaBTomada(AtomicBoolean banderaBTomada) {
        this.banderaBTomada = banderaBTomada;
    }

    /**
     *
     * @return
     */
    public int getTiempo() {
        return tiempo;
    }

    /**
     *
     * @param tiempo
     */
    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    /**
     *
     * @return
     */
    public int getCantidadJugadores() {
        return cantidadJugadores;
    }

    /**
     *
     * @param cantidadJugadores
     */
    public void setCantidadJugadores(int cantidadJugadores) {
        this.cantidadJugadores = cantidadJugadores;
    }

    /**
     *
     * @return
     */
    public String getPotenciadores() {
        return potenciadores;
    }

    /**
     *
     * @param potenciadores
     */
    public void setPotenciadores(String potenciadores) {
        this.potenciadores = potenciadores;
    }

    /**
     *
     * @return
     */
    public int getCapturasPartida() {
        return capturasPartida;
    }

    /**
     *
     * @param capturasPartida
     */
    public void setCapturasPartida(int capturasPartida) {
        this.capturasPartida = capturasPartida;
    }

    /**
     *
     * @param id
     */
    public Room(Integer id) {
        this.id = id;
    }

    public Room() {
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
    public boolean isEmpty() {
        return equipoA.size() + equipoB.size() == 0;
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
    public boolean deleteUser(String us) {
        boolean borroUser = false;
        ArrayList<Usuario> temp = new ArrayList<>(equipoA);
        ArrayList<Usuario> temp2 = new ArrayList<>(equipoB);
        for (Usuario u : temp) {
            if (u.getUserName().equals(us)) {
                equipoA.remove(u);
                borroUser = true;
            }
        }
        if (!borroUser) {
            for (Usuario u : temp2) {
                if (u.getUserName().equals(us)) {
                    equipoB.remove(u);
                    borroUser = true;
                }
            }
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
        }
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

    public List<Integer> obtenerScorer() {
        ArrayList<Integer> ans = new ArrayList<>();
        ans.add(puntajeEquipoA.get());
        ans.add(puntajeEquipoB.get());
        return ans;
    }
}
