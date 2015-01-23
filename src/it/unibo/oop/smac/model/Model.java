package it.unibo.oop.smac.model;

import it.unibo.oop.smac.database.Connection;
import it.unibo.oop.smac.database.SightingRow;
import it.unibo.oop.smac.database.StreetObserverRow;
import it.unibo.oop.smac.datatype.InfoStreetObserver;
import it.unibo.oop.smac.datatype.I.IInfoStolenCar;
import it.unibo.oop.smac.datatype.I.IInfoStreetObserver;
import it.unibo.oop.smac.datatype.I.ISighting;
import it.unibo.oop.smac.datatype.I.IStolenCar;
import it.unibo.oop.smac.datatype.I.IStreetObserver;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;

/**
 * Implementazione del Model dell'applicazione. Questa classe riceve le richieste di lettura
 * e scrittura di informazioni su degli oggetti.
 * Questa classe è realizzata utilizzando il pattern Singleton.
 * 
 * @author Federico Bellini
 *
 */
public class Model implements IModel {

	private static Model instance;

	private Model() {
	}

	public static synchronized Model getInstance() {
		if(instance == null){
			instance = new Model();
		}
		return instance;
	}
	
	/**
	 * Inserisce nel database un nuovo {@link IStreetObserver}.
	 * 
	 * @param streetObserver
	 * 			L'{@link IStreetObserver} da inserire.
	 * 
	 */
	@Override
	public void addNewStreetObserver(IStreetObserver streetObserver) {
		StreetObserverRow streetObserverRow = new StreetObserverRow(streetObserver);
		Dao<StreetObserverRow, String> streetObserverDao = this.getStreetObserverDao();
		try {
			streetObserverDao.createIfNotExists(streetObserverRow);
			System.out.println("Reading of data just added:  " + 
					streetObserverDao.queryForId(streetObserver.getID()));
		} catch(SQLException e) {
			// caso in cui la creazione non è avvenuta correttamente
			e.printStackTrace();
		}
	}

	/**
	 * Inserisce nel database un nuovo {@link ISighting}.
	 * 
	 * @param sighting
	 * 			L'{@link ISighting} da inserire.
	 * 
	 */
	@Override
	public void addSighting(ISighting sighting) {
		StreetObserverRow streetObserverRow = getStreetObserverRow(sighting.getStreetObserver());
		SightingRow sightingRow = new SightingRow(sighting, streetObserverRow);
		
		streetObserverRow.addSightings(sightingRow);
	}

	// raccoglie dati di uno streetObserver, e restituisce un pacchetto
	// InfoStreetObserver
	@Override
	public IInfoStreetObserver getStreetObserverInfo(IStreetObserver streetObserver) {

		StreetObserverRow streetObserverRow = this.getStreetObserverRow(streetObserver);
		List<SightingRow> sightingList = streetObserverRow.getSightings();
		
		return new InfoStreetObserver.Builder()
						.streetObserver(streetObserverRow)
						.totalNOfSight(sightingList.size()).build();
	}
	
	@Override
	public IInfoStolenCar getStolenCarInfo(IStolenCar stolenCar) {
		// TODO
		return null;
	}
	
	private StreetObserverRow getStreetObserverRow(IStreetObserver streetObserver) 
			throws IllegalArgumentException {
		Dao<StreetObserverRow, String> streetObserverDao = this.getStreetObserverDao();
		StreetObserverRow row = null;
		try {
			row = streetObserverDao.queryForId(streetObserver.getID());
		} catch (SQLException e) {
			throw new IllegalArgumentException("Problems occured in the database");
		}
		return row;
	}
	
	private Dao<StreetObserverRow, String> getStreetObserverDao() {
		return Connection.getInstance().getStreetObserverDao();
	}

}