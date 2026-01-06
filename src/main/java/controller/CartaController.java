package controller;

import entity.Catalogo;
import service.CartaService;
import service.CatalogoService;
import service.InfanteService;
import view.ConsolaView;

import java.time.LocalDateTime;
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
        infanteService.crear(nombreInfante, apellidoInfante);
        String asistente = view.pedirString("Asistente");
        String momentoString = view.pedirString("Momento de entrega");
        LocalDateTime momento = LocalDateTime.parse(momentoString);
        String ciudad = view.pedirString("Ciudad");
        String direccion = view.pedirString("Direccion");
        List<Catalogo> regalos = new ArrayList<>();
        List<Integer> cantidades = new ArrayList<>();
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
                    cantidad = view.pedirInt("Introduzca")
                    regalos.add(regalo);
                }

            }
        }


    }

    void infantesPorMomentos() {
    }
}
