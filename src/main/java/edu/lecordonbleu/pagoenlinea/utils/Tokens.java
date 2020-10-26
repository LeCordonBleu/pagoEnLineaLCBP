/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.lecordonbleu.pagoenlinea.utils;

import io.fusionauth.jwt.Signer;
import io.fusionauth.jwt.Verifier;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACSigner;
import io.fusionauth.jwt.hmac.HMACVerifier;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 *
 * @author eduardo
 */
public class Tokens {

    public String generaToken() {
        // Construya un firmante HMAC usando un hash SHA-256
        Signer signer = HMACSigner.newSHA256Signer("LCBP2020");

        // Cree un nuevo JWT con un emisor (iss), emitido en (iat), sujeto (sub) y vencimiento (exp)
        JWT jwt = new JWT().setIssuer("LE_CORDON_BLEU")//emisor
                .setIssuedAt(ZonedDateTime.now(ZoneOffset.UTC))//emitido
                .setSubject("g1e33sbt0n-027pca5gbbo07s")//subjeto
                .setExpiration(ZonedDateTime.now(ZoneOffset.UTC).plusMinutes(60));//vencimiento

        // Firme y codifique el JWT en una representación de cadena JSON
        String encodedJWT = JWT.getEncoder().encode(jwt, signer);

        return encodedJWT;
    }

    public boolean validaToken(String token) {

        boolean valida = false;

        // Cree un verificador HMC utilizando el mismo secreto que se usó para firmar el JWT
        Verifier verifier = HMACVerifier.newVerifier("LCBP2020");

        try {
            // Verifique y decodifique la cadena codificada JWT en un objeto rico
            JWT jwt2 = JWT.getDecoder().decode(token, verifier);

            // Afirmar que el tema de la JWT es como se esperaba
            if (jwt2.subject.equals("g1e33sbt0n-027pca5gbbo07s")) {
                valida = true;
            }
        } catch (Exception e) {
            valida = false;
        }

        return valida;

    }

}
