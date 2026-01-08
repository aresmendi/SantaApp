package service;

import entity.Asistente;
import exceptions.ReglaNegocioExcepcion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import repository.AsistenteRepository;

public class AsistenteService {
    private final EntityManagerFactory emf;

    public AsistenteService(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public Asistente crear(String nombre) {
        if (nombre != null && nombre.isBlank()) {
            throw new ReglaNegocioExcepcion("El nombre es obligatorio");
        }
        Asistente asistente = new Asistente(nombre);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            AsistenteRepository repo = new AsistenteRepository(em);
            Asistente existe = repo.buscar(nombre);
            if (existe != null) {
                throw new ReglaNegocioExcepcion("Ya existe un asistente con ese nombre");
            }
            repo.guardar(asistente);
            tx.commit();
            return asistente;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            };
            throw new ReglaNegocioExcepcion("Error creando asistente " + e.getMessage());
        }
        finally {
            em.close();
        }
    }
    public boolean borrar(String nombre) {
        if (nombre != null && nombre.isBlank()) {
            throw new ReglaNegocioExcepcion("El nombre es obligatorio");
        }
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Asistente asistente = null;
        try {
            tx.begin();
            AsistenteRepository repo = new AsistenteRepository(em);
            asistente = repo.buscar(nombre);
            if (!asistente.getNombreAsistente().equalsIgnoreCase(nombre)) {
                throw new ReglaNegocioExcepcion("El asistente no existe");
            }
            repo.borrar(asistente);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new ReglaNegocioExcepcion("Error borrando asistente " + e.getMessage());
        } finally {
            em.close();
        }
    }
}
