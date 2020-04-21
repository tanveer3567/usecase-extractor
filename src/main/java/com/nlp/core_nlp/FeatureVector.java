package com.nlp.core_nlp;

public class FeatureVector {

	private String subject;
	private String sTag;
	private String sNer;
	private int sType;
	private String verb;
	private String vTag;
	private String vCat;
	private String vProcess;
	private String object;
	private String oTag;
	private String oNer;
	private int oType;
	private int label;
	
	public FeatureVector(String subject, String sTag, String sNer, int sType, String verb, String vTag, String vCat,
			String vProcess, String object, String oTag, String oNer, int oType) {
		
		this.subject = subject;
		this.sTag = sTag;
		this.sNer = sNer;
		this.sType = sType;
		this.verb = verb;
		this.vTag = vTag;
		this.vCat = vCat;
		this.vProcess = vProcess;
		this.object = object;
		this.oTag = oTag;
		this.oNer = oNer;
		this.oType = oType;
	}
	
	public FeatureVector() {
		
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getsTag() {
		return sTag;
	}

	public void setsTag(String sTag) {
		this.sTag = sTag;
	}

	public String getsNer() {
		return sNer;
	}

	public void setsNer(String sNer) {
		this.sNer = sNer;
	}

	public int getsType() {
		return sType;
	}

	public void setsType(int sType) {
		this.sType = sType;
	}

	public String getVerb() {
		return verb;
	}

	public void setVerb(String verb) {
		this.verb = verb;
	}

	public String getvTag() {
		return vTag;
	}

	public void setvTag(String vTag) {
		this.vTag = vTag;
	}

	public String getvCat() {
		return vCat;
	}

	public void setvCat(String vCat) {
		this.vCat = vCat;
	}

	public String getvProcess() {
		return vProcess;
	}

	public void setvProcess(String vProcess) {
		this.vProcess = vProcess;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public String getoTag() {
		return oTag;
	}

	public void setoTag(String oTag) {
		this.oTag = oTag;
	}

	public String getoNer() {
		return oNer;
	}

	public void setoNer(String oNer) {
		this.oNer = oNer;
	}

	public int getoType() {
		return oType;
	}

	public void setoType(int oType) {
		this.oType = oType;
	}

	public int getLabel() {
		return label;
	}

	public void setLabel(int label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return "FeatureVector [subject=" + subject + ", sTag=" + sTag + ", sNer=" + sNer + ", sType=" + sType
				+ ", verb=" + verb + ", vTag=" + vTag + ", vCat=" + vCat + ", vProcess=" + vProcess + ", object="
				+ object + ", oTag=" + oTag + ", oNer=" + oNer + ", oType=" + oType + ", label=" + label + "]";
	}
	
	
}
