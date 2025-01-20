<h1>Sistema de Foros con Autenticación JWT en Spring Boot</h1>

<p>Este proyecto es una API REST para un sistema de foros construido con <strong>Spring Boot</strong> que utiliza <strong>JSON Web Tokens (JWT)</strong> para la autenticación y autorización de usuarios.</p>

<h2>Tecnologías Utilizadas:</h2>

<ul>
  <li><strong>Java:</strong> Lenguaje de programación principal.</li>
  <li><strong>Spring Boot:</strong> Framework para el desarrollo rápido de aplicaciones web basadas en Spring.
    <ul>
      <li><strong>Spring MVC:</strong> Para la creación de la API REST.</li>
      <li><strong>Spring Data JPA:</strong> Para la persistencia de datos y la interacción con la base de datos.</li>
      <li><strong>Spring Security:</strong> Para la seguridad de la aplicación, incluyendo la autenticación y autorización.</li>
    </ul>
  </li>
  <li><strong>JWT (JSON Web Tokens):</strong> Para la generación y validación de tokens de seguridad, permitiendo una autenticación sin estado (stateless).
    <ul>
      <li><strong>java-jwt:</strong> Librería para la creación y verificación de JWTs en Java.</li>
    </ul>
  </li>
  <li><strong>Lombok:</strong> Para reducir el código boilerplate (getters, setters, constructores, etc.).</li>
  <li><strong>Bean Validation (JSR-380):</strong> Para la validación de datos de entrada en las solicitudes.</li>
  <li><strong>MySQL:</strong> Sistema de gestión de bases de datos relacional (la configuración para la conexión a la base de datos se asume implícita en el código, probablemente en <code>application.properties</code> o <code>application.yml</code>).</li>
  <li><strong>BCrypt:</strong> Para el hashing seguro de contraseñas.</li>
</ul>

<h2>Funcionalidades:</h2>

<h3>Autenticación de Usuarios:</h3>
<ul>
  <li>Los usuarios pueden registrarse (código no mostrado pero implícito en <code>UsuarioRepository</code> y la lógica de negocio esperada) y autenticarse proporcionando su nombre de usuario y contraseña.</li>
  <li>Tras una autenticación exitosa, se genera un token JWT que el cliente debe enviar en las cabeceras de las siguientes solicitudes para acceder a recursos protegidos.</li>
</ul>

<h3>Gestión de Tópicos (Hilos de Discusión):</h3>
<ul>
  <li><strong>Creación:</strong> Los usuarios autenticados pueden crear nuevos tópicos proporcionando un título, un mensaje, su ID de autor y el nombre del curso al que pertenece el tópico.</li>
  <li><strong>Listado:</strong> Se pueden listar todos los tópicos, con soporte para paginación.</li>
  <li><strong>Consulta por ID:</strong> Se puede obtener un tópico específico por su ID.</li>
  <li><strong>Actualización:</strong> Los usuarios autenticados pueden actualizar la información de un tópico (título, mensaje, nombre del curso).</li>
  <li><strong>Eliminación:</strong> Los usuarios autenticados pueden eliminar un tópico por su ID.</li>
</ul>

<h2>Flujo de Autenticación:</h2>

<ol>
  <li>El cliente envía una solicitud POST a <code>/auth</code> con las credenciales del usuario (nombre de usuario y contraseña).</li>
  <li><code>AutenticacionController</code> recibe la solicitud y utiliza <code>AuthenticationManager</code> para autenticar al usuario contra la base de datos.</li>
  <li>Si la autenticación es exitosa, <code>GeneracionGestionToken</code> (o <code>TokenService</code>) genera un token JWT.
    <ul>
      <li>El token incluye información como el emisor (issuer), el sujeto (subject - que es el nombre de usuario), el ID del usuario, la fecha de creación y la fecha de expiración.</li>
      <li>El token se firma utilizando el algoritmo HMAC256 con una clave secreta configurada en la aplicación.</li>
    </ul>
  </li>
  <li>El token se devuelve al cliente en la respuesta.</li>
  <li>El cliente almacena el token y lo envía en la cabecera <code>Authorization</code> (con el prefijo <code>Bearer </code>) en cada solicitud a recursos protegidos.</li>
  <li><code>SecurityFilter</code> intercepta cada solicitud:
    <ul>
      <li>Extrae el token de la cabecera <code>Authorization</code>.</li>
      <li>Valida el token utilizando <code>TokenService.getSubject()</code>, y luego <code>AutenticationService</code> para cargar usuario.</li>
      <li>Si el token es válido, crea un objeto <code>UsernamePasswordAuthenticationToken</code> y lo establece en el contexto de seguridad de Spring.</li>
      <li>Si el token no es válido o ha expirado, la solicitud se rechaza.</li>
    </ul>
  </li>
</ol>

<h2>Seguridad:</h2>

<ul>
  <li><strong>Hashing de Contraseñas:</strong> Las contraseñas se almacenan en la base de datos utilizando BCrypt, un algoritmo de hashing seguro.</li>
  <li><strong>JWT:</strong> La autenticación se realiza mediante JWT, lo que permite una arquitectura sin estado y evita la necesidad de almacenar sesiones en el servidor.</li>
  <li><strong><code>@Transactional</code>:</strong> Se utiliza en los métodos de servicio que modifican la base de datos para garantizar la atomicidad de las operaciones.</li>
  <li><strong>Validación de Datos:</strong> Se utiliza Bean Validation para validar los datos de entrada en las solicitudes, evitando datos incorrectos o maliciosos.</li>
  <li><strong>Manejo de Errores:</strong> <code>TratadorDeErrores</code> proporciona un manejo centralizado de excepciones, devolviendo respuestas de error apropiadas (404 Not Found, 400 Bad Request).</li>
</ul>

<h2>Consideraciones:</h2>

<ul>
  <li>El código asume que la base de datos ya está configurada y que las tablas <code>usuarios</code> y <code>topicos</code> existen con las columnas especificadas en las entidades <code>Usuario</code> y <code>Topico</code>.</li>
  <li>La clave secreta para la firma de los JWT (<code>api.security.token.secret</code>) debe almacenarse de forma segura, idealmente fuera del código fuente (por ejemplo, en variables de entorno o en un servicio de gestión de secretos).</li>
  <li>El código incluye logs de consola para fines de depuración. En un entorno de producción, se recomienda utilizar un framework de logging adecuado (como Logback o Log4j) y configurar los logs para que se almacenen en archivos o se envíen a un sistema de gestión de logs centralizado.</li>
</ul>

<p><strong>En resumen, este proyecto proporciona una base sólida para un sistema de foros seguro y escalable, implementando las mejores prácticas de seguridad y utilizando tecnologías modernas de desarrollo web.</strong></p>

<h5>VIDEO aqui : </h4>[https://www.youtube.com/embed/jUqsKfnCJKI" ](https://youtu.be/jUqsKfnCJKI)
