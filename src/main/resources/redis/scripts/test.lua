-- test.lua
local actualjugadores = redis.call("HGET",KEYS[1],"cantidadActualJugadores")
actualjugadores=actualjugadores+1;
local equipo="";
    if actualjugadores % 2 == 0
	then
        equipo="B"
    else
        equipo="A"
    end

redis.call("HSET", KEYS[1]..":"..ARGV[1],"equipo", equipo)

return "fin"