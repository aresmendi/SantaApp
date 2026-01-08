package controller;

import view.ConsolaView;

public class MasterController {
    private final CartaController cartaController;
    private final CatalogoController catalogoController;
    private final AsistenteController asistenteController;
    private final ConsolaView view;

    public MasterController(CartaController cartaController, CatalogoController catalogoController, AsistenteController asistenteController, ConsolaView view) {
        this.cartaController = cartaController;
        this.catalogoController = catalogoController;
        this.asistenteController = asistenteController;
        this.view = view;
    }

    public void run() {
        int op;
        do {
            op = view.menu();
            try {
                switch (op) {
                    case 1 -> cartaController.crear();
                    case 2 -> catalogoController.crear();
                    case 3 -> catalogoController.actualizar();
                    case 4 -> asistenteController.crear();
                    case 5 -> asistenteController.eliminar();
                    case 6 -> catalogoController.regalosNoPedidos();
                    case 7 -> cartaController.regalosPorCiudad();
                    case 8 -> cartaController.infantesPorMomentos();
                    case 9 -> catalogoController.regalosPorTresLetras();
                    case 12 -> view.info("!Ho ho ho, ahora me voy yo!");
                }
            } catch (NumberFormatException e) {
                view.error("Introduzca un número válido");
            }
        } while (op != 12);
    }
}
