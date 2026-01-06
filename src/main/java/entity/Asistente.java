package entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "asistentes")
public class Asistente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "nombreAsistente")
    private String nombreAsistente;

    public Asistente() {
    }

    public Asistente(String nombreAsistente) {
        this.nombreAsistente = nombreAsistente;
    }

    public String getNombreAsistente() {
        return nombreAsistente;
    }

    public void setNombreAsistente(String nombreAsistente) {
        this.nombreAsistente = nombreAsistente;
    }
}
