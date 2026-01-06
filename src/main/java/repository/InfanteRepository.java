package repository;

import entity.Infante;
import jakarta.persistence.EntityManager;

import java.util.List;

public class InfanteRepository {
    private final EntityManager em;

    public InfanteRepository(EntityManager em) {
        this.em = em;
    }

    public void guardar(Infante infante) {
        em.persist(infante);
    }

    public Infante obtenerInfantePorId(int id) {
        return em.find(Infante.class, id);
    }

    public List<Infante> obtenerTodosInfantes() {
        return em.createQuery("SELECT i FROM Infante i", Infante.class).getResultList();
    }

    public void borrar(Infante infante) {
        if (!em.contains(infante)) {
            infante = em.merge(infante);
        }
        em.remove(infante);
    }

    public Infante actualizar(Infante infante) {
        return em.merge(infante);
    }
}
