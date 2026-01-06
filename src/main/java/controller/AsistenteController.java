package controller;

import service.AsistenteService;
import view.ConsolaView;

public class AsistenteController {
    private final ConsolaView view;
    private final AsistenteService service;

    public AsistenteController(ConsolaView view, AsistenteService service) {
        this.view = view;
        this.service = service;
    }

    public void crear() {
        String nombre = view.pedirString("Introduzca el nombre del asistente");
        service.crear(nombre);
        view.info("Asistente creado");
    }

    public void eliminar() {
        String nombre = view.pedirString("Introduzca el nombre del asistente a eliminar");
        service.borrar(nombre);
        view.info("Asistente eliminado");
    }
}
