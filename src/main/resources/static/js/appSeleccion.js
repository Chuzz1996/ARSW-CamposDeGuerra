
/* global deleteUser, apiclient, getUser, postUserRoom, getMyTeam, conectar, getRamdonRoom */

var appSeleccion = (function () {

    var api = apiclient;
    var myUser;
    var idRoom;

    class Usuario {
        constructor(tipoMaquina, userName, puntaje, equipo) {
            this.tipoMaquina = tipoMaquina;
            this.userName = userName;
            this.puntaje = puntaje;
            this.equipo = equipo;
        }
    }

    class Maquina {
        constructor(x, y, direction, bullets) {
            this.x = x;
            this.y = y;
            this.direction = direction;
            this.bullets = bullets;
        }
    }

    class Bullet {
        constructor(x, y, direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }
    }

    var deleteUser = function () {
        var currentUser = sessionStorage.getItem("user");
        var deletePromise = api.deleteUser(currentUser);
        deletePromise.then(
                function () {
                    alert("Logout Success");
                    var newURL = window.location.protocol + "//" + window.location.host + "/" + "index.html";
                    window.location.replace(newURL);
                },
                function () {
                    alert("Sorry,there was a problem with logout");
                }
        );
    };

    var getUser = function () {
        var currentUser = sessionStorage.getItem("user");
        var getPromise = api.getUser(currentUser, function (data) {
            myUser = data;
        });
        getPromise.then(
                function () {
                    console.info("Cargo Usuario");
                },
                function () {
                    console.info("ERROR! no Cargo Usuario");
                }
        );
        return getPromise;
    };

    var postUserRoom = function () {
        var tempUser = new Usuario(myUser.tipoMaquina, myUser.userName, myUser.puntaje, myUser.equipo);
        var postPromise = api.postUserRoom(idRoom, tempUser);
        postPromise.then(
                function () {
                    sessionStorage.setItem("idRoom", idRoom);
                },
                function () {
                    console.info("Sorry,there was a problem with the Room");

                }
        );
        return postPromise;
    };

    var getMyTeam = function () {
        var getPromise = api.getMyTeam(sessionStorage.getItem("user"), sessionStorage.getItem("idRoom"), function (data) {
            sessionStorage.setItem("myTeam", data);
        });
        getPromise.then(
                function () {
                    console.info("obtuve mi equipo: " + sessionStorage.getItem("myTeam"));
                },
                function () {
                    alert("Lo sentimos, hubo un error al obtener el equipo");
                }
        );
        return getPromise;
    };

    var conectar = function () {
        var countDown_overlay = 'position:absolute;top:50%;left:50%;background-color:black;z-index:1002;overflow:auto;width:400px;text-align:center;height:400px;margin-left:-200px;margin-top:-200px';
        $('body').append('<div id="overLay" style="' + countDown_overlay + '"><span id="time" style="color:white" >Esperando más jugadores ... </span> <img src="/images/loading.gif" class="position: absolute; left: 0; top: 0; right: 0; bottom: 0; margin: auto;"></div>');
        console.info('Connecting to WS...');
        var socket = new SockJS('/stompendpoint');
        stompClient = Stomp.over(socket);
        sessionStorage.setItem("pos", "noactualizado");
        stompClient.connect({}, function (frame) {
            stompClient.subscribe('/topic/sala.' + sessionStorage.getItem("idRoom") + "/pos", function (eventbody) {
                var object = eventbody.body;
                if (sessionStorage.getItem("pos") === "noactualizado") {
                    sessionStorage.setItem("pos", object);
                } else {
                    console.info("NO ASIGNO POSICION");
                }
            });
            stompClient.subscribe('/topic/sala.' + sessionStorage.getItem("idRoom"), function (eventbody) {
                var newURL = window.location.protocol + "//" + window.location.host + "/" + "juego.html";
                setTimeout(function (){window.location.replace(newURL);stompClient.disconnect();}, 4000);
            });
        });
        setTimeout(function () {
            stompClient.send("/app/sala." + sessionStorage.getItem("idRoom"), {}, 'listo');
        }, 4000);
    };

    var getRamdonRoom = function () {
        var getPromise = api.getFreeRoom(function (data) {
            idRoom = data;
        });
        getPromise.then(
                function () {
                    console.info("Encontro sala libre: " + idRoom);
                },
                function () {
                    alert("Lo sentimos, no hay salas disponibles intente mas tarde");
                }
        );
        return getPromise;
    };
    
    var createRoom = function(){
    }
    
    var joinRoom = function(){
        var getPromise = api.getAllRooms(function(data){
            var totalSalas = parseInt(data.length);
            var countDown_overlay = 'position:absolute;top:50%;left:50%;background-color:black;z-index:1002;overflow:auto;width:400px;text-align:center;height:400px;margin-left:-200px;margin-top:-200px';
            $('body').append('<div id="overLay" style="' + countDown_overlay + '"><span id="time" style="color:white" >Salas disponibles</span></br><table id="table1"  style="width:100%"><tr><th>ID Sala</th><th>Cantidad de Jugadores</th></tr></table> <input id="idSala" type="number" min="0" max=totalSalas/> <button class=btn btn-outline-primary >Join Match</button></div>');
            console.info(data.length);
            for(var i=0;i<data.length;i++){
                var cant =parseInt(data[i].equipoA.length)+ parseInt(data[i].equipoB.length);
                $('#table1').append("<tr> <th>" + data[i].id + "</th> <th>" + cant +"/"+data[i].cantidadJugadores + "</th> </tr>");
            }
        });
    }

    var counter = 10;



    return {
        logout: function () {
            deleteUser();
        },
        cuenta: function () {
            return sessionStorage.getItem("user");
        },
        partidaRandom: function () {
            getRamdonRoom().then(getUser).then(postUserRoom).then(getMyTeam).then(conectar);
        },
        nuevaPartida: function () {
            
        },
        unirExistente: function () {
            joinRoom();
        }
    };

}());


/*
 function Show_Countdown() {
 
 var countDown_overlay = 'position:absolute;' +
 'top:50%;' +
 'left:50%;' +
 'background-color:white;' +
 'z-index:1002;' +
 'overflow:auto;' +
 'width:400px;' +
 'text-align:center;' +
 'height:400px;' +
 'margin-left:-200px;' +
 'margin-top:-200px';
 
 $('body').append('<div id="overLay" style="' + countDown_overlay + '"><span id="time">Esperando más jugadores ... </span></div>');
 
 var timer = setInterval(function () {
 document.getElementById("time").innerHTML = counter;
 counter = (counter - 1);
 
 if (counter < 0)
 {
 
 clearInterval(timer);
 document.getElementById("overLay").style.display = 'none';
 var newURL = window.location.protocol + "//" + window.location.host + "/" + "juego.html";
 window.location.replace(newURL);
 }
 }, 1000);
 }
 */