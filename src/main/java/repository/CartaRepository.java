package repository;

import entity.Carta;
import jakarta.persistence.EntityManager;

import java.time.LocalDateTime;
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

    public Carta buscarPorId(int id) {
        return em.find(Carta.class, id);
    }

    public List<Carta> buscarTodos() {
        return em.createQuery("SELECT DISTINCT c FROM Carta c LEFT JOIN FETCH c.infante LEFT JOIN FETCH c.asistente LEFT JOIN FETCH c.regalos r LEFT JOIN FETCH r.regalo", Carta.class).getResultList();
    }

    public List<Carta> buscarPorCiudad(String ciudad) {
        return em.createQuery("SELECT DISTINCT r FROM Carta c LEFT JOIN FETCH c.regalos r LEFT JOIN FETCH r.regalo WHERE c.ciudad = :ciudad", Carta.class).setParameter("ciudad", ciudad).getResultList();
    }

    public List<Carta> buscarPorMomento(LocalDateTime momento) {
        return em.createQuery("SELECT DISTINCT c FROM Carta c LEFT JOIN FETCH c.infante LEFT JOIN FETCH c.asistente LEFT JOIN FETCH c.regalos r LEFT JOIN FETCH r.regalo WHERE c.momentoEntrega = :momento", Carta.class).setParameter("momento", momento).getResultList();
    }

    public Carta buscarPorIdInfante(Integer idInfante) {
        List<Carta> lista = em.createQuery("SELECT DISTINCT c FROM Carta c LEFT JOIN FETCH c.infante LEFT JOIN FETCH  c.asistente WHERE c.infante.id = :idInfante", Carta.class).setParameter("idInfante", idInfante).getResultList();
        if (lista.isEmpty()) {
            return null;
        } else {
            return lista.get(0);
        }
    }

    public boolean existeCarta(Integer idInfante) {
        Carta carta = buscarPorIdInfante(idInfante);
        return carta != null;

    }

}
