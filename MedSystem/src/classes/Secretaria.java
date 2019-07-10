/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

/**
 *
 * @author bruno
 */
public class Secretaria {
    private int idSecretaria;
    private String nomeSecretaria;
    private String loginSecretaria;
    private String senhaSecretaria;

    public Secretaria(String nomeSecretaria, String loginSecretaria, String senhaSecretaria) {
        this.nomeSecretaria = nomeSecretaria;
        this.loginSecretaria = loginSecretaria;
        this.senhaSecretaria = senhaSecretaria;
    }

    public int getIdSecretaria() {
        return idSecretaria;
    }

    public void setIdSecretaria(int idSecretaria) {
        this.idSecretaria = idSecretaria;
    }

    public String getNomeSecretaria() {
        return nomeSecretaria;
    }

    public void setNomeSecretaria(String nomeSecretaria) {
        this.nomeSecretaria = nomeSecretaria;
    }

    public String getLoginSecretaria() {
        return loginSecretaria;
    }

    public void setLoginSecretaria(String loginSecretaria) {
        this.loginSecretaria = loginSecretaria;
    }

    public String getSenhaSecretaria() {
        return senhaSecretaria;
    }

    public void setSenhaSecretaria(String senhaSecretaria) {
        this.senhaSecretaria = senhaSecretaria;
    }
    
    
}
