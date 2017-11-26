/* global apiclient */

var endGame = (function () {
    
    var conectar = function () {
        
        console.info('Connecting to WS...');
        var socket = new SockJS('/stompendpoint');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            stompClient.subscribe('/topic/sala.' + sessionStorage.getItem("idRoom") + "/datos", function (eventbody) {});
        });
        setTimeout(function () {
            stompClient.send("/app/sala." + sessionStorage.getItem("idRoom") + "/datos", {}, 'listo');
        }, 4000);
    };
    
    
    
    var getAllUsers = function () {
        var getPromise = apiclient.getAllUsersRoom(sessionStorage.getItem("idRoom"), function (lista1) {
            reducir = function (objeto) {
                return objeto2 = {"userName": objeto.userName, "equipo": objeto.equipo, "puntaje": objeto.puntaje};
            };
            $("#table1").find("tr:gt(0)").remove();
            lista = lista1.map(reducir);
            anadir = function (objeto) {
                $(document).ready(function () {
                    if (sessionStorage.getItem("PuntosA") > sessionStorage.getItem("PuntosB")) {
                        if (objeto.equipo === "A") {
                            $('#table1').append("<tr class='success' ><th>" + objeto.userName + "</th><th>" + objeto.equipo + "</th><th>" + objeto.puntaje + "</th></tr>");
                        } else {
                            $('#table1').append("<tr class='danger' ><th>" + objeto.userName + "</th><th>" + objeto.equipo + "</th><th>" + objeto.puntaje + "</th></tr>");
                        }

                    } else if (sessionStorage.getItem("PuntosA") < sessionStorage.getItem("PuntosB")) {
                        if (objeto.equipo === "B") {
                            $('#table1').append("<tr class='success' ><th>" + objeto.userName + "</th><th>" + objeto.equipo + "</th><th>" + objeto.puntaje + "</th></tr>");
                        } else {
                            $('#table1').append("<tr class='danger' ><th>" + objeto.userName + "</th><th>" + objeto.equipo + "</th><th>" + objeto.puntaje + "</th></tr>");
                        }
                    } else {
                        $('#table1').append("<tr class='warning' ><th>" + objeto.userName + "</th><th>" + objeto.equipo + "</th><th>" + objeto.puntaje + "</th></tr>");
                    }

                });
            };
            lista.map(anadir);
        });
        
        getPromise.then(
                function () {
                    if (sessionStorage.getItem("PuntosA") > sessionStorage.getItem("puntosB")) {
                        setTimeout(alert("Gano el equipo A!"),2000);
                        

                    } else if (sessionStorage.getItem("PuntosA") < sessionStorage.getItem("puntosB")) {
                        setTimeout(alert("Gano el equipo B!"),2000);
                    } else {
                        setTimeout(alert("Ningun equipo gano, se considera empate!"),2000);
                    }
                },
                function () {
                    alert("ERROR! no Cargo Todos Usuario");
                }
        );
        return getPromise;
    };
    
    
    
    return {
    actualizarPuntajes: function () {
            getAllUsers().then(conectar);
        }
    };
}());    
    