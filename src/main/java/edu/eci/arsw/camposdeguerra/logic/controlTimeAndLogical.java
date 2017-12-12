/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.camposdeguerra.logic;

import edu.eci.arsw.camposdeguerra.cache.CamposDeGuerraRoomPersistence;
import edu.eci.arsw.camposdeguerra.model.Room;
import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraNotFoundException;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author 2106913
 */
@Service
public class controlTimeAndLogical {

    @Autowired
    private CamposDeGuerraRoomPersistence rp;
    
    private final ConcurrentHashMap<Integer, Timer> controlTiempo = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Integer, Integer> tiempoSalas = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Integer, AtomicInteger> getdatos = new ConcurrentHashMap<>();
    Integer timeGame = 180000;

   

    public boolean roomFull(Integer idSala) throws Exception {
        boolean ans = false;
        try {
            Room r = rp.getRoom(idSala);
            Integer cj = r.getCantidadActualJugadores().get();
            if (cj % 2 == 0 && cj > 0 && cj <= r.getCantidadMaximaJugadores() && r.getEstado().equals("Nojugando")) {
                ans = true;
                r.setEstado("Jugando");
                tiempoSalas.putIfAbsent(idSala, 0);
                Timer temp = new Timer();
                final int GAME_LIMIT = r.getTiempo() + 2000;
                temp.schedule(new java.util.TimerTask() {
                    int tiempo = 0;
                    @Override
                    public void run() {
                        tiempo += 1000;
                        tiempoSalas.replace(idSala, tiempo);
                        if (tiempo > GAME_LIMIT) {
                            temp.cancel();
                            controlTiempo.remove(idSala);
                            tiempoSalas.remove(idSala);
                        }
                    }
                }, 0, 1000);
                controlTiempo.putIfAbsent(idSala, temp);

            } else if (cj % 2 == 0 && cj > 0 && cj <= r.getCantidadMaximaJugadores() && r.getEstado().equals("Jugando")) {
                ans = true;
            }

        } catch (CamposDeGuerraNotFoundException e) {
        }

        return ans;
    }

    public Integer getTime(Integer idSala) {
        return tiempoSalas.get(idSala);
    }

    public Integer getDatos(Integer idSala) throws Exception {
        AtomicInteger listos = new AtomicInteger(0);
        if (getdatos.containsKey(idSala)) {
            listos = getdatos.get(idSala);
            if (listos.get() == rp.getRoom(idSala).getAllCompetitors().size() - 1) {
                if (idSala < 4) {
                    rp.deleteAllUsuariosFromRoom(idSala);
                } else {
                    rp.deleteRoom(idSala);
                }
                getdatos.remove(idSala);
            } else {
                listos.addAndGet(1);
                getdatos.replace(idSala, listos);
            }
        } else {
            getdatos.putIfAbsent(idSala,new AtomicInteger(1));
        }
        return listos.get();
    }

}
