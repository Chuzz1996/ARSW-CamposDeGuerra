-- test.lua
local actualj = redis.call("HGET",KEYS[1],"cantidadActualJugadores")
actualj=actualj+1
local equipo=""
if actualj % 2 == 0 
then
    equipo="B"
else
    equipo="A"
end
redis.call("HSET", KEYS[1]..":"..ARGV[1],"id", ARGV[1])
redis.call("HSET", KEYS[1]..":"..ARGV[1],"equipo", equipo)
redis.call("HSET", KEYS[1]..":"..ARGV[1],"vida", ARGV[2])
redis.call("HSET", KEYS[1]..":"..ARGV[1],"puntaje", ARGV[3])
redis.call("SADD", KEYS[1]..":users", ARGV[1])
redis.call("HSET", KEYS[1],"cantidadActualJugadores",actualj)

return ""
