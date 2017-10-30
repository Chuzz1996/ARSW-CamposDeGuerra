var endGame = (function () {
    var getAllUsers = function () {
        var getPromise = apiclient.getRoom(sessionStorage.getItem("idRoom"), function (lista1) {
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
                    if (puntosA > puntosB) {
                        setTimeout(alert("Gano el equipo A!"),2000);


                    } else if (puntosA < puntosB) {
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
    
    var clearRoom = function () {
        var deletePromise = apiclient.deleteAllUsersRoom(sessionStorage.getItem("idRoom"));

        deletePromise.then(
                function () {
                    console.info("Se limpio la Room");

                },
                function () {
                    console.info("No se pudo limpiar la Room");
                }
        );
        return deletePromise;
    };
    
    return {
    actualizarPuntajes: function () {
            getAllUsers().then(clearRoom);

        }
    };
}());    
    