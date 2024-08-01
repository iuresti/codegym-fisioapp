package codegym.tequila.fisioapp.service.impl;

import codegym.tequila.fisioapp.config.UserDetailsImpl;
import codegym.tequila.fisioapp.dto.UserDto;
import codegym.tequila.fisioapp.service.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

@Service
public class JwtServiceImpl implements JwtService {

    private static final Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    @Override
    public String getToken(UserDto userDto) {
        UserDetails user = new UserDetailsImpl(userDto);
        return getToken(new HashMap<>(), user);
    }

    private String getToken(HashMap<String,Object> extraClaims, UserDetails user) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(KEY, SignatureAlgorithm.HS256)
                .compact();
    }


}
