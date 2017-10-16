/* global postUser, apiclient */

var app = (function () {

    var api = apiclient;
    var currentUser ="Predefinido";

    class Usuario {
        constructor(tipoMaquina, userName, puntaje) {
            this.tipoMaquina = tipoMaquina;
            this.userName = userName;
            this.puntaje = puntaje;
        }
    };
    
    class Maquina{
        constructor(live, speed, attack){
            this.live = live;
            this.speed = speed;
            this.attack = attack;
        }
    }


    var postUser = function () {
        currentUser =document.getElementById("username").value; 
        var newUsuario = new Usuario(null, currentUser, "0");
        var postPromise = api.postUser(newUsuario);
        postPromise.then(
                function () {
                    alert("Add user");
                },
                function () {
                    alert("This user is already defined");
                }
        );
        return postPromise;
    };

    return {
        addUser: function () {
            postUser();
        },
        getCurrentUser: function (){
                return currentUser;
        }
    };

}());


