package repository;

import entity.Asistente;
import jakarta.persistence.EntityManager;

public class AsistenteRepository {
    private EntityManager em;

    public AsistenteRepository(EntityManager em) {
        this.em = em;
    }

    public void guardar(Asistente asistente) {
        em.persist(asistente);
    }
    public void borrar(Asistente asistente) {

    }
}
