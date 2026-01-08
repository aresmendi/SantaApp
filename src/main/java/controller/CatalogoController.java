package controller;

import entity.Catalogo;
import service.CatalogoService;
import view.ConsolaView;

import java.util.List;

public class CatalogoController {
    private final ConsolaView view;
    private final CatalogoService service;

    public CatalogoController(ConsolaView view, CatalogoService service) {
        this.view = view;
        this.service = service;
    }

    public void crear() {
        int edadMinima = view.pedirInt("Introduzca edad mínima del regalo");
        String nombreRegalo = view.pedirString("Introduzca nombre del regalo");
        String descripcion = view.pedirString("Introduzca el descripcion del regalo");

        service.crearRegalo(edadMinima, nombreRegalo, descripcion);
    }

    public void actualizar() {
        view.mostrarCatalogo(service.buscarTodosRegalos());
        int id = view.pedirInt("Introduzca el id del regalo a modificar");
        Catalogo regalo = service.buscarPorId(id);
        if (regalo != null) {
            String op1 = view.pedirString("¿Desea actualizar edad minima del regalo? (Pulse S para actualizar)");
            String op2 = view.pedirString("¿Desea actualizar la descripción del regalo? (Pulse S para actualizar)");
            if (op1.equalsIgnoreCase("S")) {
                int edadMinima = view.pedirInt("Introduzca edad minima del regalo");
                service.actualizarEdadMinima(id, edadMinima);
                view.info("Edad minima del regalo actualizada");
            }
            if (op2.equalsIgnoreCase("S")) {
                String descripcion = view.pedirString("Introduzca la descripción del regalo");
                service.actualizarDescripcion(id, descripcion);
                view.info("Descripción del regalo actualizada");
            }
        } else {
            view.error("El regalo no existe");
        }
    }

    public void regalosNoPedidos() {
        List<Catalogo> regalos = service.buscarTodosRegalosNoPedidos();

        if (regalos.isEmpty()) {
            view.info("No se encontró ningún regalo no pedido");
            return;
        }
        view.info("Regalos del catálogo que no ha pedido ningún niño:");

        for (Catalogo regalo : regalos) {
            view.info(regalo.toString());
        }
    }


    public void regalosPorTresLetras() {
        String letras = view.pedirString("Introduzca 3 letras a buscar");
        List<Catalogo> regalos = service.buscarPorTresLetras(letras);
        if (regalos.isEmpty()) {
            view.info("No se encontró ninguna coincidencia");
            return;
        }
        view.info("Regalos con " + letras + ": ");
        for (Catalogo regalo : regalos) {
            view.info(regalo.toString());
        }
    }
}
