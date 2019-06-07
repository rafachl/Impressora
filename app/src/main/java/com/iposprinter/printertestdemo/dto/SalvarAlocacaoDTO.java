package com.iposprinter.printertestdemo.dto;

public class SalvarAlocacaoDTO {

    private String  tempo;
    private String tipo;
    private String moeda;
    private String vaga;
    private String valorPago;
    private String fiscal_id;
    private String placa;
 private String motorista;
 private String motoristaRuc;

    public String getMotoristaRuc() {
        return motoristaRuc;
    }

    public void setMotoristaRuc(String motoristaRuc) {
        this.motoristaRuc = motoristaRuc;
    }

    public String getMotorista() {
        return motorista;
    }

    public void setMotorista(String motorista) {
        this.motorista = motorista;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMoeda() {
        return moeda;
    }

    public void setMoeda(String moeda) {
        this.moeda = moeda;
    }

    public String getVaga() {
        return vaga;
    }

    public void setVaga(String vaga) {
        this.vaga = vaga;
    }

    public String getValorPago() {
        return valorPago;
    }

    public void setValorPago(String valorPago) {
        this.valorPago = valorPago;
    }

    public String getFiscal_id() {
        return fiscal_id;
    }

    public void setFiscal_id(String fiscal_id) {
        this.fiscal_id = fiscal_id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
}
