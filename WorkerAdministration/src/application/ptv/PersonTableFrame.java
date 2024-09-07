package application.ptv;

import application.FrameManager;
import application.utils.worker.WorkerManager;
import application.utils.worker.WorkerSearchAlgo;
import javafx.scene.layout.BorderPane;

public class PersonTableFrame extends BorderPane {
	
	private FrameManager fm;
	
	private PersonTableView ptv;	
	private PersonSearchBox psb;
	private InteractionBox ib;
	private DataBox db;
	private WorkerDataBox wdb;
	
	public PersonTableFrame(FrameManager fm) {
		this.fm = fm;		
		
		this.ptv = new PersonTableView(this);
		this.psb = new PersonSearchBox(this);
		this.ib = new InteractionBox(this);
		this.db = new DataBox(this);
		this.wdb = new WorkerDataBox(this);
		
		this.buildFrame();
	}

	private void buildFrame() {
		this.setCenter(this.ptv);
		this.setTop(this.psb);
		this.setRight(this.ib);
		this.setBottom(this.db);
	}
	
	public FrameManager getFrameManager() {
		return fm;
	}
	
	public PersonTableView getPtv() {
		return ptv;
	}

	public PersonSearchBox getPersonSearchBox() {
		return psb;
	}

	public InteractionBox getInteractionBox() {
		return ib;
	}

	public DataBox getDataBox() {
		return db;
	}
	
	public WorkerDataBox getWDB() {
		return wdb;
	}
	
	public WorkerManager getWorkerManager() {
		return this.fm.getWorkerManager();
	}
}
