package service;

import entity.Catalogo;
import exceptions.ReglaNegocioExcepcion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import repository.CatalogoRepository;

import java.util.List;

public class CatalogoService {
    private EntityManagerFactory emf;

    public CatalogoService(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public Catalogo crearRegalo(int edadMinima, String nombreRegalo, String descripcion) {
        validarCamposComunes(edadMinima, nombreRegalo, descripcion);

        Catalogo catalogo = new Catalogo(edadMinima, nombreRegalo, descripcion);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            CatalogoRepository repo = new CatalogoRepository(em);
            repo.guardar(catalogo);
            tx.commit();
            return catalogo;
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new ReglaNegocioExcepcion("Error creando regalo en el catálogo" + ex.getMessage());
        } finally {
            em.close();
        }
    }

    public boolean actualizar(Integer id, int edadMinima, String nombreRegalo, String descripcion) {
        validarCamposComunes(edadMinima, nombreRegalo, descripcion);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Catalogo catalogo = null;
        try {
            tx.begin();
            CatalogoRepository repo = new CatalogoRepository(em);
            catalogo = repo.buscarPorId(id);
            if (catalogo == null) {
                throw new ReglaNegocioExcepcion("No existe el regalo con el id: " + id);
            }
            catalogo.setEdadMinima(edadMinima);
            catalogo.setNombreRegalo(nombreRegalo);
            catalogo.setDescripcion(descripcion);
            repo.actualizar(catalogo);
            tx.commit();
            return true;
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new ReglaNegocioExcepcion("Error actualizando regalo" + ex.getMessage());
        } finally {
            em.close();
        }
    }

    public boolean actualizarEdadMinima(Integer id, int edadMinima) {
        if (edadMinima <= 0 || edadMinima > 99) {
            throw new ReglaNegocioExcepcion("La edad mínima debe estar entre 1 e 99");
        }
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Catalogo catalogo = null;
        try {
            tx.begin();
            CatalogoRepository repo = new CatalogoRepository(em);
            catalogo = repo.buscarPorId(id);
            if (catalogo == null) {
                throw new ReglaNegocioExcepcion("No existe el regalo con el id: " + id);
            }
            catalogo.setEdadMinima(edadMinima);
            repo.actualizar(catalogo);
            tx.commit();
            return true;
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new ReglaNegocioExcepcion("Error actualizando regalo" + ex.getMessage());
        } finally {
            em.close();
        }
    }

    public boolean actualizarDescripcion(Integer id, String descripcion) {
        if (descripcion == null || descripcion.isEmpty()) {
            throw new ReglaNegocioExcepcion("Debe ingresar el descripcion del regalo");
        }
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Catalogo catalogo = null;
        try {
            tx.begin();
            CatalogoRepository repo = new CatalogoRepository(em);
            catalogo = repo.buscarPorId(id);
            if (catalogo == null) {
                throw new ReglaNegocioExcepcion("No existe el regalo con el id: " + id);
            }
            catalogo.setDescripcion(descripcion);
            repo.actualizar(catalogo);
            tx.commit();
            return true;
        } catch (Exception ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new ReglaNegocioExcepcion("Error actualizando regalo" + ex.getMessage());
        } finally {
            em.close();
        }
    }


    public Catalogo buscarPorId(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            CatalogoRepository repo = new CatalogoRepository(em);
            return repo.buscarPorId(id);
        } finally {
            em.close();
        }
    }

    public List<Catalogo> buscarTodosRegalos() {
        EntityManager em = emf.createEntityManager();
        try {
            CatalogoRepository repo = new CatalogoRepository(em);
            return repo.buscarTodosRegalosNoPedidos();
        } finally {
            em.close();
        }
    }

    public List<Catalogo> buscarTodosRegalosNoPedidos() {
        EntityManager em = emf.createEntityManager();
        try {
            CatalogoRepository repo = new CatalogoRepository(em);
            return repo.buscarTodosRegalosNoPedidos();
        } finally {
            em.close();
        }
    }

    public List<Catalogo> buscarPorTresLetras(String letras) {
        if (!letras.matches("[a-zA-Z]{3}")) {
            throw new ReglaNegocioExcepcion("Introduzca 3 letras sin caracteres especiales");
        }
        EntityManager em = emf.createEntityManager();
        try {

            CatalogoRepository repo = new CatalogoRepository(em);
            return repo.buscarPorTresLetras(letras);
        } finally {
            em.close();
        }
    }

    private void validarCamposComunes(int edadMinima, String nombreRegalo, String descripcion) {
        if (edadMinima <= 0 || edadMinima > 99) {
            throw new ReglaNegocioExcepcion("La edad mínima debe estar entre 1 e 99");
        }
        if (nombreRegalo == null || nombreRegalo.isEmpty()) {
            throw new ReglaNegocioExcepcion("Debe ingresar el nombre del regalo");
        }
        if (descripcion == null || descripcion.isEmpty()) {
            throw new ReglaNegocioExcepcion("Debe ingresar el descripcion del regalo");
        }
    }
}



