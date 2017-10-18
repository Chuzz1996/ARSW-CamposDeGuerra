
/* global deleteUser, apiclient */

var appSeleccion = (function () {

    var api = apiclient;

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

    return {
        logout:function(){
            deleteUser();
        },
        cuenta:function(){
            return localStorage.getItem("user");
        }
    };

}());


