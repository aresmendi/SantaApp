package entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "cartas")
public class Carta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCarta")
    private int id;

    @OneToOne
    @JoinColumn(name = "idInfanteAso", unique = true, nullable = false)
    private Infante infante;

    @ManyToOne(optional = true)
    @JoinColumn(name = "nombreAsistente")
    private Asistente asistente;

    @Column(name = "momentoEntrega",nullable = false)
    private LocalDateTime momentoEntrega;

    @Column(name = "ciudad", length = 100,nullable = false)
    private String ciudad;

    @Column(name = "direccion",length = 100,nullable = false)
    private String direccion;

    public Carta() {
    }

    public Carta(Infante infante, Asistente asistente, LocalDateTime momentoEntrega, String ciudad, String direccion) {
        this.infante = infante;
        this.asistente = asistente;
        this.momentoEntrega = momentoEntrega;
        this.ciudad = ciudad;
        this.direccion = direccion;
    }

    public Carta(int id, Infante infante, Asistente asistente, LocalDateTime momentoEntrega, String ciudad, String direccion) {
        this.id = id;
        this.infante = infante;
        this.asistente = asistente;
        this.momentoEntrega = momentoEntrega;
        this.ciudad = ciudad;
        this.direccion = direccion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Infante getInfante() {
        return infante;
    }

    public void setInfante(Infante infante) {
        this.infante = infante;
    }

    public Asistente getAsistente() {
        return asistente;
    }

    public void setAsistente(Asistente asistente) {
        this.asistente = asistente;
    }

    public LocalDateTime getMomentoEntrega() {
        return momentoEntrega;
    }

    public void setMomentoEntrega(LocalDateTime momentoEntrega) {
        this.momentoEntrega = momentoEntrega;
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
}
