package service;

import entity.Infante;
import exceptions.ReglaNegocioExcepcion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import repository.InfanteRepository;

public class InfanteService {
    private final EntityManagerFactory emf;

    public InfanteService(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public Infante crear(String nombre, String apellidos) {
        validarCamposComunes(nombre, apellidos);
        Infante infante = new Infante(nombre, apellidos);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            InfanteRepository repo = new InfanteRepository(em);
            repo.guardar(infante);
            tx.commit();
            return infante;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new ReglaNegocioExcepcion("Error creando infante " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public boolean borrar(Integer id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Infante infante = null;
        try {
            tx.begin();
            InfanteRepository repo = new InfanteRepository(em);
            infante = repo.obtenerInfantePorId(id);
            if (infante == null) {
                throw new ReglaNegocioExcepcion("El infante no existe");
            }
            repo.borrar(infante);
            tx.commit();
            return true;
        }catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new ReglaNegocioExcepcion("Error eliminando infante " + e.getMessage());
        } finally {
            em.close();
        }
    }

    private void validarCamposComunes(String nombre, String apellidos) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre del infante es obligatorio");
        }
        if (apellidos == null || apellidos.isEmpty()) {
            throw new IllegalArgumentException("El apellido del infante es obligatorio");
        }
    }
}
