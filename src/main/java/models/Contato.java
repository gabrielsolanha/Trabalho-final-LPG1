package models;

import controllers.ConexaoBD;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import utils.Validator;
import models.TelefoneEmail.Tipo;
import utils.DateFormater;
import java.util.List;

public class Contato {
    private String id;
    private String nome;
    private String sobrenome;
    private Date dataNascimento;
    private ArrayList<TelefoneEmail> telefoneEmails;
    private ConexaoBD conexao;

    public Contato() {
        conexao = new ConexaoBD();
    }

    public Contato(
        String nome, 
        String sobrenome, 
        Date dataNascimento,
        ArrayList<TelefoneEmail> telefoneEmails
    ) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.dataNascimento = dataNascimento;
        this.telefoneEmails = telefoneEmails;
        conexao = new ConexaoBD();
    }

    public Contato(String id, String nome, String sobrenome, Date dataNascimento,
            ArrayList<TelefoneEmail> telefoneEmails) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.dataNascimento = dataNascimento;
        this.telefoneEmails = telefoneEmails;
    }

    public List<Contato> consultarContatos() {// Pode ter algum filtro aqui
        List<Contato> list = new ArrayList<Contato>();
        // Executa Select
        // String comando = "SELECT * FROM ...";
        // Statement Instrucao;
        // Instrucao = conexao.createStatement();
        // ResultSet result = null;
        // result = Instrucao.executeQuery(comando);
        
        String sql = "SELECT * FROM `contato`;";
        
        ResultSet rs = null;
        
        //List<Cliente> clientes = new ArrayList();
        
        try{
            Statement stm = this.conexao.con.createStatement();

            rs = stm.executeQuery(sql);
            
            while (rs.next()){
                 
                Contato contato = new Contato();
                
                contato.setId(rs.getString("id"));
                contato.setNome(rs.getString("nome"));
                contato.setSobrenome(rs.getString("sobrenome"));
                contato.setDataNascimento(rs.getDate("datanasc"));
                
                list.add(contato);
            }
                                    
        }catch(SQLException ex){
            System.out.println("Erro na consulta de dados.");
            ex.printStackTrace();
        }   
        
        System.err.println("Listou Contatos");
        return list;
        // while(result.next()){
        // String id = result.getString("id");
        // String rotulo = result.getString("rotulo");
        // String tipo = result.getString("tipo");
        // String valor = result.getString("valor");
        // list.add(new TelefoneEmail(id, rotulo, valor, tipo));
        // }
    }

    public static Contato findById(String id) {
        // executa select
        System.err.println("Mostrou Contato com id: " + id);
        return new Contato();
    }

    public void create() {
        try {
            String query = "INSERT INTO `contato` (`id`, `nome`, `sobrenome`, `datanasc`) "
                    + "VALUES (UUID(), '"+this.nome+"', '"+this.sobrenome+"','"+DateFormater.parseToString(this.dataNascimento)+"')";
            this.conexao.st.executeUpdate(query);

        } catch(Exception ex) {
            ex.printStackTrace();
        } 
        
        String idFromDb = "idfacil";
        this.id = idFromDb;

        System.err.println("Criou Contato");
    }

    public void create(String nome, String sobrenome, Date dataNascimento) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.dataNascimento = dataNascimento;

        try {
            String query = "INSERT INTO `contato` (`id`, `nome`, `sobrenome`, `datanasc`) "
                    + "VALUES (UUID(), ?, ?, ?)";
            
            PreparedStatement st = this.conexao.con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                        
            st.setString(1, this.nome);
            st.setString(2, this.sobrenome);
            st.setDate(3, this.dataNascimento);
            
            int affectedRows = st.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

//            try (ResultSet generatedKeys = st.getGeneratedKeys()) {
//                if (generatedKeys.next()) {
//                    this.id = generatedKeys.getString(1);
//                }
//                else {
//                    throw new SQLException("Creating user failed, no ID obtained.");
//                }
//            }
            
            
        } catch(Exception ex) {
            ex.printStackTrace();
        } 
        

        System.out.println("Criou Contato");
    }

    public void update(String id, String nome, String sobrenome) {
        // Caso a pessoa já atualizou pelos setters
        // Executa Update UPDATE `contato` SET `nome` = 'Gabriel', `sobrenome` = 'Solanha' WHERE `contato`.`id` = '0b09ffa2-8213-11eb-ad20-847bebfcdba6'


        try {
            String query = "UPDATE `contato` SET `nome` = ?, `sobrenome` = ? WHERE `contato`.`id` = ?";
            
            PreparedStatement st = this.conexao.con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                        
            st.setString(1, nome);
            st.setString(2, sobrenome);
            st.setString(3, id);
            
            int affectedRows = st.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            
            
        } catch(Exception ex) {
            ex.printStackTrace();
        } 
        

        System.out.println("Atulizou Contato");
    }

    public void update(String nome, String sobrenome, Date dataNascimento) {
        // Atualiza direto e depois salva
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.dataNascimento = dataNascimento;
        // Executa Update
        System.err.println("Updated: Contato id: " + this.id);
    }

    public void delete() {
        String deletedId = this.id;

        this.id = null;
        this.nome = null;
        this.sobrenome = null;
        this.dataNascimento = null;
        this.telefoneEmails = null;

        // Executa Delete
        System.err.println("Deleted: Contato id: " + deletedId);
    }

    public boolean validar() {
        boolean isValid = true;

        for (TelefoneEmail telefoneEmail : telefoneEmails) {
            String valor = telefoneEmail.getValor();
            if (telefoneEmail.getTipo() == Tipo.EMAIL) {
                isValid = isValid && Validator.isValidEmail(valor);
            } else {
                isValid = isValid && Validator.isValidTelefone(valor);
            }
            if (!isValid)
                System.err.println("TelefoneEmail com valor " + valor + " invalido.");
        }
        return isValid;
    }

    public void createTelefoneEmail(String rotulo, String valor, Tipo tipo) {
        TelefoneEmail newItem = new TelefoneEmail();
        if (this.id != null) {
            newItem.create(rotulo, valor, tipo, this.id);
            this.telefoneEmails.add(newItem);
        } else {
            System.err.println("Voce precisa criar o contato para executar essa acao!");
        }
    }
    
    public void updateTelefoneEmail(String telefoneEmailId, String rotulo, String valor, Tipo tipo) {
        //TelefoneEmail itemToUpdate = this.telefoneEmails.stream();
        //    .filter(telefoneEmail -> telefoneEmailId.equals(telefoneEmail.getId()))
        //    .findFirst()
        //    .orElse(null);
        //if (itemToUpdate != null) {
        //    itemToUpdate.update(rotulo, valor, tipo);
        //} else {
        //    System.err.println("TelefoneEmail invalido");
        //}
    }

    public void deleteTelefoneEmail(String telefoneEmailId) {
//        TelefoneEmail itemToUpdate = this.telefoneEmails.stream()
//            .filter(telefoneEmail -> telefoneEmailId.equals(telefoneEmail.getId()))
//            .findFirst()
//            .orElse(null);
//        if (itemToUpdate != null) {
//            itemToUpdate.delete();
//            this.telefoneEmails.remove(itemToUpdate);
//        } else {
//            System.err.println("TelefoneEmail invalido");
//        }
    }
    public void setId(String id){
        this.id = id;
    }
    public String getId() {
        return this.id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getSobrenome() {
        return this.sobrenome;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Date getDataNascimento() {
        return this.dataNascimento;
    }

    public ArrayList<TelefoneEmail> getTelefoneEmails() {
        return this.telefoneEmails;
    }
    
    public void remover(String id) {

        try {
            String query = "DELETE FROM `contato` WHERE `contato`.`id` = ?";
            
            PreparedStatement st = this.conexao.con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                        
            st.setString(1, id);
            
            int affectedRows = st.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            
        } catch(Exception ex) {
            ex.printStackTrace();
        } 
        

        System.out.println("Deletou Contato");
    }

    
 
}
