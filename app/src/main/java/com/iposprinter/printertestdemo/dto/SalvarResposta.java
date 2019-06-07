package com.iposprinter.printertestdemo.dto;

public class SalvarResposta {
    private String placa;
    private String tempo;
    private String vaga;
    private String fiscal_id;
    private String moeda;
    private String valorPago;
    private String tipo;
    private String dataHora;
    private String data;
    private String motorista;
    private String motoristaRuc;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMotorista() {
        return motorista;
    }

    public void setMotorista(String motorista) {
        this.motorista = motorista;
    }

    public String getMotoristaRuc() {
        return motoristaRuc;
    }

    public void setMotoristaRuc(String motoristaRuc) {
        this.motoristaRuc = motoristaRuc;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public String getVaga() {
        return vaga;
    }

    public void setVaga(String vaga) {
        this.vaga = vaga;
    }

    public String getFiscal_id() {
        return fiscal_id;
    }

    public void setFiscal_id(String fiscal_id) {
        this.fiscal_id = fiscal_id;
    }

    public String getMoeda() {
        return moeda;
    }

    public void setMoeda(String moeda) {
        this.moeda = moeda;
    }

    public String getValorPago() {
        return valorPago;
    }

    public void setValorPago(String valorPago) {
        this.valorPago = valorPago;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public String getDataHoraExpiracao() {
        return dataHoraExpiracao;
    }

    public void setDataHoraExpiracao(String dataHoraExpiracao) {
        this.dataHoraExpiracao = dataHoraExpiracao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrlValidacao() {
        return urlValidacao;
    }

    public void setUrlValidacao(String urlValidacao) {
        this.urlValidacao = urlValidacao;
    }

    private String dataHoraExpiracao;
    private String id;
    private String urlValidacao;

}

