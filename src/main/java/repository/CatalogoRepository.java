package repository;

import entity.Catalogo;
import jakarta.persistence.EntityManager;

import java.util.List;

public class CatalogoRepository {
    private final EntityManager em;

    public CatalogoRepository(EntityManager em) {
        this.em = em;
    }

    public void guardar(Catalogo regalo) {
        em.persist(regalo);
    }

    public void eliminar(Catalogo regalo) {
        if (!em.contains(regalo)) {
            regalo = em.merge(regalo);
        }
        em.remove(regalo);
    }

    public Catalogo actualizar(Catalogo regalo) {
        return em.merge(regalo);
    }

    public Catalogo buscarPorId(int id) {
        return em.find(Catalogo.class, id);
    }

    public List<Catalogo> buscarTodosRegalosCatalogo() {
        return em.createQuery("SELECT DISTINCT r FROM Catalogo r", Catalogo.class).getResultList();
    }
}
