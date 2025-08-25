package com.roshka.market.app.model;

public class Producto {
    private Integer id;
    private String nombre;
    private Double precio;
    private Integer proveedorId;
    private Double costo;

    public Producto(Integer id, String nombre, Double precio, Integer proveedorId, Double costo) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.proveedorId = proveedorId;
        this.costo = costo;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public Integer getProveedorId() { return proveedorId; }
    public void setProveedorId(Integer proveedorId) { this.proveedorId = proveedorId; }

    public Double getCosto() { return costo; }
    public void setCosto(Double costo) { this.costo = costo; }
}
