ABOUT:
This encapsulates the necessary JWT configuration when securing your Spring boot API with JWT. 
Provided configuration: 
(1) Authentication: creates jwt token with userdetails, secret-key, expiration time and issuer
(2) Authorization: validates token for expiration and any related issue

USAGE:
(1) Open terminal
(2) Clone repo: `git clone https://github.com/divad1998/jwt.git`
(2) Enter `cd jwt` 
(3) Run `mvn clean install`
(4) Enter `cd target`
(5) Enter `mvn install:install-file -Dfile=spring.boot.jwt-0.0.1.jar -DgroupId=com.nigenial -DartifactId=spring.boot.jwt -Dversion=0.0.1 -Dpackaging=jar`
(6) Declare the library as a dependency in your pom.xml (using the above group id, artifact id and version).
(7) Add this to your MAIN class (below @SpringBootApplication): `@ComponentScan(basePackages = {"com.nigenial", "<your-project-root-package"})`
(8) Declare values for these properties: `jwt.secret-key`, `jwt.life-time.milliseconds`, and `jwt.issuer` in your properties file. 

EXAMPLE:
`
public class AuthService {
`@Autowired
  private JWTService jwtService;
`
`public ResponseEntity<LoginResponse> authenticate(LoginRequest request){

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
  `

  `
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
`

NOTE: you don't need to declare `spring-boot-starter-web` and `spring-boot-starter-security` dependencies again, because they are transitivelty imported by the library.
