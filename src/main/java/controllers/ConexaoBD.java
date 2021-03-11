package controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexaoBD {

    private static final String USUARIO = "root";
    private static final String SENHA = "";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/aulalpg1";
    
    public Connection con;
    public Statement st;
    public ResultSet rs;
    
    public ConexaoBD(){
        try{
            Class.forName(DRIVER);         
            con = DriverManager.getConnection(URL, USUARIO, SENHA);
            st = con.createStatement();
            
        }catch (ClassNotFoundException e) {
            System.out.println("Erro de conexão: driver não encontrado");
            e.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Erro de conexão: parâmetros inválidos");
        }
    }

    public Statement createStatement() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
