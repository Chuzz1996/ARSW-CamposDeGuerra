
var appSeleccion = (function () {

    var api = apiclient;

    class Usuario {
        constructor(tipoMaquina, userName, puntaje) {
            this.tipoMaquina = tipoMaquina;
            this.userName = userName;
            this.puntaje = puntaje;
        }
    }
    ;

    class Maquina{
        constructor(live, speed, attack){
            this.live = live;
            this.speed = speed;
            this.attack = attack;
        }
    }

    var connectAndSubscribe = function () {
        console.info('Connecting to WS...');
        var socket = new SockJS('/stompendpoint');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            
        });
    };
    
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

    return {
        logout:function(){
            deleteUser();
        },
        cuenta:function(){
            return localStorage.getItem("user");
        }
    };

}());


