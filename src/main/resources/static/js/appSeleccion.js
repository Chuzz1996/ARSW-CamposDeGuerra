
/* global deleteUser, apiclient, getUser, postUserRoom, getMyTeam, conectar, getRamdonRoom */

var appSeleccion = (function () {

    var api = apiclient;
    var myUser;
    var idRoom;

    class Usuario {
        constructor(tipoMaquina, userName, puntaje,vida, equipo) {
            this.tipoMaquina = tipoMaquina;
            this.userName = userName;
            this.puntaje = puntaje;
            this.equipo = equipo;
            this.vida=vida;
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
    

    class Room {
        constructor(id,tipoMaquina,tiempo, cantidadJugadores,potenciadores,capturasPartida,estado) {
            this.puntajeEquipoA = 0;
            this.puntajeEquipoB = 0;
            this.equipoA=[];
            this.equipoB=[];
            this.id = id;
            this.banderaA="";
            this.banderaB="";
            this.tipoMaquina=tipoMaquina;
            this.banderaATomada=false;
            this.banderaBTomada=false;
            this.tiempo=tiempo;
            this.cantidadJugadores=cantidadJugadores;
            this.potenciadores=potenciadores;
            this.capturasPartida=capturasPartida;
            this.estado=estado;
        }
    }
    
    class Bullet {
        constructor(x, y, direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }
    }
    
    
    var createRoom = function(){
        var countDown_overlay = 'position:absolute;top:50%;left:50%;background-color:black;z-index:1002;overflow:auto;width:400px;text-align:center;height:400px;margin-left:-200px;margin-top:-200px';
        var temp= '<div id="overLay" style="' + countDown_overlay + '"> <p>Id Sala</p> <input id="idSalaNew" type="number" min="0" max=totalSalas/>  <p><strong>Maquina Disponible</strong> <select id="tipoMaquina"><option value="Destructora">Destructora</option><option value="Protectora">Protectora</option><option value="Veloz">Veloz</option></select> <p>  <strong>Tiempo de juego</strong> <input id="tiempo" type="number" min="0" max=totalSalas/> <p><strong>Cantidad de jugadores</strong> <select id="numeroJugadores"><option value="2">2</option><option value="4">4</option><option value="6">6</option></select> <p><strong>Potenciadores disponibles</strong> <select id="tipoPotenciador"><option value="Velocidad">Velocidad</option><option value="Vida">Vida</option><option value="Daño">Daño</option></select>  <p><strong>Capturas de bandera para ganar</strong> <input id="capturas" type="number" min="0" max=totalSalas/> <button onclick="appSeleccion.crearSala()" class=btn btn-outline-primary >Create Match</button> </div>';
        $('body').append(temp);
        console.info("supuestamente agrego overlay");
    }
    
    var joinRoom = function(){
        var getPromise = api.getAllRooms(function(data){
            var totalSalas = parseInt(data.length);
            var countDown_overlay = 'position:absolute;top:50%;left:50%;background-color:black;z-index:1002;overflow:auto;width:400px;text-align:center;height:400px;margin-left:-200px;margin-top:-200px';
            $('body').append('<div id="overLay" style="' + countDown_overlay + '"><span id="time" style="color:white" >Salas disponibles</span></br><table id="table1"  style="width:100%"><tr><th>ID Sala</th><th>Cantidad de Jugadores</th></tr></table> <input id="idSala" type="number" min="0" max=totalSalas/> <button onclick="appSeleccion.updateIdSala()" class=btn btn-outline-primary >Join Match</button></div>');
            for(var i=0;i<data.length;i++){
                var cant =parseInt(data[i].equipoA.length)+ parseInt(data[i].equipoB.length);
                $('#table1').append("<tr> <th>" + data[i].id + "</th> <th>" + cant +"/"+data[i].cantidadJugadores + "</th> </tr>");
            }
        });
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
        var tempUser = new Usuario(myUser.tipoMaquina, myUser.userName, myUser.puntaje,myUser.vida, myUser.equipo);
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
        
        stompClient.connect({}, function (frame) {
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
    
    var postRoom = function () {
        var idSalaNew=document.getElementById("idSalaNew").value;idSalaNew=parseInt(idSalaNew);
        var tipoMaquina=document.getElementById("tipoMaquina").value;
        var tiempo=document.getElementById("tiempo").value;tiempo=parseInt(tiempo);
        var cantidadJugadores=document.getElementById("numeroJugadores").value;cantidadJugadores=parseInt(cantidadJugadores);
        var tipoPotenciador=document.getElementById("tipoPotenciador").value;
        var capturas=document.getElementById("capturas").value;capturas=parseInt(capturas);
        var tempRoom = new Room(idSalaNew, tipoMaquina, tiempo,cantidadJugadores,tipoPotenciador,capturas,"No jugando");
        var postPromise = api.postRoom(tempRoom);
        postPromise.then(
                function () {
                    console.info("Success,the Room was created correctly");
                },
                function () {
                    console.info("Sorry,there was a problem with the Room");
                }
        );
        return postPromise;
    };

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
            createRoom();
        },
        unirExistente: function () {
            joinRoom();
        },
        updateIdSala: function () {
            idRoom=document.getElementById("idSala").value;
            if(idRoom!==""){
                getUser().then(postUserRoom).then(getMyTeam).then(conectar);
            }
            else{
                alert("Ingrese una sala valida");
            }
        },
        crearSala:function (){
            idRoom=document.getElementById("idSalaNew").value;
            if(idRoom!==""){
                getUser().then(postRoom).then(postUserRoom).then(getMyTeam).then(conectar);
            }
            else{
                alert("Ingrese una sala valida");
            }
            
            
            
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