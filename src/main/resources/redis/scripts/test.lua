-- test.lua
redis.call("HINCRBY", KEYS[1]..":"..ARGV[1],"cantidadActualJugadores", 1)

local actualjugadores = redis.call("HGET",KEYS[1],"cantidadActualJugadores")
local equipo="";
    if actualjugadores % 2 == 0
	then
        equipo="B"
    else
        equipo="A"
    end
redis.call("HSET", KEYS[1]..":"..ARGV[1],"id", ARGV[1])
redis.call("HSET", KEYS[1]..":"..ARGV[1],"equipo", equipo)
redis.call("HSET", KEYS[1]..":"..ARGV[1],"vida", ARGV[2])
redis.call("HSET", KEYS[1]..":"..ARGV[1],"puntaje", ARGV[3])
redis.call("SADD", "room:"..KEYS[1]..":users", ARGV[1])

return "fin"