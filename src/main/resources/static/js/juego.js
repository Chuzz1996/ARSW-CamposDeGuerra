/* global updateGameArea, updateBullets, graficarBalas, graficarBalaOponente, actualizarTrayectoriaBalas */

var juego = (function () {

    class Usuario {
        constructor(nombre, maquina, puntaje,equipo,vida) {
            this.nombre = nombre;
            this.maquina = maquina;
            this.puntaje = puntaje;
            this.equipo = equipo;
            this.vida = vida;
        }
        ;
    }
    ;

    class Maquina {
        constructor(x, y, direction, bullets) {
            this.x = x;
            this.y = y;
            this.direction = direction;
            this.bullets = bullets;
        }
        ;
    }
    ;

    class Bulletcita {
        constructor(x, y, direction,equipo,h,w) {
            this.x = x;
            this.y = y;
            this.direction = direction;
            this.equipo = equipo;
        }
    }



    var myGamePiece;
    var oponents = [];
    var aliados = [];
    var myGameArea;

    var stompClient = null;

    var puntaje = 0;

    var directionImageTank = "/images/tank";
    var directionImageShoot = "/images/bullet";
    var directionShoot = 1;
    var updateGameArea;





    var graficarBala = function (bullet) {
        var temp2 = new Image();
        temp2.src = directionImageShoot + bullet.direction + ".png";
        var h, w, sx, sy, deltaX, deltaY;
        if (bullet.direction === 3) {
            h = 30;
            w = 10;
            sx = 5;
            sy = 10;
            dx = 0;
            dy = -1;
        } else if (bullet.direction === 4) {
            h = 30;
            w = 10;
            sx = 5;
            sy = 0;
            dx = 0;
            dy = 1;
        } else if (bullet.direction === 2) {
            h = 10;
            w = 30;
            sx = 0;
            sy = 0;
            dx = 1;
            dy = 0;
        } else {
            h = 10;
            w = 30;
            sx = 10;
            sy = 0;
            dx = -1;
            dy = 0;
        }
        if(myGamePiece.crashWith(bullet,h,w)){
            myGameArea.context.fillStyle = "#A9A9A9";
            myGameArea.context.fillRect(bullet.x + sx + dx, bullet.y + sy + dy, w, h);
            let image = new Image();
            image.src = "/images/explosion.png";
            myGameArea.context.drawImage(image, myGamePiece.x , myGamePiece.y , 30, 30);
            console.info("ENTRO");
        }else{
            myGameArea.context.fillStyle = "#A9A9A9";
            myGameArea.context.fillRect(bullet.x + sx + dx, bullet.y + sy + dy, w, h);
            myGameArea.context.drawImage(temp2, bullet.x + sx, bullet.y + sy, w, h);
        }
        
    };

    var actualizarTrayectoriaBalas = function () {
        var temp = myGamePiece.shoots;
        var borrar = [];
        for (let i = 0; i < temp.length; i++) {
            if (temp[i].dir === 3) {
                myGamePiece.shoots[i].y += 1;
            } else if (temp[i].dir === 4) {
                myGamePiece.shoots[i].y -= 1;
            } else if (temp[i].dir === 2) {
                myGamePiece.shoots[i].x -= 1;
            } else {
                myGamePiece.shoots[i].x += 1;
            }
            if (temp[i].x >= myGameArea.canvas.width || temp[i].x <= -30 || temp[i].y >= myGameArea.canvas.height || temp[i].y <= -30) {
                borrar.push(i);
            }
            var temp2 = new Bulletcita(myGamePiece.shoots[i].x, myGamePiece.shoots[i].y, myGamePiece.shoots[i].dir,sessionStorage.getItem("myTeam"));
            stompClient.send("/topic/sala." + sessionStorage.getItem("idRoom") + "/bullets", {}, JSON.stringify(temp2));
        }
        for (let i = 0; i < borrar.length; i++) {
            if (i === 0) {
                myGamePiece.shoots.splice(borrar[i], 1);
            } else {
                myGamePiece.shoots.splice(borrar[i] - 1, 1);
            }
        }
    };


    function Bullet(width, height, color, x, y, type, dir) {
        this.gamearea = myGameArea;
        if (type === "image") {
            this.image = new Image();
            this.image.src = color;
        }
        this.dir = dir;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }
    ;



    updateGameArea = function () {
        myGameArea.clear();
        myGameArea.context.fillStyle = "#A9A9A9";
        myGameArea.context.fillRect(0, 0, myGameArea.canvas.width, myGameArea.canvas.height);
        myGamePiece.newPos();
        myGamePiece.update();
    };

    var updateBullets = function (bullets) {
        var ctx = myGameArea.context;
        ctx.drawImage(this.image, this.x, this.y, this.width, this.height);
    }

    function Component(width, height, color, x, y, type, bullets, direction, propietario, equipo,vida) {
        this.gamearea = myGameArea;
        if (type === "image") {
            this.image = new Image();
            this.image.src = color;
        }
        this.propietario = propietario;
        this.vida = vida;
        this.equipo=equipo;
        this.width = width;
        this.height = height;
        this.speedX = 0;
        this.speedY = 0;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.shoots = bullets;

        this.newPos = function () {
            this.x += this.speedX;
            this.y += this.speedY;
        };

        this.update = function () {
            //Dibujarme
            var ctx = myGameArea.context;
            console.info(this.vida);
            if(this.vida > 0){
                ctx.drawImage(this.image, this.x, this.y, this.width, this.height);
            }
            //Dibujar Oponentes
            for (var i = 0; i < oponents.length; i++) {
                if (oponents[i].propietario !== sessionStorage.getItem("user") && oponents[i].vida > 0) {
                    var temp = new Image();
                    temp.src = directionImageTank + oponents[i].direction + ".png";
                    ctx.drawImage(temp, oponents[i].x, oponents[i].y, 30, 30);
                }
            }
            //Dibujar aliados
            for (var i = 0; i < aliados.length; i++) {
                if (aliados[i].propietario !== sessionStorage.getItem("user") && oponents[i].vida > 0) {
                    var temp = new Image();
                    temp.src = directionImageTank + aliados[i].direction + ".png";
                    ctx.drawImage(temp, aliados[i].x, aliados[i].y, 30, 30);
                }
            }
        };
        this.crashWith = function(otherobj,h,w) {
            var myleft = this.x;
            var myright = this.x + (this.width);
            var mytop = this.y;
            var mybottom = this.y + (this.height);
            var otherleft = otherobj.x;
            var otherright = otherobj.x + w;
            var othertop = otherobj.y;
            var otherbottom = otherobj.y + h;
            var crash = true;
            this.vida -= 1;
            if ((mybottom < othertop) || (mytop > otherbottom) || (myright < otherleft) || (myleft > otherright) && otherobj.equipo===sessionStorage.getItem("myTeam")) {
                crash = false;
                this.vida += 1;
            }
            return crash;
        }
    }

    myGameArea = {
        canvas: document.createElement("canvas"),
        start: function () {
            var w = window.innerWidth;
            var h = window.innerHeight;
            this.canvas.width = w - 200;
            this.canvas.height = h - 200;
            this.context = this.canvas.getContext("2d");
            this.context.fillStyle = "#A9A9A9";
            this.context.fillRect(0, 0, this.canvas.width, this.canvas.height);
            this.interval = setInterval(actualizarTrayectoriaBalas, 20);
            document.getElementById("Game").appendChild(this.canvas);
            window.addEventListener("keydown", function (e) {

                var send = function () {
                    var bullets = [];
                    for (var i = 0; i < myGamePiece.shoots.length; i++) {
                        bullets.push(new Bulletcita(myGamePiece.shoots[i].x, myGamePiece.shoots[i].y, myGamePiece.shoots[i].dir,sessionStorage.getItem("myTeam")));
                    }
                    var maquina = new Maquina(myGamePiece.x, myGamePiece.y, myGamePiece.direction, bullets);
                    var usuario = new Usuario(sessionStorage.getItem("user"), maquina, puntaje,sessionStorage.getItem("myTeam"),myGamePiece.vida);
                    var enemyteam = "";
                    if (sessionStorage.getItem("idRoom") === "A") {
                        enemyteam = "B";
                    } else {
                        enemyteam = "A";
                    }
                    stompClient.send("/topic/sala." + sessionStorage.getItem("idRoom") + "/" + sessionStorage.getItem("myTeam"), {}, JSON.stringify(usuario));
                    stompClient.send("/topic/sala." + sessionStorage.getItem("idRoom") + "/" + enemyteam, {}, JSON.stringify(usuario));
                };



                myGameArea.key = e.keyCode;
                //THE A KEY
                if (myGameArea.key && myGameArea.key === 65) {
                    myGamePiece.speedX = -1;
                    myGamePiece.image.src = directionImageTank + "2.png";
                    directionShoot = 2;
                    myGamePiece.direction = 2;
                    //updateGameArea();
                    myGamePiece.newPos();
                    send();
                    myGamePiece.speedX = 0;
                }
                //THE D KEY
                else if (myGameArea.key && myGameArea.key === 68) {
                    myGamePiece.speedX = 1;
                    myGamePiece.image.src = directionImageTank + "1.png";
                    directionShoot = 1;
                    myGamePiece.direction = 1;
                    //updateGameArea();
                    myGamePiece.newPos();
                    send();
                    myGamePiece.speedX = 0;
                }
                //THE W KEY
                else if (myGameArea.key && myGameArea.key === 87) {
                    myGamePiece.speedY = -1;
                    myGamePiece.image.src = directionImageTank + "4.png";
                    directionShoot = 4;
                    myGamePiece.direction = 4;
                    //updateGameArea();
                    myGamePiece.newPos();
                    send();
                    myGamePiece.speedY = 0;
                }
                //THE S KEY
                else if (myGameArea.key && myGameArea.key === 83) {
                    myGamePiece.speedY = 1;
                    myGamePiece.image.src = directionImageTank + "3.png";
                    directionShoot = 3;
                    myGamePiece.direction = 3;
                    //updateGameArea();
                    myGamePiece.newPos();
                    send();
                    myGamePiece.speedY = 0;
                }
                //THE SPACE KEY   
                else if (myGameArea.key && myGameArea.key === 32) {
                    var h, w, sx, sy;
                    if (directionShoot === 3) {
                        h = 30;
                        w = 10;
                        sx = 5;
                        sy = 30;
                    } else if (directionShoot === 4) {
                        h = 30;
                        w = 10;
                        sx = 5;
                        sy = -30;
                    } else if (directionShoot === 2) {
                        h = 10;
                        w = 30;
                        sx = -30;
                        sy = 10;
                    } else {
                        h = 10;
                        w = 30;
                        sx = 30;
                        sy = 10;
                    }
                    var temp = new Bullet(w, h, directionImageShoot + myGamePiece.direction + ".png", myGamePiece.x + sx, myGamePiece.y + sy, "image", myGamePiece.direction);
                    myGamePiece.shoots.push(temp);
                }
            });
            window.addEventListener("keyup", function (e) {
                myGameArea.key = false;
            });
        },
        clear: function () {
            this.context.clearRect(0, 0, this.canvas.width, this.canvas.height);
        }
    };



    return{
        movimiento: function () {
            console.info('Connecting to WS...');
            var socket = new SockJS('/stompendpoint');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function (frame) {
                var enemyteam = "";
                if (sessionStorage.getItem("idRoom") === "A") {
                    enemyteam = "B";
                } else {
                    enemyteam = "A";
                }
                stompClient.subscribe('/topic/sala.' + sessionStorage.getItem("idRoom") + "/" + sessionStorage.getItem("myTeam"), function (eventbody) {
                    var object = JSON.parse(eventbody.body);
                    var bandera = 0;
                    for (var i = 0; i < aliados.length && bandera === 0; i++) {
                        if (aliados[i].propietario === object.nombre ) {
                            bandera = 1;
                            aliados[i] = new Component(30, 30, directionImageTank + object.maquina.direction + ".png", object.maquina.x, object.maquina.y, "image", object.maquina.bullets, object.maquina.direction, object.nombre,sessionStorage.getItem("myTeam"),object.vida);
                        }
                    }
                    if (bandera === 0 && object.equipo===sessionStorage.getItem("myTeam")) {
                        aliados.push(new Component(30, 30, directionImageTank + object.maquina.direction + ".png", object.maquina.x, object.maquina.y, "image", object.maquina.bullets, object.maquina.direction, object.nombre,sessionStorage.getItem("myTeam"),object.vida));
                    }
                    updateGameArea();
                });
                stompClient.subscribe('/topic/sala.' + sessionStorage.getItem("idRoom") + "/" + enemyteam, function (eventbody) {
                    var object = JSON.parse(eventbody.body);
                    var bandera = 0;
                    for (var i = 0; i < oponents.length && bandera === 0; i++) {
                        if (oponents[i].propietario === object.nombre) {
                            bandera = 1;
                            oponents[i] = new Component(30, 30, directionImageTank + object.maquina.direction + ".png", object.maquina.x, object.maquina.y, "image", object.maquina.bullets, object.maquina.direction, object.nombre,sessionStorage.getItem("myTeam"),object.vida);
                        }
                    }
                    if (bandera === 0 && object.equipo!==sessionStorage.getItem("myTeam")) {
                        oponents.push(new Component(30, 30, directionImageTank + object.maquina.direction + ".png", object.maquina.x, object.maquina.y, "image", object.maquina.bullets, object.maquina.direction, object.nombre,sessionStorage.getItem("myTeam"),object.vida));
                    }
                    updateGameArea();
                });
                stompClient.subscribe('/topic/sala.' + sessionStorage.getItem("idRoom") + "/bullets", function (eventbody) {
                    var object = JSON.parse(eventbody.body);
                    graficarBala(object);
                });
            });
            myGameArea.start();
            myGamePiece = new Component(30, 30, directionImageTank + "1.png", 10, 120, "image", [], 1, sessionStorage.getItem("user"),sessionStorage.getItem("myTeam"),500);
            updateGameArea();
        }
    };


}());