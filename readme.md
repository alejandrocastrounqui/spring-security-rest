#Spring security RESTful CORS

Este proyecto es un ejemplo de como configurar Spring security con un esquema RESTful y un sistema de autenticación basado en cookies. Además se incluye una configuración CORS integrada a la seguridad.

#### Spring puede administrar dos tipos de session de manera predeterminada:
___
**Basic-autentication:**

La aplicación frontend envia un header adicional con las credenciales del usuario en cada request.
___

**Basado en cookies:**

Cuando la aplicación *frontend* envia las credenciales, Java incluye en el encabezado de la respuesta una cookie con el identificador de sesión (SESSIONID).
___
    
####El inconveniente:
Spring utiliza redireccionamientos HTTP para manejar el flujo de eventos de la sesión,
 lo que genera cierto acomplamiento entre el servidor de datos, y los servidores aplicativos.    
Spring provee, por ejemplo, un protocolo para configurar la ruta a la que debe redireccionarse a un usuario ante un inicio de sesión exitoso.
 Si el servidor de datos es utilizado por dos aplicaciones con diferentes rutas de inicio, se genera un conflicto.

En este proyecto se implementan un conjunto de componentes que permiten usar los servicios sin acoplar los mismos a ninguna interfaz de usuario.

Para facilitar la comprension del proyecto, se detalla cada una de las funcionalidades a continuación:


`#castro.alejandro.security.WebConfiguration`
```java
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable()
		    .authorizeRequests()
			.antMatchers("/cors").permitAll()
			.antMatchers("/public/**").permitAll()
			.antMatchers("/protected/**").hasAuthority("PARTIAL_AUTHENTICATED")
			.antMatchers("/session/complete").hasAuthority("PARTIAL_AUTHENTICATED")
			.anyRequest().hasAuthority("FULLY_AUTHENTICATED")
		.and()
			.formLogin()
			.permitAll()
			.loginProcessingUrl(LOGIN_PATH)
			.successHandler(restAuthSuccessHandler)
	        .failureHandler(restAuthFailureHandler)
		.and()
	        .logout()
	        .permitAll()
	        .logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT_PATH, LOGOUT_METHOD))
	        .logoutSuccessHandler(restLogoutSuccessHandler)
	    .and()
	        .exceptionHandling()
	        .authenticationEntryPoint(restAuthenticationEntryPoint);;
		
    }
```

Eventualmente Spring invocará el método **configure** de la implementación de WebSecurityConfigurerAdapter existente en la fábrica de *beans*.
Cuando esto suceda, se configurarán ciertas propiedades del servidor HTTP.    

* En principio, la seguridad:    
`authorizeRequests()` activa la seguridad de spring, y a continuación provee una interfaz *fluent* para configurar el acceso a las rutas del servidor segun los permisos del usuario registrado.    

* Luego, la configuración de inicio de sesión:    
En este ejemplo, se permite a cualquier usuario acceder al servicio y se configura la ruta de acceso.
Por ultimo, y muy importante el componente que se encargará de manejar en inicio de sesión exitoso. La implementación predeterminada de este componente responde un paquete con status 302 para que el browser lleve a cabo una acción de redireccionamiento.    

* Por ultimo los parámetros del comportamiento de cierre de sesión. En este caso no solo se configura la ruta de acceso sino también el método HTTP correpondiente. Al configurar el cierre de sesión via `GET` permitimos que un usuario pueda cerrar sesión accediendo a la ruta en la barra de explorador.    

`restAuthenticationEntryPoint` es el componente que permite indicarle a los clientes del servicio que aún no han iniciado sesión, a diferencia del componente nativo que redireccion a `/login` para mostrar un formulario HTML, esta implementación devuelve un response vacio con un `status code` **401**. Esto permite que un cliente aplicativo pueda manejar la respuesta en el contexto de Javascript.    

`#castro.alejandro.security.session.RestAuthSuccessHandler`
```java
	
    @Override
    public void onAuthenticationSuccess(
		HttpServletRequest request,
		HttpServletResponse response,
	    Authentication authentication
    )throws IOException, ServletException {
    	OkStatusFilterChain okStatusFilterChain = new OkStatusFilterChain(response);
    	restCorsFilter.doFilterInternal(request, response, okStatusFilterChain);
    }
```

Al reimplementar el método `onAuthenticationSuccess` evitamos un redireccionamiento, este componente permite que un filtro sintético de CORS verifique que el request proviene de una URL permitida antes de asignar el `status code` **200**. Evitando así ataques de CSRF.

`#castro.alejandro.security.session.RestAuthFailureHandler`
```java
	
    @Override
    public void onAuthenticationFailure(
		HttpServletRequest request,
		HttpServletResponse response,
	    AuthenticationException authException
    )throws IOException, ServletException {
    	FilterChain okStatusFilterChain = new BadRequestStatusFilterChain(response);
    	restCorsFilter.doFilterInternal(request, response, okStatusFilterChain);
    }
```

Un comportamiento análogo al de `RestAuthSuccessHandler` que permite manejar el flujo de intento de inicio de sesión fallido.


`#castro.alejandro.security.session.RestLogoutSuccessHandler`
```java
	
    @Override
    public void onLogoutSuccess(
		HttpServletRequest request, 
		HttpServletResponse response, 
		Authentication authentication
	)throws IOException, ServletException  {
    	FilterChain okStatusFilterChain = new OkStatusFilterChain(response);
    	restCorsFilter.doFilterInternal(request, response, okStatusFilterChain);
    }
    
```

