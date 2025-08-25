
package com.roshka.market.app.model;

public class Cliente {
    private Integer id;
    private String nombre;
    private String apellido;
    private String nroCedula;
    private String telefono;
    
    public Cliente(){}
    
    public Cliente(Integer id, String nombre, String apellido, String nroCedula, String telefono){
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nroCedula = nroCedula;
        this.telefono = telefono;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    public String getNroCedula() {
        return nroCedula;
    }

    public void setNroCedula(String nroCedula) {
        this.nroCedula = nroCedula;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    
}
