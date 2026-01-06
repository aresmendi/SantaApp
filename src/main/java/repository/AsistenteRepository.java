package repository;

import entity.Asistente;
import jakarta.persistence.EntityManager;

import java.util.List;

public class AsistenteRepository {
    private EntityManager em;

    public AsistenteRepository(EntityManager em) {
        this.em = em;
    }

    public void guardar(Asistente asistente) {
        em.persist(asistente);
    }

    public void borrar(Asistente asistente) {
        if (!em.contains(asistente)) {
            asistente = em.merge(asistente);
        }
        em.remove(asistente);
    }

    public Asistente buscar(String id) {
        id = id.trim();
        return em.find(Asistente.class, id);
    }

    public List<Asistente> buscarTodos() {
        return em.createQuery("SELECT DISTINCT a FROM Asistente a", Asistente.class).getResultList();
    }

}
