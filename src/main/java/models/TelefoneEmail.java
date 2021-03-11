package models;

import java.util.ArrayList;
import models.Contato;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.Date;
// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.sql.Statement;
import controllers.ConexaoBD;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import utils.Validator;
import utils.DateFormater;
import java.util.List;

public class TelefoneEmail {
    public enum Tipo {
        EMAIL,
        TELEFONE
    }

    private String id;
    private String rotulo;
    private String valor;
    private Tipo tipo;
    private Contato contato;
    private ConexaoBD conexao;

    public TelefoneEmail() {
        conexao = new ConexaoBD();
    }

    public TelefoneEmail(String id, String rotulo, String valor, Tipo tipo, String contatoId){
        this.id = id;
        this.rotulo = rotulo;
        this.valor = valor;
        this.tipo = tipo;

        this.contato = Contato.findById(contatoId);
    }

    public List<TelefoneEmail> consultarTelefoneEmail(String idcontato) {// Pode ter algum filtro aqui
        List<TelefoneEmail> list = new ArrayList<TelefoneEmail>();
        // Executa Select
        // String comando = "SELECT * FROM ...";
        // Statement Instrucao;
        // Instrucao = conexao.createStatement();
        // ResultSet result = null;
        // result = Instrucao.executeQuery(comando);

        // while(result.next()){
        //     String id = result.getString("id");
        //     String rotulo = result.getString("rotulo");
        //     String tipo = result.getString("tipo");
        //     String valor = result.getString("valor");
        //     list.add(new TelefoneEmail(id, rotulo, valor, tipo));
        // }
        
        String sql = "SELECT * FROM `telefoneemail` WHERE `idcontato`='"+idcontato+"';";
        
        ResultSet rs = null;
        
        //List<Cliente> clientes = new ArrayList();
        
        
        try{
            Statement stm = this.conexao.con.createStatement();

            rs = stm.executeQuery(sql);
            Tipo tipos;
            
            while (rs.next()){
                 
                TelefoneEmail telmail = new TelefoneEmail();
                
                telmail.setId(rs.getString("idtelefoneemail"));
                telmail.setRotulo(rs.getString("rotulo"));
                telmail.setValor(rs.getString("valor"));
                if(rs.getString("tipo").equals("telefone")){
                    tipos = Tipo.TELEFONE;
                }else{
                    tipos = Tipo.EMAIL;
                }
                telmail.setTipo(tipos);
                
                list.add(telmail);
            }
                                    
        }catch(SQLException ex){
            System.out.println("Erro na consulta de dados.");
            ex.printStackTrace();
        }   
        
        
        
        
        System.err.println("Listou tefoneEmails");
        return list;
    }

    public static TelefoneEmail findById(String id) {
        // executa select
        System.err.println("Mostrou TelefoneEmail com id: "+id);
        return new TelefoneEmail();
    }

    public void create(String rotulo, String valor, Tipo tipo, String contatoId) {
        this.tipo = tipo;
        this.rotulo = rotulo;
        this.valor = valor;
        //this.contato = Contato.findById(contatoId);
        String tipos;
        if(tipo == Tipo.EMAIL){
            tipos = "email";
        }else{
            tipos = "telefone";
        }
        try {
            String query = "INSERT INTO `telefoneemail` (`idtelefoneemail`, `rotulo`, `valor`, `tipo`, `idcontato`) VALUES (UUID_SHORT(), ?, ?, ?, ?)";
            
            PreparedStatement st = this.conexao.con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                        
            st.setString(1, rotulo);
            st.setString(2, valor);
            st.setString(3, tipos);
            st.setString(4, contatoId);
            
            int affectedRows = st.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            
        } catch(Exception ex) {
            ex.printStackTrace();
            System.err.println("Erro na incerção");
        } 
        
        
        
        String idFromDb = "idfacil";
        this.id = idFromDb;

        System.err.println("Criou TelefoneEmail");
    }

    public void update() {
        // Caso a pessoa jÃ¡ atualizou pelos setters
        // Executa Update

        System.err.println("Updated: TelefoneEmail id: " + this.id);
    }

    public void update(String id,String rotulo, String valor) {
        // Atualiza direto e depois salva
        this.id = id;
        this.rotulo = rotulo;
        this.valor = valor;
        // Executa Update
        
        try {
            String query = "UPDATE `telefoneemail` SET `rotulo` = ?, `valor` = ? WHERE `telefoneemail`.`idtelefoneemail` = ?";
            
            PreparedStatement st = this.conexao.con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                        
            st.setString(1, rotulo);
            st.setString(2, valor);
            st.setString(3, id);
            
            int affectedRows = st.executeUpdate();
            
            
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            
            
        } catch(Exception ex) {
            ex.printStackTrace();
        } 
        
        
        System.err.println("Updated: TelefoneEmail id: " + this.id);
    }

    public void delete() {
        String deletedId = this.id;
        this.id = null;
        this.tipo = null;
        this.rotulo = null;
        this.valor = null;
        // Executa Delete
        System.err.println("Deleted: TelefoneEmail id: " + deletedId);
    }

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getRotulo() {
        return this.rotulo;
    }

    public void setRotulo(String valor) {
        this.rotulo = valor;
    }

    public String getValor() {
        return this.valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Tipo getTipo() {
        return this.tipo;
    }

    public void setTipo(Tipo valor) {
        this.tipo = valor;
    }
    
    public Contato getContato() {
        return this.contato;
    }

}