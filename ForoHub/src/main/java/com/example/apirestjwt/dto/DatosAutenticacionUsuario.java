package com.example.apirestjwt.dto;

public record DatosAutenticacionUsuario(String login, String clave) {
}
/* 
JSON con estructura correcta : 

{
    "login": "henry",
    "clave": "miranda"
}

SI NO CUMPLE : 400 BadRequest
*/



//getter AUTOAMTICOS por ser record