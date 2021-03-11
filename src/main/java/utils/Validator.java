/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Jeferson
 */
public class Validator {
    public static boolean isValidEmail(String email) {
        String pattern = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
        return email.matches(pattern);
    }

    public static boolean isValidTelefone(String telefone) {
        return telefone.matches("[(]?[0-9]{2}[)]?[ ]?[0-9]{4,5}[ ,-]?[0-9]{4}");
    }
}
