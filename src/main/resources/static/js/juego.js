/* global updateGameArea, updateBullets, graficarBalas, graficarBalaOponente */

var juego = (function () {

    class Usuario {
        constructor(nombre, maquina, puntaje) {
            this.nombre = nombre;
            this.maquina = maquina;
            this.puntaje = puntaje;
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
        constructor(x, y, direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }
    }



    var myGamePiece;
    var oponents = [];
    var myGameArea;

    var stompClient = null;

    var puntaje = 0;

    var directionImageTank = "/images/tank";
    var directionImageShoot = "/images/bullet";
    var directionShoot = 1;
    var updateGameArea;





    var graficarBalaOponente = function (bullet) {
        var temp2 = new Image();
        temp2.src = directionImageShoot + bullet.direction + ".png";
        var h, w, sx, sy;
        if (bullet.direction === 3) {
            h = 10;
            w = 30;
            sx = -5;
            sy = 10;
            bullet.x -= 1;
        } else if (bullet.direction === 4) {
            h = 10;
            w = 30;
            sx = 5;
            sy = 10;
            bullet.x += 1;
        } else if (bullet.direction === 2) {
            h = 30;
            w = 10;
            sx = 10;
            sy = -10;
            bullet.y -= 1;
        } else {
            h = 30;
            w = 10;
            sx = 10;
            sy = 10;
            bullet.y += 1;
        }
        myGameArea.context.clearRect(bullet.x,  bullet.y,w,h);
        myGameArea.context.fillStyle = "#A9A9A9";
        myGameArea.context.fillRect(bullet.x,  bullet.y,w,h);
        myGameArea.context.drawImage(temp2, bullet.x + sx, bullet.y + sy, w, h);
    };

    var graficarBalas = function () {
        for (let i = 0; i < myGamePiece.shoots.length; i++) {
            var temp = new Image();
            temp.src = directionImageShoot + myGamePiece.shoots[i].dir + ".png";
            var h, w, sx, sy;
            if (myGamePiece.shoots[i].dir === 3) {
                h = 10;
                w = 30;
                sx = -5;
                sy = 10;
                myGamePiece.shoots[i].x -= 1;
            } else if (myGamePiece.shoots[i].dir === 4) {
                h = 10;
                w = 30;
                sx = 5;
                sy = 10;
                myGamePiece.shoots[i].x += 1;
            } else if (myGamePiece.shoots[i].dir === 2) {
                h = 30;
                w = 10;
                sx = 10;
                sy = -10;
                myGamePiece.shoots[i].y -= 1;
            } else {
                h = 30;
                w = 10;
                sx = 10;
                sy = 10;
                myGamePiece.shoots[i].y += 1;
            }
            myGameArea.context.clearRect(myGamePiece.shoots[i].x,  myGamePiece.shoots[i].y,w,h);
            myGameArea.context.fillStyle = "#A9A9A9";
            myGameArea.context.fillRect(myGamePiece.shoots[i].x,  myGamePiece.shoots[i].y,w,h);
            myGameArea.context.drawImage(temp, myGamePiece.shoots[i].x + sx, myGamePiece.shoots[i].y + sy, w, h);
            var temp=new Bulletcita(myGamePiece.shoots[i].x, myGamePiece.shoots[i].y, myGamePiece.shoots[i].dir);
            stompClient.send("/topic/sala." + localStorage.getItem("idRoom") + "/bullets", {}, JSON.stringify(temp));
        }
    }


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

    function Component(width, height, color, x, y, type, bullets, direction) {
        this.gamearea = myGameArea;
        if (type === "image") {
            this.image = new Image();
            this.image.src = color;
        }
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
            ctx.drawImage(this.image, this.x, this.y, this.width, this.height);
            //Dibujar Oponentes
            for (var i = 0; i < oponents.length; i++) {
                var temp = new Image();
                temp.src = directionImageTank + oponents[i].direction + ".png";
                ctx.drawImage(temp, oponents[i].x, oponents[i].y, 30, 30);
            }
            oponents = [];
        };
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
            this.interval = setInterval(graficarBalas, 20);
            document.getElementById("Game").appendChild(this.canvas);
            window.addEventListener("keydown", function (e) {

                var send = function () {
                    var bullets = [];
                    for (var i = 0; i < myGamePiece.shoots.length; i++) {
                        bullets.push(new Bulletcita(myGamePiece.shoots[i].x, myGamePiece.shoots[i].y, myGamePiece.shoots[i].dir));
                    }
                    var maquina = new Maquina(myGamePiece.x, myGamePiece.y, myGamePiece.direction, bullets);
                    var usuario = new Usuario(localStorage.getItem("user"), maquina, puntaje);
                    stompClient.send("/topic/sala." + localStorage.getItem("idRoom") + "/mypos", {}, JSON.stringify(usuario));
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
                        sy = 10;
                    } else if (directionShoot === 4) {
                        h = 30;
                        w = 10;
                        sx = 5;
                        sy = -10;
                    } else if (directionShoot === 2) {
                        h = 10;
                        w = 30;
                        sx = -5;
                        sy = 10;
                    } else {
                        h = 10;
                        w = 30;
                        sx = 10;
                        sy = 10;
                    }
                    var temp = new Bullet(w, h, directionImageShoot + directionShoot + ".png", myGamePiece.x + sx, myGamePiece.y + sy, "image", directionShoot);
                    myGamePiece.shoots.push(temp);
                    //updateGameArea();
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
                stompClient.subscribe('/topic/sala.' + localStorage.getItem("idRoom") + "/mypos", function (eventbody) {
                    var object = JSON.parse(eventbody.body);
                    oponents.push(new Component(30, 30, directionImageTank + object.maquina.direction + ".png", object.maquina.x, object.maquina.y, "image", object.maquina.bullets, object.maquina.direction));
                    updateGameArea();
                });
                stompClient.subscribe('/topic/sala.' + localStorage.getItem("idRoom") + "/bullets", function (eventbody) {
                    var object = JSON.parse(eventbody.body);
                    graficarBalaOponente(object);
                });
            });
            myGameArea.start();
            myGamePiece = new Component(30, 30, directionImageTank + "1.png", 10, 120, "image", [], 1);
            updateGameArea();
        }
    };


}());