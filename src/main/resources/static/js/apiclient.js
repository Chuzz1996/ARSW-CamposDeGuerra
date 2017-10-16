/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


apiclient = (function () {

    return {
        postUser: function (user) {
            return $.ajax({url: "/CamposDeGuerra", type: "POST", data: JSON.stringify(user), contentType: "application/json"});
        }
    };
    
})();