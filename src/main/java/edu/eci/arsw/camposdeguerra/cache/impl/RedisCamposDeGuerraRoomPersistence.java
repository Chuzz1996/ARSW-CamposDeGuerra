/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.camposdeguerra.cache.impl;

import ch.qos.logback.core.pattern.parser.Parser;
import edu.eci.arsw.camposdeguerra.model.Room;
import edu.eci.arsw.camposdeguerra.model.Usuario;
import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraNotFoundException;
import edu.eci.arsw.camposdeguerra.cache.CamposDeGuerraRoomPersistence;
import edu.eci.arsw.camposdeguerra.model.Maquina;
import edu.eci.arsw.camposdeguerra.persistence.CamposDeGuerraPersistenceException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;



@Service
public class RedisCamposDeGuerraRoomPersistence implements CamposDeGuerraRoomPersistence {

    @Autowired
    private StringRedisTemplate template;

    public RedisCamposDeGuerraRoomPersistence() {
    }

    @Override
    public Integer getRoomFree() throws CamposDeGuerraNotFoundException {
        Integer ans = -1;
        for (int i = 0; i < 4; i++) {
            String value = (String) template.opsForHash().get("room:" + i, "cantidadActualJugadores");
            String value2 = (String) template.opsForHash().get("room:" + i, "cantidadMaximaJugadores");
            if (Integer.parseInt(value) < Integer.parseInt(value2)) {
                ans = i;
            }
        }
        if (ans == -1) {
            throw new CamposDeGuerraNotFoundException("No existen rooms disponibles!");
        }
        return ans;

    }

