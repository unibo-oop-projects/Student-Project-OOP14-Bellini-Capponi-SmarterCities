package it.unibo.oop.smac.model;

import it.unibo.oop.smac.datatype.I.IInfoStolenCar;
import it.unibo.oop.smac.datatype.I.IInfoStreetObserver;
import it.unibo.oop.smac.datatype.I.ISighting;
import it.unibo.oop.smac.datatype.I.IStolenCar;
import it.unibo.oop.smac.datatype.I.IStreetObserver;

public interface IModel {
	
	// questo metodo deve aggiungere un nuovo streetObserver al DATABASE
	void addNewStreetObserver(IStreetObserver streetObserver);
	
	// questo metodo deve aggiungere il pacchetto di informazioni "s" al database.
	void addSighting(ISighting sighting);
	
	// questo metodo deve far tornare un pacchetto IInfoStreetObserver dello streetObserver richiesto
	IInfoStreetObserver getStreetObserverInfo(IStreetObserver streetObserver);
	
	// questo metodo deve far tornare un pacchetto IInfoStolenCarr della stolenCar richiesta
	IInfoStolenCar getStolenCarInfo(IStolenCar stolenCar);
}