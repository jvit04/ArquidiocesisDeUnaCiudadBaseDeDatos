package application;

import java.time.LocalDate;

public class Parroquia {
    public String nombre;
    public String vicaria;
    public String ciudad;
    public String direccion;
    public String parroco;
    public String telefono;
    public String sitioWeb;
    public String email;
    public LocalDate fechaFundacion;

    public Parroquia(String nombre, String vicaria, String ciudad, String direccion,
                     String parroco, String telefono, String sitioWeb, String email,
                     LocalDate fechaFundacion) {
        this.nombre = nombre;
        this.vicaria = vicaria;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.parroco = parroco;
        this.telefono = telefono;
        this.sitioWeb = sitioWeb;
        this.email = email;
        this.fechaFundacion = fechaFundacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getVicaria() {
        return vicaria;
    }

    public void setVicaria(String vicaria) {
        this.vicaria = vicaria;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getParroco() {
        return parroco;
    }

    public void setParroco(String parroco) {
        this.parroco = parroco;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getSitioWeb() {
        return sitioWeb;
    }

    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getFechaFundacion() {
        return fechaFundacion;
    }

    public void setFechaFundacion(LocalDate fechaFundacion) {
        this.fechaFundacion = fechaFundacion;
    }
}