Equivalente a `RestAuthSuccessHandler`, permite manejar el flujo del cierre de sesión.

`#castro.alejandro.security.session.RestAuthenticationProvider`
```java
	    
	@PostConstruct
	public void initialize() {
		this.setUserDetailsService(restUserDetailsService);
		this.setPasswordEncoder(restPaswordEncoder);
	}
    
```

RestAuthenticationProvider es el proveedor de los 2 servicios indispensables para el manejo de una sesión. **UserDetailsService** y **PaswordEncoder**.

La mayoría de los ejemplos muestran la inyección de estos servicios en un metodo de algun otro componente instanciado por Spring, marcado con el annotation @bean.
 No es una práctica aconsejable ya que viola el principio de inyección de dependencias.

`#castro.alejandro.security.service.RestUserDetailsService`
```java
	    
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if(user == null){
			throw new UsernameNotFoundException("");
		}
		RestUserDetails restUserDetails = new RestUserDetails(user);
		restUserDetails.addRole(RestGrantedAuthority.PARTIAL_AUTHENTICATED);
		return restUserDetails;
	}
    
```

RestUserDetailsService implementa la *interface* solicitada por Spring para obtener las credenciales de un usuario por identificador.
Este componente es complicado de entender en algunas oportunidades, asi que le dedicaremos un par de lineas extra.

Cuando un cliente envia sus credenciales, normalmente un identificador único (username) y un password,
 Spring solicita al servicio **UserDetailsService** el usuario del sistema que satisface solo la condición de poseer el identificador o *username* en cuestión.
 Esto se debe a que a diferencia de sistemas antiguos, las contraseñas no se guardan textuales en la base de datos (o cualquier otro servicio de persistencia de datos)
 sino que se guarda con un algoritmo de encriptacion asimétrico aplicado, para que ni siquiera los administradores de la plataforma puedan obtener contraseñas.
 Estos algoritmos a la vez permiten comprobar que dos cadenas hayan sigo generadas desde la misma contraseña inicial (*casi).
 Explicado esto, podemos entender ahora que Spring no podria nunca proveer la contraseña persistida al servicio de búsqueda de usuarios,
 sino que puede verificar la misma con la proporcionada en el intento de inicio de sesión actual. Para esto último, necesita del servicio PaswordEncoder.


`#castro.alejandro.security.service.RestPaswordEncoder`
```java
	    
	@Override
	public String encode(CharSequence rawPassword) {
		//Aplico el algoritmo de encriptación
		return rawPassword+"abc";
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		//comparo con algoritmo de comprobación.
		return (encode(rawPassword)).equals(encodedPassword);
	}
    
```

Como se detalló anteriormente RestPaswordEncoder permite verificar que la contraseña 
proporcionada por el cliente concuerda con el del usuario obtenido teniendo en cuenta el identificador o username.

`encode` se utiliza para encodear las contraseñas antes de persistirlas, por ejemplo en un cambio de contraseña.
 Esto permite a los repositorios o servicios inyectar la instancia existente de este componente y abstraer el algoritmo de encriptado utilizado.
 Lo que perminte configurarlo dinámicamente.    
`matches` permite verificar que `rawPassword` (la contraseña proporcionada por el cliente) concuerda con encodedPassword (la contraseña existente en el sistema).

Este mecanismo evita que el sistema deba guarda en memoria las credenciales del usuario durante la vida de la sesión.

##CORS

`#castro.alejandro.security.CorsConfiguration`
```java
	    
	public void addCorsMappings(CorsRegistry registry){
		registry.addMapping("/**")
		  .allowedOrigins("http://localhost:9001", "http://0.0.0.0:9001", "http://127.0.0.1:9001")
		  .allowedHeaders("*")
		  .allowedMethods("*")
		  .allowCredentials(true);
	}
    
```

CorsConfiguration permite configurar los sitios desde los que se podrá acceder a los servicios proporcionados.
 Si bien la implementación del filtro nativo de Spring cubre la mayoria de las prestaciones necesarias,
 este no contemplará los servicios de seguridad reimplementados, para estos deberemos agregar un filtro sintético que emula el comportamiento
 del componente global.





`#castro.alejandro.security.session.filter.RestCorsFilter`
```java
	    
	public void doFilterInternal(
		HttpServletRequest request, 
		HttpServletResponse response,
		FilterChain filterChain
	)throws ServletException, IOException {
		if (CorsUtils.isCorsRequest(request)) {
			CorsConfiguration corsConfiguration = this.getCorsConfiguration(request);
			if (corsConfiguration != null) {
				boolean isValid = this.processor.processRequest(corsConfiguration, request, response);
				if (!isValid || CorsUtils.isPreFlightRequest(request)) {
					return;
				}
			}
		}
		filterChain.doFilter(request, response);
	}
	
    
```

Como se explicó anteriormente, el filtro original de Spring no es aplicado a los request de inicio y cierre de sesión,
 por lo que se implementó un filtro que emula el comportamiento del componente global,
 de esta manera se mantiene una mayor consistencia, al no duplicar el comportamiento, reutilizando el algoritmo.    
Dado que Spring utiliza un esquema de *middlewares* se agregaron componentes que permiten asignar el `statuscode` correspondiente luego
 del procesamiento del filtro CORS

`#castro.alejandro.security.session.filter.BadRequestStatusFilterChain`
`#castro.alejandro.security.session.filter.OkStatusFilterChain`
`#castro.alejandro.security.session.filter.UnauthorizedRequestStatusFilterChain`








