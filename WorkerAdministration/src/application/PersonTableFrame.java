package application;

import application.ptv.BrowsePane;
import application.ptv.DataBox;
import application.ptv.FileManagerBox;
import application.ptv.InteractionBox;
import application.ptv.PersonSearchBox;
import application.ptv.PersonTableView;
import application.ptv.TableViewConfig;
import application.utils.LoadedPathManager;
import javafx.scene.layout.BorderPane;

public class PersonTableFrame extends BorderPane {
	
	private FrameManager fm;
	
	private TableViewConfig cfg;
	private LoadedPathManager lpm;
	
	private PersonTableView ptv;	
	private BrowsePane bp;
	private FileManagerBox fmb;
	private PersonSearchBox psb;
	private InteractionBox ib;
	private DataBox db;
	
	public PersonTableFrame(FrameManager fm) {
		this.fm = fm;
		
		this.cfg = new TableViewConfig("C:\\Users\\ctrap\\Desktop\\test", "config.txt");
		this.cfg.load();
		this.lpm = new LoadedPathManager(this.cfg);
		this.lpm.loadPathsOutOfCfg();
		
		this.ptv = new PersonTableView(this);
		this.bp = new BrowsePane(this, this.ptv);
		this.fmb = new FileManagerBox(this.ptv);
		this.psb = new PersonSearchBox(this.ptv);
		this.ib = new InteractionBox(this.ptv);
		this.db = new DataBox(this.ptv);
		
		this.buildFrame();
	}

	private void buildFrame() {
		this.setCenter(this.ptv);
		this.setTop(this.psb);
		this.setRight(this.bp);
		this.setBottom(this.db);
	}
	
	public LoadedPathManager getPathManager() {
		return lpm;
	}

	public FrameManager getFrameManager() {
		return fm;
	}
	
	public PersonTableView getPtv() {
		return ptv;
	}

	public BrowsePane getBrowsePane() {
		return bp;
	}

	public FileManagerBox getFileManagerBox() {
		return fmb;
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
	
	public TableViewConfig getConfig() {
		return cfg;
	}
}
