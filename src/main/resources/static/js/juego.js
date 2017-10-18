var juego = (function () {

    var myGamePiece;
    var myGameArea;

    var directionImageTank = "/images/tank";
    var directionImageShoot = "/images/bullet";
    var directionShoot = 4;
    
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

    function updateGameArea() {
        myGameArea.clear();
        myGameArea.context.fillStyle = "#A9A9A9";
        myGameArea.context.fillRect(0, 0, myGameArea.canvas.width, myGameArea.canvas.height);
        myGamePiece.speedX = 0;
        myGamePiece.speedY = 0;

        //THE A KEY
        if (myGameArea.key && myGameArea.key === 65) {
            myGamePiece.speedX = -myGamePiece.speed;
            myGamePiece.image.src = directionImageTank + "2.png";
            directionShoot = 3;
        }
        //THE D KEY
        if (myGameArea.key && myGameArea.key === 68) {
            myGamePiece.speedX = myGamePiece.speed;
            myGamePiece.image.src = directionImageTank + "1.png";
            directionShoot = 4;
        }
        //THE W KEY
        if (myGameArea.key && myGameArea.key === 87) {
            myGamePiece.speedY = -myGamePiece.speed;
            myGamePiece.image.src = directionImageTank + "4.png";
            directionShoot = 2;
        }
        //THE S KEY
        if (myGameArea.key && myGameArea.key === 83) {
            myGamePiece.speedY = myGamePiece.speed;
            myGamePiece.image.src = directionImageTank + "3.png";
            directionShoot = 1;
        }
        //THE SPACE KEY   
        if (myGameArea.key && myGameArea.key === 32) {
            var h, w,sx,sy;
            if (directionShoot === 3) {
                h = 10;
                w = 30;
                sx=-5;
                sy=10;
            } else if (directionShoot === 4) {
                h = 10;
                w = 30;
                sx=5;
                sy=10;
            } else if (directionShoot === 2) {
                h = 30;
                w = 10;
                sx=10;
                sy=-10;
            } else {
                h = 30;
                w = 10;
                sx=10;
                sy=10;
            }
            myGamePiece.shoots.push(new Bullet(w, h, directionImageShoot + directionShoot + ".png", myGamePiece.x+sx, myGamePiece.y+sy, "image", directionShoot));
        }
        myGamePiece.newPos();
        myGamePiece.update();
    }
    
    function Component(width, height, color, x, y, type) {
        this.gamearea = myGameArea;
        if (type === "image") {
            this.image = new Image();
            this.image.src = color;
        }
        this.width = width;
        this.height = height;
        this.speedX = 0;
        this.speedY = 0;
        this.speed = 1;
        this.x = x;
        this.y = y;
        this.shoots = [];
        this.update = function () {
            var ctx = myGameArea.context;
            if (type === "image") {
                ctx.drawImage(this.image, this.x, this.y, this.width, this.height);
            } else {
                ctx.fillStyle = color;
                ctx.fillRect(this.x, this.y, this.width, this.height);
            }
            for (let i = 0; i < this.shoots.length; i++) {
                ctx.drawImage(this.shoots[i].image, this.shoots[i].x, this.shoots[i].y, this.shoots[i].width, this.shoots[i].height);
                if (this.shoots[i].dir === 4) {
                    this.shoots[i].x += 1;
                } else if (this.shoots[i].dir === 3) {
                    this.shoots[i].x -= 1;
                } else if (this.shoots[i].dir === 2) {
                    this.shoots[i].y -= 1;
                } else {
                    this.shoots[i].y += 1;
                }
            }
        };
        this.newPos = function () {
            this.x += this.speedX;
            this.y += this.speedY;
        };
    }

    myGameArea = {
        canvas: document.createElement("canvas"),
        start: function () {
            var w = window.innerWidth;
            var h = window.innerHeight;
            this.canvas.width = w-200;
            this.canvas.height = h-200;
            this.context = this.canvas.getContext("2d");
            this.context.fillStyle = "blue";
            this.context.fillRect(0, 0, this.canvas.width, this.canvas.height);
            document.getElementById("Game").appendChild(this.canvas);
            this.interval = setInterval(updateGameArea, 1);
            window.addEventListener("keydown", function (e) {
                myGameArea.key = e.keyCode;
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
            myGameArea.start();
            myGamePiece = new Component(30, 30, directionImageTank + "1.png", 10, 120, "image");
        }
    };
    
    
}());