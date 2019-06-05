package com.iposprinter.printertestdemo.dto;

public class Locacoes {

    private String id;
    private String fiscal_id;
    private String motorista;
    private String placa;
    private String tipo;
    private String veiculoNacao;
    private String tempo;
    private String dataHora;
    private String dataHoraExpiracao;
    private String vaga;
    private String moeda;
    private String valorPago;
    private String latitude;
    private String longitude;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.placa +" | "+this.motorista+" Vaga: "+this.vaga+" Entrada "+this.dataHora+ " até: "+this.dataHoraExpiracao;
    }

    public String getFiscal_id() {
        return fiscal_id;
    }

    public void setFiscal_id(String fiscal_id) {
        this.fiscal_id = fiscal_id;
    }

    public String getMotorista() {
        return motorista;
    }

    public void setMotorista(String motorista) {
        this.motorista = motorista;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getVeiculoNacao() {
        return veiculoNacao;
    }

    public void setVeiculoNacao(String veiculoNacao) {
        this.veiculoNacao = veiculoNacao;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
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

    public String getVaga() {
        return vaga;
    }

    public void setVaga(String vaga) {
        this.vaga = vaga;
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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
