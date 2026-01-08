package controller;

import entity.Carta;
import entity.Catalogo;
import entity.Infante;
import entity.RegalosPorCarta;
import exceptions.ReglaNegocioExcepcion;
import service.CartaService;
import service.CatalogoService;
import service.InfanteService;
import view.ConsolaView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class CartaController {
    private final ConsolaView view;
    private final InfanteService infanteService;
    private final CatalogoService catalogoService;
    private final CartaService service;

    public CartaController(ConsolaView view, InfanteService infanteService, CatalogoService catalogoService, CartaService service) {
        this.view = view;
        this.infanteService = infanteService;
        this.catalogoService = catalogoService;
        this.service = service;
    }

    void crear() {
        String nombreInfante = view.pedirString("Nombre de infante");
        String apellidoInfante = view.pedirString("Apellido de infante");
        Infante infante = infanteService.crear(nombreInfante, apellidoInfante);
        String asistente = view.pedirString("Asistente");
        String momentoString = view.pedirString("Momento de entrega (formato dd/MM/yyyy HH:mm:ss)");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime momento;
        try {
            momento = LocalDateTime.parse(momentoString, formatter);
        } catch (DateTimeParseException e) {
            view.error("Formato de tiempo no valido, intente de nuevo con dd/MM/yyyy HH:mm:ss");
            return;
        }
        String ciudad = view.pedirString("Ciudad");
        String direccion = view.pedirString("Direccion");
        List<Catalogo> regalos = new ArrayList<>();
        ArrayList<Integer> cantidades = new ArrayList<>();
        while (true) {
            view.mostrarCatalogo(catalogoService.buscarTodosRegalos());
            int idRegalo = view.pedirInt("Id de regalo, seleccione de los de arriba (Introduzca 0 para salir)");
            if (idRegalo == 0) {
                if (regalos.isEmpty()) {
                    view.info("Debe regalos para que la carta sea válida");
                    continue;
                } else {
                    view.info("Los regalos se han añadido correctamente");
                    break;
                }
            }
            Catalogo regalo = catalogoService.buscarPorId(idRegalo);
            if (regalo == null) {
                view.info("El regalo no existe, pruebe de nuevo");
                continue;
            } else {
                boolean yaIncluido = false;
                for (Catalogo r : regalos) {
                    if (r.getIdReferencia().equals(regalo.getIdReferencia())) {
                        yaIncluido = true;
                        break;
                    }
                }
                if (yaIncluido) {
                    view.error("El regalo ya se ha pedido, pruebe otro");
                    continue;
                } else {
                    int cantidad;
                    while (true) {
                        cantidad = view.pedirInt("Introduzca la cantidad de " + regalo.getNombreRegalo() + " que desea");
                        if (cantidad <= 0) {
                            view.info("El cantidad debe ser mayor que 0");
                            continue;
                        } else {
                            break;
                        }
                    }
                    cantidades.add(cantidad);
                    regalos.add(regalo);
                }
            }
        }
        service.crear(infante.getIdInfante(), asistente, momento, ciudad, direccion, regalos, cantidades);
        view.info("La carta de " + infante.getNombre() + " se ha añadido correctamente");
    }

    void infantesPorMomentos() {
        String momentoString = view.pedirString("Momento de entrega (Formato: dd/MM/yyyy HH:mm:ss)");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime momento;
        try {
            momento = LocalDateTime.parse(momentoString, formatter);
            List<Carta> cartas = service.cartasPorMomentos(momento);
            if (cartas.isEmpty()) {
                view.info("No hay cartas para ese momento.");
                return;
            }
            for (Carta c : cartas) {
                view.info("------------------------------------");
                view.info("Carta de " + c.getInfante().getNombre() + " desde la ciudad de " + c.getCiudad() + "\n" +
                        "Momento de entrega: " + c.getMomentoEntrega());
                for (RegalosPorCarta rpc : c.getRegalos()){
                    Catalogo regalo = rpc.getRegalo();
                    view.info("- " + regalo.getNombreRegalo()
                            + " | Cantidad: " + rpc.getCantidad());
                }
                view.info("------------------------------------");
            }
        } catch (DateTimeParseException e) {
            view.error("Formato de tiempo no valido, intente de nuevo con dd/MM/yyyy HH:mm:ss");
            return;
        }
    }

    void regalosPorCiudad() {
        String ciudad = view.pedirString("Ciudad a buscar");
        List<Carta> cartas = service.cartasPorCiudad(ciudad);
        if (cartas.isEmpty()) {
            view.info("No hay cartas para esta ciudad.");
            return;
        }
        for (Carta c : cartas) {
            view.info("------------------------------------");
            view.info("Carta de " + c.getInfante().getNombre() + " desde la ciudad de " + c.getCiudad() + "\n");
            for (RegalosPorCarta rpc : c.getRegalos()){
                Catalogo regalo = rpc.getRegalo();
                view.info("- " + regalo.getNombreRegalo()
                + " | Cantidad: " + rpc.getCantidad());
            }
            view.info("------------------------------------");
        }
    }
}
