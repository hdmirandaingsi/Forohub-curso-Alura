package com.example.apirestjwt.security;

public record DatosJWTToken(String jwTtoken) {//se espera un DATO INMUTABLE: Token JWT
}
/*
 JWT :json web token

1 - firmados digitalmente(api.security.token.secret)

2 - información(usuario,identificador, nombre, permisos,roles

3 - caducar  y revocar usuario , permisos , roles

*/