package entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "infantes")
public class Infante implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idInfante")
    private Integer idInfante;

    @Column(nullable = false,length = 50)
    private String nombre;
    @Column(nullable = false,length = 100)
    private String apellidos;

    public Infante() {
    }


    public Infante(String nombre, String apellidos) {
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public Infante(Integer idInfante, String nombre, String apellidos) {
        this.idInfante = idInfante;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public Integer getIdInfante() {
        return idInfante;
    }

    public void setIdInfante(Integer idInfante) {
        this.idInfante = idInfante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    @Override
    public String toString() {
        return "Infante con el id " + idInfante+
                ", se llama " + nombre + ' ' + apellidos;
    }
}
