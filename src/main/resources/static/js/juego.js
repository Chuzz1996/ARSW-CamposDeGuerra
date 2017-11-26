/* global  updateBullets, graficarBalas, graficarBalaOponente, actualizarTrayectoriaBalas, send, graficarExplosion, graficarBala, updateOponents, updateAliados, graficarBandera, checkBandera, checkSoltarBandera, checkPostPoint, checkGetBandera, getscorer, startTime, updateVidaOtherUser, graficarObstaculos, apiclient, getRoom, cargarPartida */

var juego = (function () {

    var api = apiclient;
    var myroom;
    var myGamePiece;
    var oponents = [];
    var aliados = [];
    var obstaculos = [];
    var potenciadores = [];
    var myBandera;
    var enemyBandera;
    var myGameArea;
    var myteam;
    var enemyteam;
    var stompClient = null;
    var puntaje = 0;
    var directionImageTank = "/images/";
    var directionImageShoot = "/images/bullet";
    var directionBandera = "/images/bandera";
    var directionObstaculo = "/images/obstaculo";
    var directionPotenciador = "/images/potenciador";
    var directionShoot = 1;
    var puntos;
    var bala;
    var min = 0;
    var sec = 0;
    var mili = 0;
    var tiempoRoom;

    class Usuario {
        constructor(userName, tipoMaquina, puntaje, equipo, vida) {
            this.userName = userName;
            this.tipoMaquina = tipoMaquina;
            this.puntaje = puntaje;
            this.equipo = equipo;
            this.vida = vida;
        }
        ;
    }
    ;

    class Bandera {
        constructor(x, y, team) {
            this.x = x;
            this.y = y;
            this.team = team;
            this.image = new Image();
            this.image.src = directionBandera + team + ".png";
        }
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
        constructor(x, y, direction, equipo) {
            this.x = x;
            this.y = y;
            this.direction = direction;
            this.equipo = equipo;
        }
    }

    class Explocion {
        constructor(x, y) {
            this.x = x;
            this.y = y;

        }
    }

    class Score {
        constructor(scoreA, scoreB) {
            this.scoreA = scoreA;
            this.scoreB = scoreB;

        }
    }

    class Potenciador {
        constructor(x,y,ran,tipo,tiempo) {
            this.x = x;
            this.y = y;
            this.tipo=tipo;
            this.tiempo=tiempo;
            this.image = new Image();
            this.image.src = directionPotenciador+ + ran + ".png";

        }
    }

    var cargarPartida = function () {
        var currentRoom = sessionStorage.getItem("idRoom");
        var getPromise = api.getRoom(currentRoom, function (data) {
            tiempoRoom = data.tiempo;
            var x, y, dir;
            puntos = new Score(0, 0);
            bala = new Image();
            var velocidad, dano;
            if (data.tipoMaquina === "Veloz") {
                velocidad = 20;
                dano = 3;
                vida = 400;
                directionImageTank = "/images/veloz"
            } else if (data.tipoMaquina === "Protectora") {
                velocidad = 10;
                dano = 5;
                vida = 1000;
                directionImageTank = "/images/protectora";
            } else {
                velocidad = 15;
                dano = 10;
                vida = 500;
                directionImageTank = "/images/destructora";
            }

            if (myteam === "A") {
                x = 30;
                dir = "1";
                y = Math.round(myGameArea.canvas.height * 0.30);


                for (var i = 0; i < data.equipoA.length; i++) {
                    if (data.equipoA[i].userName !== sessionStorage.getItem("user")) {
                        aliados.push(new Component(50, 50, directionImageTank + dir + myteam + ".png", x, (i + 1) * y, "image", [], dir, data.equipoA[i].userName, "A", vida, velocidad, dano));
                    } else {
                        myGamePiece = new Component(50, 50, directionImageTank + dir + myteam + ".png", x, (i + 1) * y, "image", [], 1, sessionStorage.getItem("user"), myteam, vida, velocidad, dano);
                    }
                }
                x = myGameArea.canvas.width - 30;
                for (var i = 0; i < data.equipoB.length; i++) {
                    if (data.equipoB[i].userName !== sessionStorage.getItem("user")) {
                        oponents.push(new Component(50, 50, directionImageTank + "2" + "B" + ".png", x, (i + 1) * y, "image", [], "2", data.equipoB[i].userName, "B", vida, velocidad, dano));
                    }
                }
            } else {
                x = myGameArea.canvas.width - 30;
                dir = "2";
                y = Math.round(myGameArea.canvas.height * 0.30);

                for (var i = 0; i < data.equipoB.length; i++) {
                    if (data.equipoB[i].userName !== sessionStorage.getItem("user")) {
                        aliados.push(new Component(50, 50, directionImageTank + dir + myteam + ".png", x, (i + 1) * y, "image", [], dir, data.equipoB[i].userName, myteam, vida, velocidad, dano));
                    } else {
                        myGamePiece = new Component(50, 50, directionImageTank + dir + myteam + ".png", x, (i + 1) * y, "image", [], 2, sessionStorage.getItem("user"), myteam, vida, velocidad, dano);
                    }
                }
                x = 30;
                for (var i = 0; i < data.equipoA.length; i++) {
                    if (data.equipoA[i].userName !== sessionStorage.getItem("user")) {
                        oponents.push(new Component(50, 50, directionImageTank + "1" + "A" + ".png", x, (i + 1) * y, "image", [], "1", data.equipoA[i].userName, "A", vida, velocidad, dano));
                    }
                }

            }
            for (var i = 0; i < 30; i++) {
                if (i <= 10) {
                    obstaculos.push(new Obstaculo(50, 50, directionObstaculo + "1.png", i * 20, i * 20, "pasto", 200, i));
                } else if (i > 10 && i <= 20) {
                    obstaculos.push(new Obstaculo(50, 50, directionObstaculo + "2.png", i * 40, i * 40, "ladrillo", 500, i));
                } else {
                    obstaculos.push(new Obstaculo(50, 50, directionObstaculo + "3.png", i * 60, i * 60, "metal", 1000, i));
                }
            }

        });
        getPromise.then(
                function () {
                    console.info("Cargo datos correctamente");
                },
                function () {
                    console.info("ERROR! No cargo los datos");
                }
        );
        return getPromise;
    };


    var startTime = function () {
        var handler = function () {
            var totalSeconds = Math.floor(mili / 1000);
            min = Math.floor(totalSeconds / 60);
            sec = totalSeconds - min * 60;
            sec++;
            for (var item in potenciadores) {
                item.tiempo -= 1000;
            }
            if (sec === 60) {
                sec = 0;
                min++;
                if (min === 60) {
                    min = 0;
                }
            }
            mili = ((min * 60) + sec) * 1000;
            if (mili === tiempoRoom) {
                stompClient.send('/app/sala.' + myroom + "/endGame", {}, 'end');
            }
            var potenciador;
            if (mili % 30000 === 0) {
                var Poten = Math.floor((Math.random() * 3) + 1);
                var xPonte = Math.floor((Math.random() * myGameArea.canvas.width) + 1);
                var yPonte = Math.floor((Math.random() * myGameArea.canvas.height) + 1);
                if (Poten === 1) {
                    potenciador = new Potenciador(xPonte, yPonte, Poten, "vida", 10000);
                } else if (Poten === 2) {
                    potenciador = new Potenciador(xPonte, yPonte, Poten, "vida", 10000);
                } else {
                    potenciador = new Potenciador(xPonte, yPonte, Poten, "vida", 10000);
                }
                stompClient.send('/topic/sala.' + myroom + "/potenciador", {}, JSON.stringify(potenciador));
            }
            document.getElementById("Segundos").innerHTML = (min < 10 ? "0" + min : min) + ":" + (sec < 10 ? "0" + sec : sec);

        };
        setInterval(handler, 1000);
    };


    var graficarBala = function (bullet) {
        bala.src = directionImageShoot + bullet.direction + bullet.equipo + ".png";
        var h, w, sx, sy, dx, dy;
        if (bullet.direction === 3) {
            h = 30;
            w = 10;
            sx = 0;
            sy = 10;
            dx = 0;
            dy = -15;
        } else if (bullet.direction === 4) {
            h = 30;
            w = 10;
            sx = 0;
            sy = 0;
            dx = 0;
            dy = 15;
        } else if (bullet.direction === 2) {
            h = 10;
            w = 30;
            sx = 0;
            sy = 0;
            dx = 15;
            dy = 0;
        } else {
            h = 10;
            w = 30;
            sx = 10;
            sy = 0;
            dx = -15;
            dy = 0;
        }
        if (bullet.equipo !== myteam && myGamePiece.crashWith(bullet, h, w, "bullet")) {
            myGameArea.context.fillStyle = "#A9A9A9";
            myGameArea.context.fillRect(bullet.x + sx + dx, bullet.y + sy + dy, w, h);
            var temp2 = new Explocion(myGamePiece.x, myGamePiece.y);
            stompClient.send("/topic/sala." + myroom + "/explocion", {}, JSON.stringify(temp2));
            document.getElementById("live").innerHTML = "Vida: " + myGamePiece.vida;
        } else {
            myGameArea.context.fillStyle = "#A9A9A9";
            myGameArea.context.fillRect(bullet.x + sx + dx, bullet.y + sy + dy, w, h);
            myGameArea.context.drawImage(bala, bullet.x + sx, bullet.y + sy, w, h);
        }
        for (var i = 0; i < obstaculos.length; i++) {
            if (obstaculos[i].alive) {
                var crash = obstaculos[i].crashWith(bullet, h, w, "bullet");
                if (crash) {
                    var temp3 = new Explocion(obstaculos[i].x, obstaculos[i].y);
                    graficarExplosion(temp3);
                }
            }
        }
        setTimeout(function () {
            actualizarTrayectoriaBalas(bullet);
        }, 10);
    };

    var actualizarTrayectoriaBalas = function (shoot) {
        if (shoot.direction === 3) {
            shoot.y += 15;
        } else if (shoot.direction === 4) {
            shoot.y -= 15;
        } else if (shoot.direction === 2) {
            shoot.x -= 15;
        } else {
            shoot.x += 15;
        }
        if (shoot.x <= myGameArea.canvas.width && shoot.x >= -50 && shoot.y <= myGameArea.canvas.height && shoot.y >= -50) {
            graficarBala(shoot);
        }
    };


    var updateVidaOtherUser = function (o) {
        if (o.vida > 0) {
            var ctx = myGameArea.context;
            ctx.drawImage(o.image, o.x, o.y, o.width, o.height);
            ctx.font = "10px Verdana";
            var gradient = ctx.createLinearGradient(0, 0, myGameArea.canvas.width, 0);
            if (o.equipo === "A") {
                gradient.addColorStop("1.0", "blue");
            } else {
                gradient.addColorStop("1.0", "red");
            }
            ctx.fillStyle = gradient;
            ctx.fillText(o.propietario, o.x, o.y + o.height + 10);
            ctx.fillText(o.vida, o.x, o.y + o.height + 20);
        }
    };

    var updateOponents = function () {
        //Dibujar Oponentes
        oponents.map(function (o) {
            if (o.vida > 0) {
                var ctx = myGameArea.context;
                ctx.drawImage(o.image, o.x, o.y, o.width, o.height);
                updateVidaOtherUser(o);
            }
        });
    };
    var updateAliados = function () {
        //Dibujar aliados
        aliados.map(function (a) {
            if (a.vida > 0) {
                var ctx = myGameArea.context;
                ctx.drawImage(a.image, a.x, a.y, a.width, a.height);
                updateVidaOtherUser(a);
            }
        });
    };
    var graficarBandera = function (banderateam) {
        if (banderateam === myteam) {
            myGameArea.context.drawImage(myBandera.image, myBandera.x, myBandera.y, Math.round(myGameArea.canvas.width * 0.03), Math.round(myGameArea.canvas.height * 0.04));
        } else {
            myGameArea.context.drawImage(enemyBandera.image, enemyBandera.x, enemyBandera.y, Math.round(myGameArea.canvas.width * 0.03), Math.round(myGameArea.canvas.height * 0.04));
        }
    };

    var graficarObstaculos = function () {
        for (var i = 0; i < obstaculos.length; i++) {
            if (obstaculos[i].alive) {
                myGameArea.context.drawImage(obstaculos[i].image, obstaculos[i].x, obstaculos[i].y, obstaculos[i].width, obstaculos[i].height);
            }
        }
    };

    var graficarPoteciadores=function (){
        for (var p in potenciadores) {
            myGameArea.context.drawImage(p.image, p.x, p.y, 30, 30);
        }
    };

    var graficarExplosion = function (explosion) {
        myGameArea.context.fillStyle = "#A9A9A9";
        myGameArea.context.fillRect(explosion.x, explosion.y, 30, 30);
        let image = new Image();
        image.src = "/images/explosion.png";
        myGameArea.context.drawImage(image, explosion.x, explosion.y, 30, 30);
        setTimeout(function () {
            updateOponents();
            updateAliados();
        }, 5000);
        setTimeout(function () {
            myGamePiece.update();
        }, 5000);
    };

    var checkGetBandera = function () {
        var obtenerBandera;
        if (myteam === "A") {
            obtenerBandera = apiclient.postBanderaB(myroom, new Usuario(sessionStorage.getItem("user"), null, 0, myteam, 0));
        } else {
            obtenerBandera = apiclient.postBanderaA(myroom, new Usuario(sessionStorage.getItem("user"), null, 0, myteam, 0));
        }
        obtenerBandera.then(
                function () {
                    alert("Tomaste la bandera");
                    myGamePiece.hasban = true;
                    myGameArea.context.fillStyle = "#A9A9A9";
                    myGameArea.context.fillRect(enemyBandera.x, enemyBandera.y, Math.round(myGameArea.canvas.width * 0.03), Math.round(myGameArea.canvas.height * 0.04));
                    enemyBandera.x = myGamePiece.x - 20;
                    enemyBandera.x = myGamePiece.y - 20;
                },
                function () {
                    alert("la bandera ya fue tomada por otra persona");
                }
        );
        return obtenerBandera;
    };


    var checkPostPoint = function () {
        var postPoint;
        if (myteam === "A") {
            postPoint = apiclient.postPuntuarBanderaA(myroom, new Usuario(sessionStorage.getItem("user"), null, 0, myteam, 0));
        } else {
            postPoint = apiclient.postPuntuarBanderaB(myroom, new Usuario(sessionStorage.getItem("user"), null, 0, myteam, 0));
        }
        postPoint.then(
                function () {
                    alert("Has Realizado un Punto!!!");
                    myGamePiece.hasban = false;
                    if (myteam === "A") {
                        puntos.scoreA += 1;
                    } else {
                        puntos.scoreB += 1;
                    }
                    stompClient.send("/topic/sala." + myroom + "/puntaje", {}, JSON.stringify(puntos));
                },
                function () {
                    alert("Tu bandera fue robada,ve y buscala!!!");
                }
        );
        return postPoint;
    };


    var checkSoltarBandera = function () {
        var deleteBandera;
        if (myteam === "A") {
            deleteBandera = apiclient.deleteSoltarBanderaB(myroom, new Usuario(sessionStorage.getItem("user"), null, 0, myteam, 0));
        } else {
            deleteBandera = apiclient.deleteSoltarBanderaA(myroom, new Usuario(sessionStorage.getItem("user"), null, 0, myteam, 0));
        }
        deleteBandera.then(
                function () {
                    alert("Como has puntuado, la bandera enemiga a vuelto a su base!!!");
                    myGamePiece.hasban = false;
                    myGameArea.context.fillStyle = "#A9A9A9";
                    myGameArea.context.fillRect(enemyBandera.x, enemyBandera.y, Math.round(myGameArea.canvas.width * 0.03), Math.round(myGameArea.canvas.height * 0.04));
                    if (myteam === "A") {
                        enemyBandera.x = Math.round(myGameArea.canvas.width * 0.90);
                        enemyBandera.y = Math.round(myGameArea.canvas.height * 0.50);
                    } else {
                        enemyBandera.x = Math.round(myGameArea.canvas.width * 0.10);
                        enemyBandera.y = Math.round(myGameArea.canvas.height * 0.50);
                    }
                    stompClient.send("/topic/sala." + myroom + "/bandera", {}, JSON.stringify(enemyBandera));

                },
                function () {
                    alert("No se puedo soltar la bandera!!!");
                }
        );
        return deleteBandera;
    };

    var getscorer = function () {
        var getPromise = apiclient.getScorer(sessionStorage.getItem("idRoom"), function (data) {
            reducir = function (objeto) {
                return objeto2 = {"score": objeto};
            };
            lista = data.map(reducir);
            puntos.scoreA = lista[0].score;
            puntos.scoreB = lista[1].score;
        });
        getPromise.then(
                function () {
                    console.info("Se obtuvo el Scorer");
                },
                function () {
                    console.info("No Se obtuvo el Scorer");
                }
        );
        return getPromise;
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


    function Obstaculo(width, height, color, x, y, tipo, vida, id) {
        this.gamearea = myGameArea;
        this.image = new Image();
        this.image.src = color;
        this.vida = vida;
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.tipo = tipo;
        this.alive = true;
        this.id = id;

        this.crashWith = function (otherobj, h, w, tipo) {
            var myleft = this.x;
            var myright = this.x + (this.width);
            var mytop = this.y;
            var mybottom = this.y + (this.height);
            var otherleft = otherobj.x;
            var otherright = otherobj.x + w;
            var othertop = otherobj.y;
            var otherbottom = otherobj.y + h;
            var crash = true;
            if ((mybottom < othertop) || (mytop > otherbottom) || (myright < otherleft) || (myleft > otherright)) {
                crash = false;
            }
            if (crash && tipo === "bullet") {
                this.vida -= 1;
                if (this.vida <= 0) {
                    this.alive = false;
                    myGameArea.context.fillStyle = "#A9A9A9";
                    myGameArea.context.fillRect(this.x, this.y, this.width, this.height);
                }
            }
            return crash;
        };
    }
    ;



    function Component(width, height, color, x, y, type, bullets, direction, propietario, equipo, vida, velocidad, dano) {
        this.gamearea = myGameArea;
        if (type === "image") {
            this.image = new Image();
            this.image.src = color;
        }
        this.propietario = propietario;
        this.vida = vida;
        this.equipo = equipo;
        this.width = width;
        this.height = height;
        this.speedX = 0;
        this.speedY = 0;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.shoots = bullets;
        this.hasban = false;
        this.velocidad = velocidad;
        this.dano = dano;

        this.newPos = function () {
            myGameArea.context.fillStyle = "#A9A9A9";
            myGameArea.context.fillRect(this.x, this.y, 50, 80);
            this.x += this.speedX;
            this.y += this.speedY;
            if (this.crashWith(enemyBandera, Math.round(myGameArea.canvas.width * 0.03), Math.round(myGameArea.canvas.height * 0.04), "enemyBandera") && this.hasban === false) {
                checkGetBandera();
            }
            if (this.hasban) {
                myGameArea.context.fillStyle = "#A9A9A9";
                myGameArea.context.fillRect(enemyBandera.x, enemyBandera.y, Math.round(myGameArea.canvas.width * 0.03), Math.round(myGameArea.canvas.height * 0.04));
                enemyBandera.x = this.x - 20;
                enemyBandera.y = this.y - 20;
            }
            if (this.hasban && this.crashWith(myBandera, Math.round(myGameArea.canvas.width * 0.03), Math.round(myGameArea.canvas.height * 0.04), "myBandera")) {
                checkPostPoint().then(checkSoltarBandera);
            }
            for (var i = 0; i < obstaculos.length; i++) {
                if (obstaculos[i].alive) {
                    if (obstaculos[i].crashWith(this, this.width, this.height, "jugador")) {
                        this.x -= this.speedX;
                        this.y -= this.speedY;
                    }
                }
            }
            for (var i = 0; i < potenciadores.length; i++) {
                if (potenciadores[i].alive) {
                    potenciadores[i].crashWith(this, this.width, this.height, "jugador")
                }
            }
            graficarObstaculos();
            graficarBandera(myteam);
            graficarBandera(enemyteam);


        };

        this.update = function () {
            //Dibujarme
            var ctx = myGameArea.context;
            if (this.vida > 0) {
                ctx.drawImage(this.image, this.x, this.y, this.width, this.height);
                updateVidaOtherUser(this);
            } else if (this.vida < 0 && this.hasban) {
                checkSoltarBandera();
            }
            graficarBandera(myteam);
            graficarBandera(enemyteam);

        };
        this.crashWith = function (otherobj, h, w, tipo) {
            var myleft = this.x;
            var myright = this.x + (this.width);
            var mytop = this.y;
            var mybottom = this.y + (this.height);
            var otherleft = otherobj.x;
            var otherright = otherobj.x + w;
            var othertop = otherobj.y;
            var otherbottom = otherobj.y + h;
            var crash = true;
            if ((mybottom < othertop) || (mytop > otherbottom) || (myright < otherleft) || (myleft > otherright)) {
                crash = false;
            }
            if (crash && tipo === "bullet") {
                this.vida -= this.dano;
            } else if (crash && tipo === "vida") {
                this.vida += 100;
            } else if (crash && tipo === "velocidad") {
                this.velocidad += 15;
            } else if (crash && tipo === "ataque") {
                this.dano += 10;
            }
            return crash;
        };
    }

    var send = function () {
        var maquina = new Maquina(myGamePiece.x, myGamePiece.y, myGamePiece.direction, []);
        var usuarioA;
        var usuarioB;
        var vidaenemigo;
        if (myGamePiece.vida > 0) {
            vidaenemigo = 999;
        } else {
            vidaenemigo = 0;
        }
        if (myteam === "A") {
            usuarioA = new Usuario(sessionStorage.getItem("user"), maquina, puntaje, myteam, myGamePiece.vida);
            usuarioB = new Usuario(sessionStorage.getItem("user"), maquina, puntaje, myteam, vidaenemigo);
        } else {
            usuarioA = new Usuario(sessionStorage.getItem("user"), maquina, puntaje, myteam, vidaenemigo);
            usuarioB = new Usuario(sessionStorage.getItem("user"), maquina, puntaje, myteam, myGamePiece.vida);
        }
        stompClient.send("/app/sala." + myroom + "/" + myteam, {}, JSON.stringify(usuarioA));
        stompClient.send("/app/sala." + myroom + "/" + enemyteam, {}, JSON.stringify(usuarioB));
        if (myGamePiece.hasban) {
            stompClient.send("/topic/sala." + myroom + "/bandera", {}, JSON.stringify(enemyBandera));
        }
    };

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
            document.getElementById("Game").appendChild(this.canvas);
            window.addEventListener("keydown", function (e) {

                myGameArea.key = e.keyCode;

                //THE A KEY
                if (myGameArea.key && myGameArea.key === 65) {
                    myGamePiece.speedX = -myGamePiece.velocidad;
                    myGamePiece.image.src = directionImageTank + "2" + myteam + ".png";
                    directionShoot = 2;
                    myGamePiece.direction = 2;
                    myGamePiece.newPos();
                    myGamePiece.update();
                    send();
                    myGamePiece.speedX = 0;
                }
                //THE D KEY
                else if (myGameArea.key && myGameArea.key === 68) {
                    myGamePiece.speedX = myGamePiece.velocidad;
                    myGamePiece.image.src = directionImageTank + "1" + myteam + ".png";
                    directionShoot = 1;
                    myGamePiece.direction = 1;
                    myGamePiece.newPos();
                    myGamePiece.update();
                    send();
                    myGamePiece.speedX = 0;
                }
                //THE W KEY
                else if (myGameArea.key && myGameArea.key === 87) {
                    myGamePiece.speedY = -myGamePiece.velocidad;
                    myGamePiece.image.src = directionImageTank + "4" + myteam + ".png";
                    directionShoot = 4;
                    myGamePiece.direction = 4;
                    myGamePiece.newPos();
                    myGamePiece.update();
                    send();
                    myGamePiece.speedY = 0;
                }
                //THE S KEY
                else if (myGameArea.key && myGameArea.key === 83) {
                    myGamePiece.speedY = myGamePiece.velocidad;
                    myGamePiece.image.src = directionImageTank + "3" + myteam + ".png";
                    directionShoot = 3;
                    myGamePiece.direction = 3;
                    myGamePiece.newPos();
                    myGamePiece.update();
                    send();
                    myGamePiece.speedY = 0;
                }
                //THE SPACE KEY   
                else if (myGameArea.key && myGameArea.key === 32 && myGamePiece.shoots.length < 11) {
                    var h, w, sx, sy;
                    if (directionShoot === 3) {
                        h = 30;
                        w = 10;
                        sx = 20;
                        sy = 60;
                    } else if (directionShoot === 4) {
                        h = 30;
                        w = 10;
                        sx = 20;
                        sy = -60;
                    } else if (directionShoot === 2) {
                        h = 10;
                        w = 30;
                        sx = -60;
                        sy = 20;
                    } else {
                        h = 10;
                        w = 30;
                        sx = 70;
                        sy = 20;
                    }
                    var temp2 = new Bulletcita(myGamePiece.x + sx, myGamePiece.y + sy, myGamePiece.direction, myteam)
                    stompClient.send("/app/sala." + myroom + "/bullets", {}, JSON.stringify(temp2));
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
            if (sessionStorage.getItem("myTeam") === "A") {
                enemyteam = "B";
                myteam = "A";
            } else {
                enemyteam = "A";
                myteam = "B";
            }
            myroom = sessionStorage.getItem("idRoom");

            var socket = new SockJS('/stompendpoint');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function (frame) {

                //ENDGAME
                stompClient.subscribe("/topic/sala." + myroom + "/endGame", function (evenbody) {
                    sessionStorage.setItem("PuntosA", puntos.scoreA);
                    sessionStorage.setItem("PuntosB", puntos.scoreB);
                    stompClient.disconnect();
                    var newURL = window.location.protocol + "//" + window.location.host + "/" + "endgame.html";
                    window.location.replace(newURL);
                });

                //ALIADOS
                stompClient.subscribe('/topic/sala.' + myroom + "/" + myteam, function (eventbody) {
                    var object = JSON.parse(eventbody.body);
                    var ban = 0;
                    for (var i = 0; i < aliados.length && ban === 0; i++) {
                        if (aliados[i].propietario === object.userName) {
                            myGameArea.context.fillStyle = "#A9A9A9";
                            myGameArea.context.fillRect(aliados[i].x, aliados[i].y, 50, 80);
                            ban = 1;
                            var a = new Component(50, 50, directionImageTank + object.tipoMaquina.direction + myteam + ".png", object.tipoMaquina.x, object.tipoMaquina.y, "image", [], object.tipoMaquina.direction, object.userName, myteam, object.vida, object.velocidad, object.dano);
                            aliados[i] = a;
                        }
                    }
                    if (ban === 0 && object.equipo === myteam && object.userName !== sessionStorage.getItem("user")) {
                        var a = new Component(50, 50, directionImageTank + object.tipoMaquina.direction + myteam + ".png", object.tipoMaquina.x, object.tipoMaquina.y, "image", [], object.tipoMaquina.direction, object.userName, myteam, object.vida, object.velocidad, object.dano);
                        aliados.push(a);
                    }
                    updateAliados();
                    myGamePiece.update();
                });

                //ENEMIGOS
                stompClient.subscribe('/topic/sala.' + myroom + "/" + enemyteam, function (eventbody) {
                    var object = JSON.parse(eventbody.body);
                    var ban = 0;
                    for (var i = 0; i < oponents.length && ban === 0; i++) {
                        if (oponents[i].propietario === object.userName) {
                            myGameArea.context.fillStyle = "#A9A9A9";
                            myGameArea.context.fillRect(oponents[i].x, oponents[i].y, 50, 80);
                            ban = 1;
                            var o = new Component(50, 50, directionImageTank + object.tipoMaquina.direction + enemyteam + ".png", object.tipoMaquina.x, object.tipoMaquina.y, "image", [], object.tipoMaquina.direction, object.userName, enemyteam, object.vida, object.velocidad, object.dano);
                            oponents[i] = o;
                        }
                    }
                    if (ban === 0 && object.equipo !== myteam && object.userName !== sessionStorage.getItem("user")) {
                        var o = new Component(50, 50, directionImageTank + object.tipoMaquina.direction + enemyteam + ".png", object.tipoMaquina.x, object.tipoMaquina.y, "image", [], object.tipoMaquina.direction, object.userName, enemyteam, object.vida, object.velocidad, object.dano);
                        oponents.push(o);
                    }
                    updateOponents();
                    myGamePiece.update();
                });

                //BALAS
                stompClient.subscribe('/topic/sala.' + myroom + "/bullets", function (eventbody) {
                    var object = JSON.parse(eventbody.body);
                    graficarBala(object);
                });

                //EXPLOCION
                stompClient.subscribe('/topic/sala.' + myroom + "/explocion", function (eventbody) {
                    var object = JSON.parse(eventbody.body);
                    graficarExplosion(object);
                });

                //BANDERA
                stompClient.subscribe('/topic/sala.' + myroom + "/bandera", function (eventbody) {
                    var object = JSON.parse(eventbody.body);
                    if (object.team === myteam) {
                        myGameArea.context.fillStyle = "#A9A9A9";
                        myGameArea.context.fillRect(myBandera.x, myBandera.y, Math.round(myGameArea.canvas.width * 0.03), Math.round(myGameArea.canvas.height * 0.04));
                        myBandera.x = object.x;
                        myBandera.y = object.y;
                    } else {
                        myGameArea.context.fillStyle = "#A9A9A9";
                        myGameArea.context.fillRect(enemyBandera.x, enemyBandera.y, Math.round(myGameArea.canvas.width * 0.03), Math.round(myGameArea.canvas.height * 0.04));
                        enemyBandera.x = object.x;
                        enemyBandera.y = object.y;
                    }
                    graficarBandera(object.team);
                });

                //PUNTAJE
                stompClient.subscribe('/topic/sala.' + myroom + "/puntaje", function (eventbody) {
                    getscorer().then(function () {
                        document.getElementById("Puntaje").innerHTML = "Puntaje: " + puntos.scoreA + "-" + puntos.scoreB;
                    });
                });

                //TIEMPO
                stompClient.subscribe('/topic/sala.' + myroom + "/tiempo", function (eventbody) {
                    var object = eventbody.body;
                    mili = object;
                });

                //POTENCIADOR
                stompClient.subscribe('/topic/sala.' + myroom + "/potenciador", function (eventbody) {
                    var object = JSON.parse(eventbody.body);
                    var banderaP = false;
                    for (var p in potenciadores) {
                        if (p.x === object.x && p.y === object.y) {
                            p.tiempo = 0;
                            banderaP = true;
                        }
                    }
                    if (!banderaP) {
                        potenciadores.push(object);
                    }
                    setTimeout(graficarPoteciadores, 10000);
                });
            });


            myGameArea.start();
            myBandera = new Bandera(Math.round(myGameArea.canvas.width * 0.10), Math.round(myGameArea.canvas.height * 0.50), myteam);
            enemyBandera = new Bandera(Math.round(myGameArea.canvas.width * 0.90), Math.round(myGameArea.canvas.height * 0.50), enemyteam);
            cargarPartida().then(getscorer).then(graficarObstaculos);
            graficarBandera(myteam);
            graficarBandera(enemyteam);
            startTime();

            /*setTimeout(function () {
             graficarObstaculos();
             graficarBandera(myteam);
             graficarBandera(enemyteam);
             getscorer().then(send);
             startTime();
             }, 5000);*/
        }
    };
}());
