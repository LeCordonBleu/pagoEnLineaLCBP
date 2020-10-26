/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.lecordonbleu.pagoenlinea.utils;

import java.util.Base64;

/**
 *
 * @author eduardo
 */
public class Codificador {

    public String asciiToHex(String asciiStr) {
        char[] chars = asciiStr.toCharArray();
        StringBuilder hex = new StringBuilder();
        for (char ch : chars) {
            hex.append(Integer.toHexString((int) ch));
        }

        return hex.toString();
    }

    public String hexToAscii(String hexStr) {
        StringBuilder output = new StringBuilder("");

        try {
            for (int i = 0; i < hexStr.length(); i += 2) {
                String str = hexStr.substring(i, i + 2);
                output.append((char) Integer.parseInt(str, 16));
            }
            return output.toString();
        } catch (Exception e) {
            return "";
        }

    }

    public String encode(String txt) {
        String encoded = new String(Base64.getEncoder().encode(txt.getBytes()));
        return encoded;

    }

    public String decode(String txt) {
        String decoded = new String(Base64.getDecoder().decode(txt));
        return decoded;

    }
}
