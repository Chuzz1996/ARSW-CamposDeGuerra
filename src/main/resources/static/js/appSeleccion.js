
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
        var tempUser = new Usuario(myUser.tipoMaquina, myUser.userName, myUser.puntaje, myUser.equipo)
        var postPromise = api.postUserRoom(idRoom, tempUser);
        postPromise.then(
                function () {
                    sessionStorage.setItem("idRoom", idRoom);
                    api.getMyTeam(sessionStorage.getItem("user"), sessionStorage.getItem("idRoom"),function(data){sessionStorage.setItem("myTeam",data);});
                    var newURL = window.location.protocol + "//" + window.location.host + "/" + "juego.html";
                    window.location.replace(newURL);
                },
                function () {
                    console.info("Sorry,there was a problem with the Room");
                }
        );
        return postPromise;
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


    return {
        logout: function () {
            deleteUser();
        },
        cuenta: function () {
            return sessionStorage.getItem("user");
        },
        partidaRandom: function () {
            getRamdonRoom().then(getUser).then(postUserRoom);
        },
        nuevaPartida: function () {

        },
        unirExistente: function () {

        },
    };

}());


