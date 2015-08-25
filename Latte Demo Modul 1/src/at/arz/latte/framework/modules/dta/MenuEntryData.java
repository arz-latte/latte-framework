package at.arz.latte.framework.modules.dta;

public class MenuEntryData {
	private String value;

	private String href;

	private int position;

	public MenuEntryData() {
		super();
	}

	public MenuEntryData(String value, String href, int position) {
		super();
		this.value = value;
		this.href = href;
		this.position = position;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	@Override
	public String toString() {
		return "MenuEntryData [value=" + value + ", href=" + href + ", position=" + position + "]";
	}

}
