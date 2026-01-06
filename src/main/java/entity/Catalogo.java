package entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "catalogo")
public class Catalogo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idReferencia")
    private Integer idReferencia;

    @Column(name = "edadMinima", nullable = false)
    private int edadMinima;

    @Column(name = "nombreRegalo", length = 100,nullable = false)
    private String nombreRegalo;

    @Column(name = "descripcion", length = 300, nullable = false)
    private String descripcion;

    public Catalogo() {
    }

    public Catalogo(int edadMinima, String nombreRegalo, String descripcion) {
        this.edadMinima = edadMinima;
        this.nombreRegalo = nombreRegalo;
        this.descripcion = descripcion;
    }

    public Catalogo(Integer idReferencia, int edadMinima, String nombreRegalo, String descripcion) {
        this.idReferencia = idReferencia;
        this.edadMinima = edadMinima;
        this.nombreRegalo = nombreRegalo;
        this.descripcion = descripcion;
    }

    public Integer getIdReferencia() {
        return idReferencia;
    }

    public void setIdReferencia(Integer idReferencia) {
        this.idReferencia = idReferencia;
    }

    public int getEdadMinima() {
        return edadMinima;
    }

    public void setEdadMinima(int edadMinima) {
        this.edadMinima = edadMinima;
    }

    public String getNombreRegalo() {
        return nombreRegalo;
    }

    public void setNombreRegalo(String nombreRegalo) {
        this.nombreRegalo = nombreRegalo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
