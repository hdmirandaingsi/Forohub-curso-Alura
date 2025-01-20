package com.example.apirestjwt.controller;

import com.example.apirestjwt.dto.DatosAutenticacionUsuario; 
import com.example.apirestjwt.model.Usuario;
import com.example.apirestjwt.security.DatosJWTToken;
import com.example.apirestjwt.security.GeneracionGestionToken;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController//esta clase : RequestHTTP + ResponsiveDirectos
@RequestMapping("/auth")
public class AutenticacionController {

    @Autowired//inyectaDependencia
    private AuthenticationManager authenticationManager; // para Autenticar Usuario

    @Autowired//inyectaDependencia
    private GeneracionGestionToken tokenService; // generacion Token JWT

    @PostMapping//requestHTTP:POST  ---> activa :METODO
    public ResponseEntity<DatosJWTToken> autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario) {
    //acceso publico para el metodo ,  REponseEntity<TipoGenerico>= respuesta Personalizado tipo : DatosJWTToken    
    // Nombre Del Metodo :autenticarUsuario   
    // RequestBody: Extraer (BODY requestHTTP) luego lo convierte a Objeto JAVA
    //@valid : validar Objetos 
    //DatosAutenticacionUsuario :record con (string login , string clave)
    //
        Authentication authToken = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.login(),datosAutenticacionUsuario.clave());
        // record genera metodos : datosAutenticacionUsuario.login() para login y datosAutenticacionUsuario.clave() para clave
        // nombre del objeto : authToken
        // new = crear una nueva clase(UsernamePasswordAuthenticationToken)
        // authToken = Objeto, interfaz(Authentication) :  Login=Principal , clave = Credenciales , roles o permisos  y si esta autenticado o no
        //                     clase(UsernamePasswordAuthenticationToken) permisos y roles ,autenticado(FALSE)
        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        // authnticate : toma un objeto de tipo Authentication : authToken ,marca como autenticado (authenticated = true)
        //Spring Security. authenticationManager asigna las autoridades (roles o permisos) al usuario autenticado.
        // authenticationManager=verifica si las credenciales  (Principal,Credenciales)
        //   authenticationManager si falla spring security marca responsiveHTTP : 403 Forbidden 
        var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        // 
        // 
        // 
        // 
        // 
        // 
        
        // Agregar un log para imprimir el token generado//
        System.out.println("Token generado: " + JWTtoken);
        System.out.println("AUTENTICADO: VERDADERO");
        return ResponseEntity.ok(new DatosJWTToken(JWTtoken));
    }
}