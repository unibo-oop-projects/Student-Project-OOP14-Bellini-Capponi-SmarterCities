package it.unibo.oop.smac.controller;

import it.unibo.oop.smac.network.NetServer;
import it.unibo.oop.smac.simulator.client.SightingSenderClient;
import it.unibo.oop.smac.view.ViewGUI;

/**
 * Esempio di avvio dell'applicazione.
 * 
 * @author Federico Bellini
 * @author Francesco Capponi
 */
public final class SmarterCitiesApplication {

  private SmarterCitiesApplication() {

  }

  /**
   * Main dell'applicazione. Starta i servizi e l'interfaccia grafica
   * 
   * @param args
   * @throws InterruptedException
   */
  public static void main(String[] args) throws InterruptedException {

    final IController controller = new StolenCarsController(new ViewGUI());
    new NetServer(controller);

    // creazione dei client
    Thread.sleep(1000);
    for (int i = 0; i < 2; i++) {
      Thread.sleep(2000);
      new Thread(new SightingSenderClient()).start();
    }
  }

}
