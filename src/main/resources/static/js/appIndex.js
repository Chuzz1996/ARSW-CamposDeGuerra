
/* global apiclient, postUser */

var appIndex = (function () {

    var api = apiclient;

    class Usuario {
        constructor(tipoMaquina, userName, puntaje,vida,equipo) {
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


    var postUser = function () {
        sessionStorage.setItem("user", document.getElementById("username").value);
        var maquina=new Maquina(0,0,1,[]);
        var newUsuario = new Usuario(maquina, sessionStorage.getItem("user"),0,0,"Ninguno");
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
            return sessionStorage.getItem("user");
        }
    };

}());


