package br.com.andersonsoares.madeiramadeirateste.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by andersonsoares on 04/03/2018.
 */

public class Transporte implements Parcelable {
    public String tipo;
    public String endereco;
    public double latitude;
    public double longitude;
    public String telefone;
    public String celular;
    public String nome;
    public String instrucoes;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getInstrucoes() {
        return instrucoes;
    }

    public void setInstrucoes(String instrucoes) {
        this.instrucoes = instrucoes;
    }



    public Transporte(String tipo, String endereco, double latitude, double longitude, String telefone, String celular, String nome, String instrucoes) {
        this.tipo = tipo;
        this.endereco = endereco;
        this.latitude = latitude;
        this.longitude = longitude;
        this.telefone = telefone;
        this.celular = celular;
        this.nome = nome;
        this.instrucoes = instrucoes;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tipo);
        dest.writeString(this.endereco);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeString(this.telefone);
        dest.writeString(this.celular);
        dest.writeString(this.nome);
        dest.writeString(this.instrucoes);
    }

    protected Transporte(Parcel in) {
        this.tipo = in.readString();
        this.endereco = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.telefone = in.readString();
        this.celular = in.readString();
        this.nome = in.readString();
        this.instrucoes = in.readString();
    }

    public static final Parcelable.Creator<Transporte> CREATOR = new Parcelable.Creator<Transporte>() {
        @Override
        public Transporte createFromParcel(Parcel source) {
            return new Transporte(source);
        }

        @Override
        public Transporte[] newArray(int size) {
            return new Transporte[size];
        }
    };
}