    @Override
    public void addUserToRoom(Usuario us, Integer room) throws CamposDeGuerraPersistenceException {
        String value = (String) template.opsForHash().get("room:" + room, "cantidadActualJugadores");
        String value2 = (String) template.opsForHash().get("room:" + room, "cantidadMaximaJugadores");
        if (value != null) {
            if (Integer.parseInt(value) < Integer.parseInt(value2)) {
                template.execute(new SessionCallback< List< Object>>() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public < K, V> List<Object> execute(final RedisOperations< K, V> operations) throws DataAccessException {
                        String equipo = "";
                        if (Integer.parseInt(value) + 1 % 2 == 0) {
                            equipo = "B";
                        } else {
                            equipo = "A";
                        }
                        operations.watch((K) ("room:" + room + "cantidadActualJugadores"));
                        operations.multi();
                        operations.opsForHash().put((K) ("room:" + room + ":" + us.getId()), "id", us.getId());
                        operations.opsForHash().put((K) ("room:" + room + ":" + us.getId()), "vida", us.getVida());
                        operations.opsForHash().put((K) ("room:" + room + ":" + us.getId()), "puntaje", us.getPuntaje());
                        operations.opsForHash().put((K) ("room:" + room + ":" + us.getId()), "equipo", equipo);
                        operations.opsForHash().put((K) ("room:" + room + ":" + us.getId() + ":maquina"), "speed", us.getTipoMaquina().getSpeed());
                        operations.opsForHash().put((K) ("room:" + room + ":" + us.getId() + ":maquina"), "attack", us.getTipoMaquina().getAttack());
                        operations.opsForHash().put((K) ("room:" + room + ":" + us.getId() + ":maquina"), "x", us.getTipoMaquina().getX());
                        operations.opsForHash().put((K) ("room:" + room + ":" + us.getId() + ":maquina"), "y", us.getTipoMaquina().getY());
                        operations.opsForHash().put((K) ("room:" + room + ":" + us.getId() + ":maquina"), "direction", us.getTipoMaquina().getDirection());
                        operations.opsForHash().put((K) ("room:" + room + ":" + us.getId() + ":maquina"), "bullets", "");
                        operations.opsForHash().put((K) ("room:" + room + ":" + us.getId() + ":maquina"), "nombreImagen", us.getTipoMaquina().getNombreImagen());
                        Object[] args = new Object[1];
                        args[0] = us.getId();
                        operations.opsForSet().add((K) ("room:" + room + ":users"), (V) args);
                        return operations.exec();
                    }
                });
            } else {
                throw new CamposDeGuerraPersistenceException("La Room ingresada se encuentre llena, Por favor intente con otra");
            }
        } else {
            throw new CamposDeGuerraPersistenceException("La Room ingresada no existe!");
        }
    }

    @Override
    public Set<Usuario> getAllUsuariosFromRoom(Integer room) throws CamposDeGuerraNotFoundException {
        HashSet<Usuario> ans = new HashSet<>();
        String value = (String) template.opsForHash().get("room:" + room, "cantidadActualJugadores");
        if (value != null) {
            Set<String> value2 = template.opsForSet().members("room:" + room + ":users");
            for (String s : value2) {
                Usuario temp = new Usuario();
                temp.setId((String) template.opsForHash().get("room:" + room + ":" + s, "id"));
                temp.setVida((Integer) template.opsForHash().get("room:" + room + ":" + s, "vida"));
                temp.setPuntaje((Integer) template.opsForHash().get("room:" + room + ":" + s, "puntaje"));
                temp.setEquipo((String) template.opsForHash().get("room:" + room + ":" + s, "equipo"));
                Maquina maq = new Maquina();
                maq.setAttack((Integer) template.opsForHash().get("room:" + room + ":" + s + ":" + "maquina", "attack"));
                maq.setDirection((Integer) template.opsForHash().get("room:" + room + ":" + s + ":" + "maquina", "direction"));
                maq.setNombreImagen((String) template.opsForHash().get("room:" + room + ":" + s + ":" + "maquina", "nombreImagen"));
                maq.setSpeed((Integer) template.opsForHash().get("room:" + room + ":" + s + ":" + "maquina", "speed"));
                maq.setX((Integer) template.opsForHash().get("room:" + room + ":" + s + ":" + "maquina", "x"));
                maq.setY((Integer) template.opsForHash().get("room:" + room + ":" + s + ":" + "maquina", "y"));
                temp.setTipoMaquina(maq);
                ans.add(temp);
            }

        } else {
            throw new CamposDeGuerraNotFoundException("La Room ingresada no existe!");
        }

        return ans;
    }

    @Override
    public void deleteUsuarioFromRoom(String us, Integer room) throws CamposDeGuerraNotFoundException {
        String value = (String) template.opsForHash().get("room:" + room, "cantidadActualJugadores");
        if (value != null) {
            boolean ans = false;
            boolean value2 = template.opsForSet().isMember("room:" + room + ":users", us);
            if (value2) {
                template.opsForSet().remove("room:" + room + ":users", us);
                template.opsForHash().delete("room:" + room + ":" + us, "id");
                template.opsForHash().delete("room:" + room + ":" + us, "vida");
                template.opsForHash().delete("room:" + room + ":" + us, "puntaje");
                template.opsForHash().delete("room:" + room + ":" + us, "equipo");
                template.opsForHash().delete("room:" + room + ":" + us + ":maquina", "x");
                template.opsForHash().delete("room:" + room + ":" + us + ":maquina", "y");
                template.opsForHash().delete("room:" + room + ":" + us + ":maquina", "speed");
                template.opsForHash().delete("room:" + room + ":" + us + ":maquina", "attack");
                template.opsForHash().delete("room:" + room + ":" + us + ":maquina", "nombreImagen");
                template.opsForHash().delete("room:" + room + ":" + us + ":maquina", "bullets");
                template.opsForHash().delete("room:" + room + ":" + us + ":maquina", "direction");
                ans = true;
            }

            if (!ans) {
                throw new CamposDeGuerraNotFoundException("El usuario ingresado no existe en esta sala!");
            }

        } else {
            throw new CamposDeGuerraNotFoundException("La Room ingresada no existe!");
        }
    }

    @Override
    public void deleteAllUsuariosFromRoom(Integer room) throws CamposDeGuerraNotFoundException {
        String value = (String) template.opsForHash().get("room:" + room, "cantidadActualJugadores");
        if (value != null) {
            boolean ans = false;
            Set<String> value2 = template.opsForSet().members("room:" + room + ":users");
            for (String s : value2) {
                template.opsForSet().remove("room:" + room + ":users", s);
                template.opsForHash().delete("room:" + room + ":" + s, "id");
                template.opsForHash().delete("room:" + room + ":" + s, "vida");
                template.opsForHash().delete("room:" + room + ":" + s, "puntaje");
                template.opsForHash().delete("room:" + room + ":" + s, "equipo");
                template.opsForHash().delete("room:" + room + ":" + s + ":maquina", "x");
                template.opsForHash().delete("room:" + room + ":" + s + ":maquina", "y");
                template.opsForHash().delete("room:" + room + ":" + s + ":maquina", "speed");
                template.opsForHash().delete("room:" + room + ":" + s + ":maquina", "attack");
                template.opsForHash().delete("room:" + room + ":" + s + ":maquina", "nombreImagen");
                template.opsForHash().delete("room:" + room + ":" + s + ":maquina", "bullets");
                template.opsForHash().delete("room:" + room + ":" + s + ":maquina", "direction");
            }
        } else {
            throw new CamposDeGuerraNotFoundException("La Room ingresada no existe!");
        }
    }

    @Override
    public String getTeamOfMyRoom(String us, Integer room) throws CamposDeGuerraNotFoundException {
        String ans = "";
        String value = (String) template.opsForHash().get("room:" + room, "cantidadActualJugadores");
        if (value != null) {
            String value2 = (String) template.opsForHash().get("room:" + room + ":" + us, "equipo");
            if (value2 != null) {
                ans = value2;
            } else {
                throw new CamposDeGuerraNotFoundException("El usuario ingresado no existe en esta sala!");
            }
        } else {
            throw new CamposDeGuerraNotFoundException("La Room ingresada no existe!");
        }
        return ans;
    }

    @Override
    public void setFlagARoom(String us, Integer room) throws CamposDeGuerraNotFoundException {

        String value = (String) template.opsForHash().get("room:" + room, "cantidadActualJugadores");

        if (value != null) {
            String teamuser = getTeamOfMyRoom(us, room);
            String value3 = (String) template.opsForHash().get("room:" + room, "banderaA");
            if (teamuser.equals("B") && value3.equals("")) {
                template.execute(new SessionCallback< List< Object>>() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public < K, V> List<Object> execute(final RedisOperations< K, V> operations) throws DataAccessException {
                        operations.watch((K) ("room:" + room + " banderaA"));
                        operations.multi();
                        operations.opsForHash().put((K) ("room:" + room), "banderaA", us);
                        operations.opsForHash().put((K) ("room:" + room), "banderaATomada", true);
                        return operations.exec();
                    }
                });
            } else {
                throw new CamposDeGuerraNotFoundException("EL usuario ingresado no puede tomar su propia bandera o la bandera ya fue tomada!");
            }

        } else {
            throw new CamposDeGuerraNotFoundException("La Room ingresada no existe!");
        }
    }

    @Override
    public void setFlagBRoom(String us, Integer room) throws CamposDeGuerraNotFoundException {
        String value = (String) template.opsForHash().get("room:" + room, "cantidadActualJugadores");

        if (value != null) {
            String teamuser = getTeamOfMyRoom(us, room);
            String value3 = (String) template.opsForHash().get("room:" + room, "banderaB");
            if (teamuser.equals("A") && value3.equals("")) {
                template.execute(new SessionCallback< List< Object>>() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public < K, V> List<Object> execute(final RedisOperations< K, V> operations) throws DataAccessException {
                        operations.watch((K) ("room:" + room + " banderaB"));
                        operations.multi();
                        operations.opsForHash().put((K) ("room:" + room), "banderaB", us);
                        operations.opsForHash().put((K) ("room:" + room), "banderaBTomada", true);
                        return operations.exec();
                    }
                });
            } else {
                throw new CamposDeGuerraNotFoundException("EL usuario ingresado no puede tomar su propia bandera o la bandera ya fue tomada!");
            }

        } else {
            throw new CamposDeGuerraNotFoundException("La Room ingresada no existe!");
        }
    }

    @Override
    public void puntuarA(String us, Integer room) throws CamposDeGuerraNotFoundException {

        String value = (String) template.opsForHash().get("room:" + room, "cantidadActualJugadores");

        if (value != null) {
            String teamuser = getTeamOfMyRoom(us, room);
            String value3 = (String) template.opsForHash().get("room:" + room, "banderaA");
            String value2 = (String) template.opsForHash().get("room:" + room, "banderaB");
            Integer value4 = (Integer) template.opsForHash().get("room:" + room + ":" + us, "puntaje");
            Integer value5 = (Integer) template.opsForHash().get("room:" + room, "puntajeEquipoA");
            if (teamuser.equals("A") && value3.equals("") && value2.equals(us)) {
                template.execute(new SessionCallback< List< Object>>() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public < K, V> List<Object> execute(final RedisOperations< K, V> operations) throws DataAccessException {
                        operations.watch((K) ("room:" + room + " banderaA"));
                        operations.multi();
                        operations.opsForHash().put((K) ("room:" + room + ":" + us), "puntaje", value4 + 1);
                        operations.opsForHash().put((K) ("room:" + room), "puntajeEquipoA", value5 + 1);
                        return operations.exec();
                    }
                });
            } else {
                throw new CamposDeGuerraNotFoundException("La Bandera de su equipo fue tomada por el enemigo o el usuario no existe en la sala!");
            }

        } else {
            throw new CamposDeGuerraNotFoundException("La Room ingresada no existe!");
        }
    }

    @Override
    public void puntuarB(String us, Integer room) throws CamposDeGuerraNotFoundException {

        String value = (String) template.opsForHash().get("room:" + room, "cantidadActualJugadores");

        if (value != null) {
            String teamuser = getTeamOfMyRoom(us, room);
            String value3 = (String) template.opsForHash().get("room:" + room, "banderaB");
            Integer value4 = (Integer) template.opsForHash().get("room:" + room + ":" + us, "puntaje");
            String value2 = (String) template.opsForHash().get("room:" + room, "banderaA");
            Integer value5 = (Integer) template.opsForHash().get("room:" + room, "puntajeEquipoB");
            if (teamuser.equals("B") && value3.equals("") && value2.equals(us)) {
                template.execute(new SessionCallback< List< Object>>() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public < K, V> List<Object> execute(final RedisOperations< K, V> operations) throws DataAccessException {
                        operations.watch((K) ("room:" + room + " banderaB"));
                        operations.multi();
                        operations.opsForHash().put((K) ("room:" + room + ":" + us), "puntaje", value4 + 1);
                        operations.opsForHash().put((K) ("room:" + room), "puntajeEquipoB", value5 + 1);
                        return operations.exec();
                    }
                });
            } else {
                throw new CamposDeGuerraNotFoundException("La Bandera de su equipo fue tomada por el enemigo o el usuario no existe en la sala!");
            }

        } else {
            throw new CamposDeGuerraNotFoundException("La Room ingresada no existe!");
        }
    }

    @Override
    public void soltarBanderaB(String us, Integer room) throws CamposDeGuerraNotFoundException {
        String value = (String) template.opsForHash().get("room:" + room, "cantidadActualJugadores");
        if (value != null) {
            String teamuser = getTeamOfMyRoom(us, room);
            String value2 = (String) template.opsForHash().get("room:" + room, "banderaB");
            if (teamuser.equals("A") && value2.equals(us)) {
                template.execute(new SessionCallback< List< Object>>() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public < K, V> List<Object> execute(final RedisOperations< K, V> operations) throws DataAccessException {
                        operations.watch((K) ("room:" + room + " banderaB"));
                        operations.multi();
                        operations.opsForHash().put((K) ("room:" + room), "banderaB", "");
                        operations.opsForHash().put((K) ("room:" + room), "banderaBTomada", false);
                        return operations.exec();
                    }
                });
            } else {
                throw new CamposDeGuerraNotFoundException("Usted no posee la bandera B!");
            }

        } else {
            throw new CamposDeGuerraNotFoundException("La Room ingresada no existe!");
        }
    }

    @Override
    public void soltarBanderaA(String us, Integer room) throws CamposDeGuerraNotFoundException {
        String value = (String) template.opsForHash().get("room:" + room, "cantidadActualJugadores");
        if (value != null) {
            String teamuser = getTeamOfMyRoom(us, room);
            String value2 = (String) template.opsForHash().get("room:" + room, "banderaA");
            if (teamuser.equals("B") && value2.equals(us)) {
                template.execute(new SessionCallback< List< Object>>() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public < K, V> List<Object> execute(final RedisOperations< K, V> operations) throws DataAccessException {
                        operations.watch((K) ("room:" + room + " banderaA"));
                        operations.multi();
                        operations.opsForHash().put((K) ("room:" + room), "banderaA", "");
                        operations.opsForHash().put((K) ("room:" + room), "banderaATomada", true);
                        return operations.exec();
                    }
                });
            } else {
                throw new CamposDeGuerraNotFoundException("Usted no posee la bandera A!");
            }

        } else {
            throw new CamposDeGuerraNotFoundException("La Room ingresada no existe!");
        }
    }

    @Override
    public List<Integer> obtenerScorer(Integer room) throws CamposDeGuerraNotFoundException {
        List<Integer> ans = new ArrayList<>();
        Integer value = (Integer) template.opsForHash().get("room:" + room, "puntajeEquipoA");
        Integer value2 = (Integer) template.opsForHash().get("room:" + room, "puntajeEquipoB");
        if (value != null) {
            ans.add(value);
            ans.add(value2);
        } else {
            throw new CamposDeGuerraNotFoundException("La Room ingresada no existe!");
        }

        return ans;
    }

    @Override
    public void addRoom(Room room) throws CamposDeGuerraPersistenceException {
        String value = (String) template.opsForHash().get("room:" + room, "cantidadActualJugadores");
        if (value != null) {
            throw new CamposDeGuerraPersistenceException("el identificador de la sala ya esta");
        } else {
            template.opsForHash().put("room:" + room.getId(), "id", room.getId());
            template.opsForHash().put("room:" + room.getId(), "puntajeEquipoA", 0);
            template.opsForHash().put("room:" + room.getId(), "puntajeEquipoB", 0);
            template.opsForHash().put("room:" + room.getId(), "banderaA", "");
            template.opsForHash().put("room:" + room.getId(), "banderaB", "");
            template.opsForHash().put("room:" + room.getId(), "banderaATomada", false);
            template.opsForHash().put("room:" + room.getId(), "banderaBTomada", false);
            template.opsForHash().put("room:" + room.getId(), "tiempo", room.getTiempo());
            template.opsForHash().put("room:" + room.getId(), "cantidadMaximaJugadores", room.getCantidadMaximaJugadores());
            template.opsForHash().put("room:" + room.getId(), "cantidadActualJugadores", 0);
            template.opsForHash().put("room:" + room.getId(), "potenciadores", room.getPotenciadores());
            template.opsForHash().put("room:" + room.getId(), "capturasParaGanar", room.getCapturasParaGanar());
            template.opsForHash().put("room:" + room.getId(), "tipoMaquina", room.getTipoMaquina());
            template.opsForHash().put("room:" + room.getId(), "estado", "Nojugando");
            String[] args = new String[1];
            args[0] = room.getId().toString();
            template.opsForSet().add(("rooms"), args);
        }
    }

    @Override
    public List<Room> getAllRooms() throws CamposDeGuerraNotFoundException {
        List<Room> asn = new ArrayList<>();
        Set<String> value2 = template.opsForSet().members("rooms");
        for (String s : value2) {
            asn.add(getRoom(Integer.parseInt(s)));
        }
        return asn;

    }

    @Override
    public Room getRoom(Integer s) throws CamposDeGuerraNotFoundException {
        Room temp=new Room(s);
        String value = (String) template.opsForHash().get("room:" + s, "cantidadActualJugadores");
        if (value == null) {
            throw new CamposDeGuerraNotFoundException("La sala no existe");
        }
        else{
            temp.setPuntajeEquipoA((AtomicInteger) template.opsForHash().get("room:" + s, "puntajeEquipoA"));
            temp.setPuntajeEquipoA((AtomicInteger) template.opsForHash().get("room:" + s, "puntajeEquipoB"));
            temp.setBanderaA((String) template.opsForHash().get("room:" + s, "banderaA"));
            temp.setBanderaB((String) template.opsForHash().get("room:" + s, "banderaB"));
            temp.setBanderaATomada((AtomicBoolean) template.opsForHash().get("room:" + s, "banderaATomada"));
            temp.setBanderaBTomada((AtomicBoolean) template.opsForHash().get("room:" + s, "banderaBTomada"));
            temp.setTiempo((Integer) template.opsForHash().get("room:" + s, "tiempo"));
            temp.setCantidadMaximaJugadores((Integer) template.opsForHash().get("room:" + s, "cantidadMaximaJugadores"));
            temp.setCantidadActualJugadores((AtomicInteger) template.opsForHash().get("room:" + s, "cantidadActualJugadores"));
            temp.setPotenciadores((String) template.opsForHash().get("room:" + s, "potenciadores"));
            temp.setCapturasParaGanar((Integer) template.opsForHash().get("room:" + s, "capturasParaGanar"));
            temp.setTipoMaquina((String) template.opsForHash().get("room:" + s, "tipoMaquina"));
            temp.setEstado((String) template.opsForHash().get("room:" + s, "estado"));
            Set<Usuario> temp2 = getAllUsuariosFromRoom(s);
            ConcurrentLinkedQueue<Usuario> equipoA = new ConcurrentLinkedQueue<>();
            ConcurrentLinkedQueue<Usuario> equipoB = new ConcurrentLinkedQueue<>();
            for (Usuario u : temp2) {
                if (u.getEquipo().equals("A")) {
                    equipoA.add(u);
                } else {
                    equipoB.add(u);
                }
            }
            temp.setEquipoA(equipoA);
            temp.setEquipoA(equipoB);
        }
        return temp;
    }

    @Override
    public void deleteRoom(Integer idSala) throws CamposDeGuerraNotFoundException {
        String value = (String) template.opsForHash().get("room:" + idSala, "cantidadActualJugadores");
        if (value == null) {
            throw new CamposDeGuerraNotFoundException("La sala no existe");
        } else {
            template.opsForHash().delete("room:" + idSala, "id");
            template.opsForHash().delete("room:" + idSala, "puntajeEquipoA");
            template.opsForHash().delete("room:" + idSala, "puntajeEquipoB");
            template.opsForHash().delete("room:" + idSala, "banderaA");
            template.opsForHash().delete("room:" + idSala, "banderaB");
            template.opsForHash().delete("room:" + idSala, "banderaATomada");
            template.opsForHash().delete("room:" + idSala, "banderaBTomada");
            template.opsForHash().delete("room:" + idSala, "tiempo");
            template.opsForHash().delete("room:" + idSala, "cantidadMaximaJugadores");
            template.opsForHash().delete("room:" + idSala, "cantidadActualJugadores");
            template.opsForHash().delete("room:" + idSala, "potenciadores");
            template.opsForHash().delete("room:" + idSala, "capturasParaGanar");
            template.opsForHash().delete("room:" + idSala, "tipoMaquina");
            template.opsForHash().delete("room:" + idSala, "estado");
            Set<String> value2 = template.opsForSet().members("room:" + idSala + ":users");
            for (String s : value2) {
                template.opsForSet().remove("room:" + idSala + ":users", s);
                template.opsForHash().delete("room:" + idSala + ":" + s, "id");
                template.opsForHash().delete("room:" + idSala + ":" + s, "vida");
                template.opsForHash().delete("room:" + idSala + ":" + s, "puntaje");
                template.opsForHash().delete("room:" + idSala + ":" + s, "equipo");
                template.opsForHash().delete("room:" + idSala + ":" + s + ":maquina", "x");
                template.opsForHash().delete("room:" + idSala + ":" + s + ":maquina", "y");
                template.opsForHash().delete("room:" + idSala + ":" + s + ":maquina", "speed");
                template.opsForHash().delete("room:" + idSala + ":" + s + ":maquina", "attack");
                template.opsForHash().delete("room:" + idSala + ":" + s + ":maquina", "nombreImagen");
                template.opsForHash().delete("room:" + idSala + ":" + s + ":maquina", "bullets");
                template.opsForHash().delete("room:" + idSala + ":" + s + ":maquina", "direction");
            }

        }
    }

}
