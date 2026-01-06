package entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "regalosPorCarta")
public class RegalosPorCarta implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRegaloCarta")
    private Integer idRegaloCarta;

    @ManyToOne
    @JoinColumn(name = "idCartaAso")
    private Carta carta;

    @ManyToOne
    @JoinColumn(name = "idRefAso")
    private Catalogo regalo;

    @Column(name = "cantidad")
    private int cantidad;

    public RegalosPorCarta() {
    }

    public RegalosPorCarta(Carta carta, Catalogo regalo, int cantidad) {
        this.carta = carta;
        this.regalo = regalo;
        this.cantidad = cantidad;
    }

    public RegalosPorCarta(Integer idRegaloCarta, Carta carta, Catalogo regalo, int cantidad) {
        this.idRegaloCarta = idRegaloCarta;
        this.carta = carta;
        this.regalo = regalo;
        this.cantidad = cantidad;
    }

    public Integer getIdRegaloCarta() {
        return idRegaloCarta;
    }

    public void setIdRegaloCarta(Integer idRegaloCarta) {
        this.idRegaloCarta = idRegaloCarta;
    }

    public Carta getCarta() {
        return carta;
    }

    public void setCarta(Carta carta) {
        this.carta = carta;
    }

    public Catalogo getRegalo() {
        return regalo;
    }

    public void setRegalo(Catalogo regalo) {
        this.regalo = regalo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
