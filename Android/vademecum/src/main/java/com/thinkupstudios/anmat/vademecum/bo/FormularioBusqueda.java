package com.thinkupstudios.anmat.vademecum.bo;

import java.io.Serializable;

/**
 * Created by FaQ on 12/02/2015.
 * Formulario de Busqueda
 */
public class FormularioBusqueda implements Serializable {
    public static String FORMULARIO_MANUAL = "Formulario_Busqueda";
    public static String PRINCIPIO_ACTIVO = "Principio_Activo";
    private String laboratorio ="";
    private String nombreGenerico ="";
    private String nombreComercial ="";
    private String forma ="";
    private Boolean esRemediar = false;
    private Boolean useLike = true;
    private Boolean filtrarPorFormula = false;

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public String getNombreGenerico() {
        return nombreGenerico;
    }

    public void setNombreGenerico(String nombreGenerico) {
        this.nombreGenerico = nombreGenerico;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getForma() {
        return forma;
    }

    public void setForma(String forma) {
        this.forma = forma;
    }

    public Boolean useLike() {
        return useLike;
    }

    public void setUseLike(Boolean useLike) {
        this.useLike = useLike;
    }

    public void setFiltrarPorFormula(boolean filtrarPorFormula) {
        this.filtrarPorFormula = filtrarPorFormula;
    }

    public boolean filtrarPorFormula(){
        return this.filtrarPorFormula;
    }

    public Boolean isRemediar() {
        return esRemediar;
    }

    public void setEsRemediar(Boolean esRemediar) {
        this.esRemediar = esRemediar;
    }

    public boolean isEmprty(){
        return  this.laboratorio.isEmpty()
                && this.nombreComercial.isEmpty()
                && this.nombreGenerico.isEmpty()
                && this.forma.isEmpty()
                && !this.esRemediar;
    }
}
