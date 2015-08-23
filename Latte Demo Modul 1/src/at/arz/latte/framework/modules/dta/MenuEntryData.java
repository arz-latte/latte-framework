package at.arz.latte.framework.modules.dta;

public class MenuEntryData {
	private String value;

	private String url;

	private int position;

	public MenuEntryData() {
		super();
	}

	public MenuEntryData(String value, String url, int position) {
		super();
		this.value = value;
		this.url = url;
		this.position = position;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	@Override
	public String toString() {
		return "MenuEntryData [value=" + value + ", url=" + url + ", position=" + position + "]";
	}

}
