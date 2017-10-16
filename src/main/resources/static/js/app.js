/* global postUser, apiclient */

var app = (function () {

    var api = apiclient;
    var currentUser = "Predefinido";

    class Usuario {
        constructor(tipoMaquina, userName, puntaje) {
            this.tipoMaquina = tipoMaquina;
            this.userName = userName;
            this.puntaje = puntaje;
        }
    }
    ;


    var postUser = function () {
        currentUser = document.getElementById("username").value;
        var newUsuario = new Usuario("null", currentUser, "0");
        var postPromise = api.postUser(newUsuario);
        postPromise.then(
                function () {
                    alert("Added user");
                    window.location.replace("http://localhost:8080/seleccionPartida.html");
                },
                function () {
                    alert("This user is already defined");
                }
        );

    };

    return {
        addUser: function () {
            postUser();
        },
        getCurrentUser: function () {
            return currentUser;
        }
    };

}());


