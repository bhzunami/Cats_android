package ch.swisscom.cats_android.model;


public class Event {

	private Integer id;
	private String pspnr;
	private String bName;
	private String atDate;
	private String timeFrom;
	private String timeTo;
	private String memo;

	public Event() {
	}

	public Event(Integer id, String pspnr, String atDate, String timeFrom,
			String timeTo, String memo) {
		this.id = id;
		this.pspnr = pspnr;
		this.atDate = atDate;
		this.timeFrom = timeFrom;
		this.timeTo = timeTo;
		this.memo = memo;
	}

	@Override
	public String toString() {
		return pspnr + " , " + atDate + " , " + timeFrom + " to " + timeTo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPspnr() {
		return pspnr;
	}

	public void setPspnr(String pspnr) {
		this.pspnr = pspnr;
	}

	public String getbName() {
		return bName;
	}

	public void setbName(String bName) {
		this.bName = bName;
	}

	public String getAtDate() {
		return atDate;
	}

	public void setAtDate(String atDate) {
		this.atDate = atDate;
	}

	public String getTimeFrom() {
		String formatedTime = new String();

		formatedTime = timeFrom.substring(0, 2) + ":"
				+ timeFrom.substring(3, 5);

		return formatedTime;
	}

	public void setTimeFrom(String timeFrom) {
		this.timeFrom = timeFrom;
	}

	public String getTimeTo() {
		String formatedTime = new String();

		formatedTime = timeTo.substring(0, 2) + ":"
				+ timeTo.substring(3, 5);

		return formatedTime;
	}

	public void setTimeTo(String timeTo) {
		this.timeTo = timeTo;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
