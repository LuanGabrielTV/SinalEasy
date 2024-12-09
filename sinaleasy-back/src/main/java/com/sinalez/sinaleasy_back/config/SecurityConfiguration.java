// package br.com.jonascamargo.sinaleasy.config;

// import org.springframework.context.annotation.Configuration;

// @Configuration
// public class SecurityConfiguration {
//     private SecurityFilter securityFilter;

//     public SecurityConfiguration(SecurityFilter securityFilter) {
//         this.securityFilter = securityFilter;
//     }

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//         return httpSecurity
//                 .csrf(csrf -> csrf.disable())
//                 .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                 .authorizeHttpRequests(authorize -> authorize
//                         .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
//                         .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
//                         // .requestMatchers(HttpMethod.GET, "/api/signals/**").permitAll()
//                         // .requestMatchers(HttpMethod.POST, "/api/cities/").hasRole("ADMIN")
//                         .anyRequest().authenticated()
//                 )
//                 .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
//                 .build();
//     }

//     @Bean
//     public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//         return authenticationConfiguration.getAuthenticationManager();
//     }

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }
// }