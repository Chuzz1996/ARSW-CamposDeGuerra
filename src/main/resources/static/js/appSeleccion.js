
/* global deleteUser, apiclient */

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

    class Maquina{
        constructor(live, speed, attack){
            this.live = live;
            this.speed = speed;
            this.attack = attack;
        }
    }


    
    var deleteUser = function () {
        var currentUser = localStorage.getItem("user");
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
    
    var connectAndSubscribe = function (room) {
        console.info('Connecting to WS...');
        var socket = new SockJS('/stompendpoint');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/newpoint.' + id, function (eventbody) {
                var pointRecived = JSON.parse(eventbody.body);
                addPointToCanvas(pointRecived);
            });
            stompClient.subscribe('/topic/newpolygon.' + id, function (eventbody) {
                var polygonRecived = JSON.parse(eventbody.body);
                addPolygonToCanvas(polygonRecived);
            });
        });
    };
    
    var getUser= function (){
        var currentUser = localStorage.getItem("user");
        var getPromise = api.getUser(currentUser,function (data){
            myUser=data;
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
        var tempUser = new Usuario(myUser.tipoMaquina, myUser.userName, myUser.puntaje, myUser.equipo)
        var postPromise = api.postUserRoom(idRoom,tempUser);
        postPromise.then(
                function () {
                    console.info("Log in Room Success");
                },
                function () {
                    console.info("Sorry,there was a problem with the Room");
                }
        );
        return postPromise;
    };
    
    var getRamdonRoom = function () {
        var getPromise = api.getFreeRoom(function (data){
            idRoom=data;
        });
        getPromise.then(
                function () {
                    console.info("Encontro sala libre: "+ idRoom);
                },
                function () {
                    alert("Lo sentimos, no hay salas disponibles intente mas tarde");
                }
        );
        return getPromise; 
    };
    

    return {
        logout:function(){
            deleteUser();
        },
        cuenta:function(){
            return localStorage.getItem("user");
        },
        partidaRandom:function(){
            getRamdonRoom().then(getUser).then(postUserRoom);
        },
        nuevaPartida:function(){
            
        },
        unirExistente:function(){
            
        },
    };

}());


