package com.mrjob.configuraciones;

import com.mrjob.service.impl.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    /**  ------------------------------  IMPORTANTE ----------------------------------
     *Este filtro intercepta todas las invocaciones al servidor (extiende de OncePerRequestFilter) y:
     * Comprueba la existencia del token (existeJWTToken(...)).
     * Si existe, lo desencripta y valida (validateToken(...)).
     * Si está todo_OK, añade la configuración necesaria al contexto de Spring para autorizar la petición (setUpSpringAuthentication(...)).
     * Para este último punto, se hace uso del objeto GrantedAuthority que se incluyó en el token durante el proceso de autenticación.
     */

    /**
     * Estos token están compuestos por tres partes:
     * Header: contiene el hash que se usa para encriptar el token.
     * Payload: contiene una serie de atributos (clave, valor) que se encriptan en el token.
     * Firma: contiene header y payload concatenados y encriptados (Header + “.” + Payload + Secret key).
     */

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestTokenHeader = request.getHeader("Authorization"); /**request = es la peticion */
        String username = null;
        String jwtToken = null;

        if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")){
            jwtToken = requestTokenHeader.substring(7);

            try {
                username = this.jwtUtils.extractUsername(jwtToken);
            }catch (ExpiredJwtException expiredJwtException){
                System.out.println("El Token ha Expirado...!");
            }catch (Exception exception){
                exception.printStackTrace();
            }
        }else {
            /** UN TOKEN PARA QUE SEA VALIDO SIEMPRE TIENE QUE EMPEZAR CON  -==> "Bearer "
             SI NO INICIA CON Bearer NO ES VALIDO
             */
            System.out.println("Token invalido, No empieza con bearer String");
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){ /** ESTE IF VERIFICA SI ES EL TOKEN VALIDOOOO-....*/
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if(this.jwtUtils.validateToken(jwtToken,userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }
        }else{ /** SI NO ES VALIDO EL TOKEN ENTONCES....*/
            System.out.println("El Token no es valido...!");
        }
        filterChain.doFilter(request, response); /** Ejecuta este filtro y le pasamos la peticion y la respuesta */
    }
}
