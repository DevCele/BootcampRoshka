package com.roshka.market.app.model;

public class Proveedor {
    private Integer id;
    private String nombre;
    private String ruc;
    private Integer paisId;

    public Proveedor() {}
    public Proveedor(Integer id, String nombre, String ruc, Integer paisId) {
        this.id = id; this.nombre = nombre; this.ruc = ruc; this.paisId = paisId;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getRuc() { return ruc; }
    public void setRuc(String ruc) { this.ruc = ruc; }
    public Integer getPaisId() { return paisId; }
    public void setPaisId(Integer paisId) { this.paisId = paisId; }
}
