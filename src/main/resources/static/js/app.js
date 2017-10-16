var app = (function () {

    var api = apiclient;

    class Usuario {
        constructor(tipoMaquina, userName, puntaje) {
            this.tipoMaquina = tipoMaquina;
            this.userName = userName;
            this.puntaje = puntaje;
        }
    };


    var postUser = function () {
        var newUsuario = new Usuario("null", document.getElementById('username').value, "0");
        var postPromise = api.postUser();
        postPromise.then(
                function () {
                    alert("Add user");
                },
                function () {
                    alert("This user is already defined");
                }
        );
    };

    return {
        addUser: function () {
            postUser();
        }
    };

}());


