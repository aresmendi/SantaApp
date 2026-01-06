package service;

import entity.*;
import exceptions.ReglaNegocioExcepcion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import repository.AsistenteRepository;
import repository.CartaRepository;
import repository.InfanteRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CartaService {
    private final EntityManagerFactory emf;

    public CartaService(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public Carta crear(Integer idInfante, String nombreAsistente, LocalDateTime momentoEntrega, String ciudad, String direccion, List<Catalogo> regalos, ArrayList<Integer> cantidades) {
        validarCamposComunes(idInfante, nombreAsistente, momentoEntrega, ciudad, direccion, regalos, cantidades);

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            CartaRepository repo = new CartaRepository(em);
            InfanteRepository infrepo = new InfanteRepository(em);
            AsistenteRepository asistrepo = new AsistenteRepository(em);

            //Me traigo las entidades para que estén managed y para que sean los objetos que pide el constructor
            Infante infante = infrepo.obtenerInfantePorId(idInfante);
            Asistente asistente = asistrepo.buscar(nombreAsistente);

            //Rellenamos la carticaaa
            Carta carta = new Carta();

            carta.setInfante(infante);
            carta.setAsistente(asistente);
            carta.setMomentoEntrega(momentoEntrega);
            carta.setCiudad(ciudad);
            carta.setDireccion(direccion);


            //Transformamos los regalos del catálogo en regalosPorCarta y los agregamos a la carta
            for (Catalogo regalo : regalos) {
                int posicion = regalos.indexOf(regalo);
                int cantidad = cantidades.get(posicion);
                RegalosPorCarta rcp = agregarRegalos(carta, regalo, cantidad);
            }

            repo.guardar(carta);
            tx.commit();
            return carta;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new ReglaNegocioExcepcion("Error al crear la carta " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public List<Carta> cartasPorCiudad(String ciudad) {
        if (ciudad == null||ciudad.isBlank()) {
            throw new ReglaNegocioExcepcion("La ciudad no puede ser nula ni estar en blanco");
        }
        EntityManager em = emf.createEntityManager();
        try{
            CartaRepository repo = new CartaRepository(em);
            return repo.buscarPorCiudad(ciudad);
        } finally {
            em.close();
        }
    }

    public List<Carta> cartasPorMomentos(LocalDateTime momentoEntrega) {
        if (momentoEntrega == null) {
            throw new ReglaNegocioExcepcion("El momento no puede ser nulo");
        }
        EntityManager em = emf.createEntityManager();
        try{
            CartaRepository repo = new CartaRepository(em);
            return repo.buscarPorMomento(momentoEntrega);
        } finally {
            em.close();
        }
    }

    private void validarCamposComunes(Integer idInfante, String nombreAsistente, LocalDateTime momentoEntrega, String ciudad, String direccion, List<Catalogo> regalos, List<Integer> cantidades) {
        EntityManager em = emf.createEntityManager();
        CartaRepository repo = new CartaRepository(em);
        AsistenteRepository as = new AsistenteRepository(em);

        LocalDateTime inicio = LocalDateTime.of(2025, 12, 24, 23, 0);
        LocalDateTime fin = LocalDateTime.of(2025, 12, 25, 23, 59);

        if (repo.existeCarta(idInfante)) {
            throw new ReglaNegocioExcepcion("El infante ya ha escrito una carta");
        }
        if (as.buscar(nombreAsistente) == null) {
            throw new ReglaNegocioExcepcion("El asistente no existe");
        }
        if (momentoEntrega == null || momentoEntrega.isBefore(inicio) || momentoEntrega.isAfter(fin)) {
            throw new ReglaNegocioExcepcion("La entrega no puede ser nulla y debe ser entre el dia 24 de Diciembre de 2025 a las 23:00 y el 25 de Diciembre a las 23:59");
        }
        if (ciudad == null || ciudad.isBlank()) {
            throw new ReglaNegocioExcepcion("La ciudad no puede ser nula");
        }
        if (direccion == null || direccion.isBlank()) {
            throw new ReglaNegocioExcepcion("La dirección no puede ser nula");
        }
        if (regalos == null || regalos.isEmpty()) {
            throw new ReglaNegocioExcepcion("No puede haber una carta sin regalos");
        }
        if(cantidades.size() != regalos.size()) {
            throw new ReglaNegocioExcepcion("No puede haber regalos sin cantidades ni viceversa");
        }
    }

    private RegalosPorCarta agregarRegalos(Carta carta, Catalogo regalo, int cantidad) {
        RegalosPorCarta rcp = new RegalosPorCarta(carta, regalo, cantidad);
        carta.agregarRegalosPorCarta(rcp);
        return rcp;
    }
}





