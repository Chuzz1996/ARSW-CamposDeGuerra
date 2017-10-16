var app = (function () {

    class Usuario{
        constructor(tipoMaquina,userName,puntaje){
            this.tipoMaquina=tipoMaquina;
            this.userName=userName;
            this.puntaje=puntaje;
        }   
    };
    
    var api = apiclient;

    var addNewUser = function(){
        var newUsuario = new Usuario("null",document.getElementById('username').value,"0");
        var promesa = api.addUser().then(
                function(){
                    alert("PASO");
                },
                function(){
                    alert("Name already exist");
                }
        )
    }

    return {
        
        addUser:function(){
            addNewUser();
        }
    };

}());


