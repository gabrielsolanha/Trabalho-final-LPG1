/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Date;

/**
 *
 * @author Jeferson
 */
public class DateFormater {
    public static String parseToString(Date data) {
        String valor = data.getYear()+"-"+data.getMonth()+"-"+data.getDate();
        return valor;
    }
}
