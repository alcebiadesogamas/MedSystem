/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.util.Date;
/**
 *
 * @author bruno
 */
public class Paciente {
    private int idPaciente;
    private String nomePaciente;
    //private Date dataNasc = new Date();
    private String dataNasc;
    private String sexo;
    private String rg;
    private String cpf;
    private String profissao;
    private String filiacao;
    private int idEndereco;
    private Endereco end;

    public Paciente(String nomePaciente, String dataNasc, String sexo, String rg, String cpf, String profissao, String filiacao) {
        this.nomePaciente = nomePaciente;
        this.dataNasc = dataNasc;
        this.sexo = sexo;
        this.rg = rg;
        this.cpf = cpf;
        this.profissao = profissao;
        this.filiacao = filiacao;
    }

    public Endereco getEnd() {
        return end;
    }

    public void setEnd(Endereco end) {
        this.end = end;
    }
    
    
    
    public Paciente(){
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getNomePaciente() {
        return nomePaciente;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public String getFiliacao() {
        return filiacao;
    }

    public void setFiliacao(String filiacao) {
        this.filiacao = filiacao;
    }

    public int getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(int idEndereco) {
        this.idEndereco = idEndereco;
    }

    
}
