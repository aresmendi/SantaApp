package repository;

import entity.RegalosPorCarta;
import jakarta.persistence.EntityManager;

import java.util.List;

public class RegalosPorCartaRepository {
    private EntityManager em;

    public RegalosPorCartaRepository(EntityManager em) {
        this.em = em;
    }

    public void guardar(RegalosPorCarta regalosPorCarta) {
        em.persist(regalosPorCarta);
    }

    public void borrar(RegalosPorCarta regalosPorCarta) {
        if (!em.contains(regalosPorCarta)) {
            regalosPorCarta = em.merge(regalosPorCarta);
        }
        em.remove(regalosPorCarta);
    }

    public RegalosPorCarta buscarPorId(int id) {
        return em.find(RegalosPorCarta.class, id);
    }

    public List<RegalosPorCarta> buscarTodos() {
        return em.createQuery("SELECT DISTINCT r FROM RegalosPorCarta r JOIN fetch r.carta JOIN FETCH r.regalo", RegalosPorCarta.class).getResultList();

    }
}
