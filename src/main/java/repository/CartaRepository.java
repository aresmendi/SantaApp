package repository;

import entity.Carta;
import jakarta.persistence.EntityManager;

import java.util.List;

public class CartaRepository {
    private EntityManager em;

    public CartaRepository(EntityManager em) {
        this.em = em;
    }

    public void guardar(Carta carta) {
        em.persist(carta);
    }

    public void eliminar(Carta carta) {
        em.remove(carta);
    }

    public Carta buscarPorId(Long id) {
        return em.find(Carta.class, id);
    }

    public List<Carta> buscarTodos() {
        return em.createQuery("SELECT DISTINCT c FROM Carta c LEFT JOIN FETCH c.infante LEFT JOIN FETCH c.asistente", Carta.class).getResultList();
    }
}
