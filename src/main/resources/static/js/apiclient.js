/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var apiclient = (function(){

	return {
            addUser:function(userName){
            return $.ajax({
                url:"/CamposDeGuerra",
                type:"POST",
                data: JSON.stringify(userName),
                contentType: "application/json"
            });
        }
    }
})();