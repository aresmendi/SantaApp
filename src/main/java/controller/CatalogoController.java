package controller;

import entity.Catalogo;
import service.CatalogoService;
import view.ConsolaView;

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
        view.info(service.buscarTodosRegalosNoPedidos().toString());
    }


    public void regalosPorTresLetras() {
        String letras = view.pedirString("Introduzca 3 letras a buscar");
        service.buscarPorTresLetras(letras);
    }
}
