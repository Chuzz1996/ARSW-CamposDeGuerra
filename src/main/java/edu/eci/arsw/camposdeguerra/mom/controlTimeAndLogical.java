/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.camposdeguerra.mom;

import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

/**
 *
 * @author 2106913
 */

public class controlTimeAndLogical {
    private final ConcurrentHashMap<Integer, AtomicInteger> personasEnsalas = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Integer, String> estadoSalas = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Integer, Timer> controlTiempo = new ConcurrentHashMap<>();
    Integer timeGame = 180000;
    @Autowired private SimpMessagingTemplate msgt;
    
    public Integer listoParaJugar(String estado, Integer idSala) throws Exception {
        Integer pos=0;
        if (estado.equals("listo")) {
            if (personasEnsalas.containsKey(idSala)) {
                AtomicInteger temp = personasEnsalas.get(idSala);
                pos=temp.addAndGet(1);
                personasEnsalas.putIfAbsent(idSala, temp);
            } else {
                AtomicInteger temp = new AtomicInteger(1);
                pos=temp.get();
                personasEnsalas.putIfAbsent(idSala, temp);
                estadoSalas.putIfAbsent(idSala, "nojugando");
            }
        }
        return pos;
    }

    public boolean roomFull(Integer idSala) {
        boolean ans = false;
        if (personasEnsalas.get(idSala).get() >= 4 && estadoSalas.get(idSala).equals("nojugando")) {
            ans = true;
            estadoSalas.replace(idSala, "jugando");
            Timer temp = new Timer();
            temp.schedule(new java.util.TimerTask() {@Override public void run() {msgt.convertAndSend("/topic/sala." + idSala + "/endGame", "Termino el juego");personasEnsalas.remove(idSala);estadoSalas.remove(idSala);}}, timeGame);
            final int GAME_LIMIT = timeGame + 2000;
            temp.schedule(new java.util.TimerTask() {
                int tiempo = 0;
                @Override
                public void run() {
                    String time = String.format("%02d : %02d", TimeUnit.MILLISECONDS.toMinutes(tiempo), TimeUnit.MILLISECONDS.toSeconds(tiempo) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(tiempo)));
                    msgt.convertAndSend("/topic/sala." + idSala + "/tiempo", time);
                    tiempo += 1000;
                    if (tiempo > GAME_LIMIT) {
                        temp.cancel();
                        controlTiempo.remove(idSala);
                    }
                }
            }, 0, 1000);
            controlTiempo.putIfAbsent(idSala, temp);

        } else if (personasEnsalas.get(idSala).get() >= 4 && estadoSalas.get(idSala).equals("jugando")) {
            ans = true;
        }
        return ans;
    }


}
