/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.camposdeguerra.mom;

import edu.eci.arsw.camposdeguerra.cache.CamposDeGuerraRoomPersistence;
import edu.eci.arsw.camposdeguerra.model.Room;
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
    private final ConcurrentHashMap<Integer, Integer> getdatos = new ConcurrentHashMap<>();
    Integer timeGame = 180000;
    
    public Integer listoParaJugar(String estado, Integer idSala) throws Exception {
        Integer pos=0;
        if (estado.equals("listo")) {
            Room r=null;
            try{
               r=rp.getRoom(idSala);
               pos=(r.getAllCompetitors().size())+1;
            }catch (Exception e){throw new Exception("Hubo un problema asignando posicion");}
        }
        return pos;
    }

    public boolean roomFull(Integer idSala) throws Exception{
        boolean ans = false;
        try{
            Room r=rp.getRoom(idSala);
            Integer cj= r.getAllCompetitors().size();
        if ( cj % 2 == 0 && cj>0 && cj<= r.getCantidadJugadores() && r.getEstado().equals("No jugando")) {
            ans = true;
            r.setEstado("Jugando");
            tiempoSalas.putIfAbsent(idSala,0);
            Timer temp = new Timer();
            final int GAME_LIMIT = timeGame + 2000;
            temp.schedule(new java.util.TimerTask() {
                int tiempo = 0;
                @Override
                public void run() {
                    tiempo += 1000;
                    tiempoSalas.replace(idSala,tiempo);
                    if (tiempo > GAME_LIMIT) {
                        temp.cancel();
                        controlTiempo.remove(idSala);
                        tiempoSalas.remove(idSala);
                    }
                }
            }, 0, 1000);
            controlTiempo.putIfAbsent(idSala, temp);

        } else if (cj % 2 == 0 && cj>0 && cj<= r.getCantidadJugadores() && r.getEstado().equals("Jugando")) {
            ans = true;
        }
  
        }catch (Exception e){}
        
        return ans;
    }
    
    public Integer getTime(Integer idSala){
        return tiempoSalas.get(idSala);
    }
    
     

    void getDatos(Integer idSala) throws Exception{

        if(getdatos.containsKey(idSala)){
            Integer listos=getdatos.get(idSala);
            if(listos==rp.getRoom(idSala).getAllCompetitors().size()-1){
                if(idSala<4){rp.deleteAllUsuariosFromRoom(idSala);}
                else{rp.deleteRoom(idSala);}
                getdatos.remove(idSala);
            }
            else{
                listos=listos+1;
                getdatos.replace(idSala, listos);
            }
        }
        else{
         getdatos.putIfAbsent(idSala, 1);
        }
    }
    
    
    


}
