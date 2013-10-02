package br.com.imobiliariaservices.model;

/**
 *
 * @author Ricardo
 */
public class Conexao {
    
    private String url;
    private String usuario;
    private String senha;
    
    public Conexao(String url, String usuario, String senha){
        this.url = url;
        this.usuario = usuario;
        this.senha = senha;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    } 
    
}
