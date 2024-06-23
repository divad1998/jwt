### ***ABOUT***
A library for development environments which provides the necessary JWT configuration for securing your Spring boot API with JWT. 
###### Provided configuration: 
1. Authentication: creates jwt token with userdetails, secret-key, expiration time and issuer (using a JWTService class)
2. Authorization: validates token for expiration and any related issue (using a JWTFIlter class)

### ***USAGE***
1. Open terminal
2. Clone repo: `git clone https://github.com/divad1998/jwt.git`
3. Enter and hit `cd jwt` 
4. Enter and hit `mvn clean install`
5. Enter and hit `cd target`
6. Enter and hit `mvn install:install-file -Dfile=spring.boot.jwt-0.0.1.jar -DgroupId=com.nigenial -DartifactId=spring.boot.jwt -Dversion=0.0.1 -Dpackaging=jar`
7. Declare the library as a dependency in your pom.xml (using the above group id, artifact id and version).
8. Add this to your MAIN class (below @SpringBootApplication): `@ComponentScan(basePackages = {"com.nigenial", "<your-project-root-package"})`
9. Declare values for these properties: `jwt.secret-key`, `jwt.life-time.milliseconds`, and `jwt.issuer` in your properties file. 

### ***EXAMPLE***

```java
@SpringBootApplication
@ComponentScan(basePackages = {"com.nigenial", "com.example.demo"})
public class ProjectTwoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectTwoApplication.class, args);
	}

}

@Service
public class AuthService {
        @Autowired
        private JWTService jwtService;

        public ResponseEntity<LoginResponse> authenticate(LoginRequest request){

                Authentication auth = authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        
                if( auth != null ){
                    AccountUser user = userRepository.findByUsername(request.getUsername());
                    String token = jwtService.createToken(user);
                    return new ResponseEntity<>(LoginResponse.builder().user(user).token(token).build(), HttpStatus.OK);
                }
                return null;
        }
}   


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JWTFilter jwtFilter; 

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity customSecurity) throws Exception{

        return customSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requestRegistry -> requestRegistry
                    .requestMatchers("/api/users/register", "/api/users/login").permitAll()
                    .requestMatchers("/api/users/all").hasAnyAuthority(Role.ADMIN.name())
                    .anyRequest().authenticated())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
```
**NOTE**: you don't need to declare `spring-boot-starter-web` and `spring-boot-starter-security` dependencies again, because they are transitivelty imported by the library.
