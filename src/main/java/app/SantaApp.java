package app;

import controller.AsistenteController;
import controller.CartaController;
import controller.CatalogoController;
import controller.MasterController;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import service.AsistenteService;
import service.CartaService;
import service.CatalogoService;
import service.InfanteService;
import view.ConsolaView;

public class SantaApp {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("SantaPU");
        ConsolaView view = new ConsolaView();
        CatalogoService catalogoService = new CatalogoService(emf);
        CatalogoController catalogoController = new CatalogoController(view, catalogoService);
        AsistenteService asistenteService = new AsistenteService(emf);
        AsistenteController asistenteController = new AsistenteController(view, asistenteService);
        InfanteService infanteService = new InfanteService(emf);
        CartaService cartaService = new CartaService(emf);
        CartaController cartaController = new CartaController(view, infanteService, catalogoService, cartaService);
        MasterController masterController = new MasterController(cartaController, catalogoController, asistenteController, view);

        masterController.run();

        emf.close();
    }
}
