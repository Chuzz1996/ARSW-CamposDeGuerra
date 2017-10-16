/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var apiclient = (function () {

    return {
        postUser: function (user) {
            return $.ajax({
                url: "/CamposDeGuerra", 
                type: "POST", 
                data: JSON.stringify(user), 
                contentType: "application/json"});
        },   
        deleteUser: function (user) {
            return $.ajax({
                url: "/CamposDeGuerra/"+user, 
                type: "DELETE"});
        },
        putUser: function (user) {
            return $.ajax({
                url: "/CamposDeGuerra", 
                type: "PUT", 
                data: JSON.stringify(user), 
                contentType: "application/json"});
        },
        getUser: function (user,callback) {
            return $ajax({
                url:"/CamposDeGuerra/"+user,
                type:"GET",
                success: function(response){callback(response);}});
        }
        
        
    };
    
}());

