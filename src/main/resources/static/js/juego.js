var juego = function(){
    
    var myGamePiece;

    var directionImageTank = "/images/tank";
    var directionImageShoot = "/images/bullet";
    var directionShoot = 4; 
    
    var myGameArea = {
        canvas : document.createElement("canvas"),
        start : function() {
            this.canvas.width = 500;
            this.canvas.height = 500;
            this.context = this.canvas.getContext("2d");
            document.body.insertBefore(this.canvas, document.body.childNodes[0]);
            this.interval = setInterval(updateGameArea, 1);
            window.addEventListener('keydown', function (e) {
                myGameArea.key = e.keyCode;
            })
            window.addEventListener('keyup', function (e) {
                myGameArea.key = false;
            })
        }, 
        clear : function(){
            this.context.clearRect(0, 0, this.canvas.width, this.canvas.height);
        }
    }

    function component(width, height, color, x, y, type) {
        this.gamearea = myGameArea;
        if(type=="image"){
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
        this.update = function() {
            ctx = myGameArea.context;
            if(type=="image"){
                ctx.drawImage(this.image,this.x,this.y,this.width,this.height);
            }else{
                ctx.fillStyle = color;
                ctx.fillRect(this.x, this.y, this.width, this.height);
            }
            for(let i = 0; i < this.shoots.length; i++){
                this.shoots[i].update;
                console.info(this.shoots[i].x+","+this.shoots[i].y);
            }
        }
        this.newPos = function() {
            this.x += this.speedX;
            this.y += this.speedY;        
        }
    }
    
    function bullet(width, height, color, x, y, type){
        this.gamearea = myGameArea;
        if(type=="image"){
            this.image = new Image();
            this.image.src = color;
        }
        this.width = width;
        this.height = height;
        this.speedX = 0;
        this.speedY = 0;
        this.x = x;
        this.y = y;
        this.update = function() {
            ctx = myGameArea.context;
            console.info("ENTRO");
            if(type=="image"){
                ctx.drawImage(this.image,this.x,this.y,this.width,this.height);
            }else{
                ctx.fillStyle = color;
                ctx.fillRect(this.x, this.y, this.width, this.height);
            }
        }
        this.newPos = function() {
            this.x += this.speedX;
            this.y += this.speedY;        
        }
    }
    
    function updateGameArea() {
        myGameArea.clear();
        myGamePiece.speedX = 0;
        myGamePiece.speedY = 0;    
        
        if (myGameArea.key && myGameArea.key == 37) {
            myGamePiece.speedX = -myGamePiece.speed; 
            myGamePiece.image.src = directionImageTank+"2.png";
            directionShoot = 3;
        }
        if (myGameArea.key && myGameArea.key == 39) {
            myGamePiece.speedX = myGamePiece.speed; 
            myGamePiece.image.src = directionImageTank+"1.png";
            directionShoot = 4;
        }
        if (myGameArea.key && myGameArea.key == 38) {
            myGamePiece.speedY = -myGamePiece.speed; 
            myGamePiece.image.src = directionImageTank+"4.png";
            directionShoot = 2;
        }
        if (myGameArea.key && myGameArea.key == 40) {
            myGamePiece.speedY = myGamePiece.speed; 
            myGamePiece.image.src = directionImageTank+"3.png";
            directionShoot = 1;
        }
        myGamePiece.newPos();    
        if(myGameArea.key && myGameArea.key == 32){
            myGamePiece.shoots.push(new bullet(myGamePiece.x+1,myGamePiece.y+1,directionImageShoot+directionShoot+".png",10,20,"image"));
        }
        myGamePiece.update();
    }
    
    return{
        movimiento:function(){
            myGameArea.start();
            myGamePiece = new component(30, 30, directionImageTank+"1.png", 10, 120,"image");
        }
    }
}();