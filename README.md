Sistema de Foros con Autenticación JWT en Spring Boot

Este proyecto es una API REST para un sistema de foros construido con Spring Boot que utiliza JSON Web Tokens (JWT) para la autenticación y autorización de usuarios.

Tecnologías Utilizadas:

    Java: Lenguaje de programación principal.

    Spring Boot: Framework para el desarrollo rápido de aplicaciones web basadas en Spring.

        Spring MVC: Para la creación de la API REST.

        Spring Data JPA: Para la persistencia de datos y la interacción con la base de datos.

        Spring Security: Para la seguridad de la aplicación, incluyendo la autenticación y autorización.

    JWT (JSON Web Tokens): Para la generación y validación de tokens de seguridad, permitiendo una autenticación sin estado (stateless).

        java-jwt: Librería para la creación y verificación de JWTs en Java.

    Lombok: Para reducir el código boilerplate (getters, setters, constructores, etc.).

    Bean Validation (JSR-380): Para la validación de datos de entrada en las solicitudes.

    MySQL: Sistema de gestión de bases de datos relacional (la configuración para la conexión a la base de datos se asume implícita en el código, probablemente en application.properties o application.yml).

    BCrypt: Para el hashing seguro de contraseñas.

Funcionalidades:

    Autenticación de Usuarios:

        Los usuarios pueden registrarse (código no mostrado pero implícito en UsuarioRepository y la lógica de negocio esperada) y autenticarse proporcionando su nombre de usuario y contraseña.

        Tras una autenticación exitosa, se genera un token JWT que el cliente debe enviar en las cabeceras de las siguientes solicitudes para acceder a recursos protegidos.

    Gestión de Tópicos (Hilos de Discusión):

        Creación: Los usuarios autenticados pueden crear nuevos tópicos proporcionando un título, un mensaje, su ID de autor y el nombre del curso al que pertenece el tópico.

        Listado: Se pueden listar todos los tópicos, con soporte para paginación.

        Consulta por ID: Se puede obtener un tópico específico por su ID.

        Actualización: Los usuarios autenticados pueden actualizar la información de un tópico (título, mensaje, nombre del curso).

        Eliminación: Los usuarios autenticados pueden eliminar un tópico por su ID.

Flujo de Autenticación:

    El cliente envía una solicitud POST a /auth con las credenciales del usuario (nombre de usuario y contraseña).

    AutenticacionController recibe la solicitud y utiliza AuthenticationManager para autenticar al usuario contra la base de datos.

    Si la autenticación es exitosa, GeneracionGestionToken (o TokenService) genera un token JWT.

        El token incluye información como el emisor (issuer), el sujeto (subject - que es el nombre de usuario), el ID del usuario, la fecha de creación y la fecha de expiración.

        El token se firma utilizando el algoritmo HMAC256 con una clave secreta configurada en la aplicación.

    El token se devuelve al cliente en la respuesta.

    El cliente almacena el token y lo envía en la cabecera Authorization (con el prefijo Bearer ) en cada solicitud a recursos protegidos.

    SecurityFilter intercepta cada solicitud:

        Extrae el token de la cabecera Authorization.

        Valida el token utilizando TokenService.getSubject(), y luego AutenticationService para cargar usuario.

        Si el token es válido, crea un objeto UsernamePasswordAuthenticationToken y lo establece en el contexto de seguridad de Spring.

        Si el token no es válido o ha expirado, la solicitud se rechaza.

Seguridad:

    Hashing de Contraseñas: Las contraseñas se almacenan en la base de datos utilizando BCrypt, un algoritmo de hashing seguro.

    JWT: La autenticación se realiza mediante JWT, lo que permite una arquitectura sin estado y evita la necesidad de almacenar sesiones en el servidor.

    @Transactional: Se utiliza en los métodos de servicio que modifican la base de datos para garantizar la atomicidad de las operaciones.

    Validación de Datos: Se utiliza Bean Validation para validar los datos de entrada en las solicitudes, evitando datos incorrectos o maliciosos.

    Manejo de Errores: TratadorDeErrores proporciona un manejo centralizado de excepciones, devolviendo respuestas de error apropiadas (404 Not Found, 400 Bad Request).

Consideraciones:

    El código asume que la base de datos ya está configurada y que las tablas usuarios y topicos existen con las columnas especificadas en las entidades Usuario y Topico.

    La clave secreta para la firma de los JWT (api.security.token.secret) debe almacenarse de forma segura, idealmente fuera del código fuente (por ejemplo, en variables de entorno o en un servicio de gestión de secretos).

    El código incluye logs de consola para fines de depuración. En un entorno de producción, se recomienda utilizar un framework de logging adecuado (como Logback o Log4j) y configurar los logs para que se almacenen en archivos o se envíen a un sistema de gestión de logs centralizado.
