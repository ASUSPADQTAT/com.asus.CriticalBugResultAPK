package com.asus.CriticalBugResultAPK;

public class CriticalBug {

	public int ID;
	public String Domain;
	public String TCID;
	public String TCName;
	public String AutoType;
	public String ScreenShotURI;
	public String Result;

	public CriticalBug() {

		Domain = TCID = TCName = AutoType = ScreenShotURI = Result = "";
	}

	public CriticalBug(int _ID, String _Domain, String _TCID, String _TCName, String _AutoType, String _ScreenShotURI, String _Result) {
		this.ID = _ID;
		this.Domain = _Domain;
		this.TCID = _TCID;
		this.TCName = _TCName;
		this.AutoType = _AutoType;
		this.ScreenShotURI = _ScreenShotURI;
		this.Result = _Result;
	}
	
	public CriticalBug( String _Domain, String _TCID, String _TCName, String _AutoType, String _ScreenShotURI, String _Result) {		
		this.Domain = _Domain;
		this.TCID = _TCID;
		this.TCName = _TCName;
		this.AutoType = _AutoType;
		this.ScreenShotURI = _ScreenShotURI;
		this.Result = _Result;
	}
}
