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
public class Agendamento {
    private int idAgendamento;
    private String dataAgendamento;
    private String horaAgendamento; 
    private String tipoAgendamento;
    private int idSecretaria;
    private int idPaciente;
    private String presenca;
    private String nomePaciente;

    public Agendamento(String horaAgendamento, String dataAgendamento, String tipoAgendamento, String nomePaciente, int idAgendamento, int presenca) {
        
        
        this.horaAgendamento = horaAgendamento;
        this.dataAgendamento = dataAgendamento;
        this.tipoAgendamento = tipoAgendamento;
        this.nomePaciente = nomePaciente;
        this.idAgendamento = idAgendamento;
        if(presenca == 1)
            this.presenca = "Sim";
        else
            this.presenca = "Não";
    }
    
    public Agendamento(String horaAgendamento, String dataAgendamento, String tipoAgendamento) {
        
        
        this.horaAgendamento = horaAgendamento;
        this.dataAgendamento = dataAgendamento;
        this.tipoAgendamento = tipoAgendamento;
        
    }
    
    public Agendamento(String horaAgendamento, String dataAgendamento, String tipoAgendamento, int idPaciente) {
        
        
        this.horaAgendamento = horaAgendamento;
        this.dataAgendamento = dataAgendamento;
        this.tipoAgendamento = tipoAgendamento;
        this.idPaciente = idPaciente;
        
    }

    
    public String getPresenca() {
        return presenca;
    }

    public void setPresenca(String presenca) {
        this.presenca = presenca;
    }
    public int getIdAgendamento() {
        return idAgendamento;
    }

    public void setIdAgendamento(int idAgendamento) {
        this.idAgendamento = idAgendamento;
    }

    public String getDataAgendamento() {
        return dataAgendamento;
    }

    public void setDataAgendamento(String dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }

    public String getHoraAgendamento() {
        return horaAgendamento;
    }

    public String getNomePaciente() {
        return nomePaciente;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    public void setHoraAgendamento(String horaAgendamento) {
        this.horaAgendamento = horaAgendamento;
    }

    public String getTipoAgendamento() {
        return tipoAgendamento;
    }

    public void setTipoAgendamento(String tipoAgendamento) {
        this.tipoAgendamento = tipoAgendamento;
    }

    public int getIdSecretaria() {
        return idSecretaria;
    }

    public void setIdSecretaria(int idSecretaria) {
        this.idSecretaria = idSecretaria;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }
    
    
    
}
