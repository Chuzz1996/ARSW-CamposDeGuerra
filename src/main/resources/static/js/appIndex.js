
var appIndex = (function () {

    var api = apiclient;

    class Usuario {
        constructor(tipoMaquina, userName, puntaje,equipo) {
            this.tipoMaquina = tipoMaquina;
            this.userName = userName;
            this.puntaje = puntaje;
            this.equipo = equipo;
        }
    }

    class Maquina {
        constructor(live, speed, attack) {
            this.live = live;
            this.speed = speed;
            this.attack = attack;
        }
    }


    var postUser = function () {
        localStorage.setItem("user", document.getElementById("username").value);
        var newUsuario = new Usuario(null, localStorage.getItem("user"), 0, 0);
        var postPromise = api.postUser(newUsuario);
        postPromise.then(
                function () {
                    var newURL = window.location.protocol + "//" + window.location.host + "/" + "seleccionPartida.html";
                    window.location.replace(newURL);
                },
                function () {
                    alert("You can't play with this username, it's playing");
                }
        );

    };


    return {
        addUser: function () {
            var username = document.getElementById("username").value;
            if (username === "") {
                alert("Escriba un usuario valido");
            }
            else{
                postUser();
            }
            
        },
        getCurrentUser: function () {
            return currentUser;
        }
    };

}());


