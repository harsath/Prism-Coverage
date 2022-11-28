package prism;

import java.io.Serializable;

public class StringType implements AtomType, Serializable {
	private String value;
	private boolean initialized;

	public StringType(String value) {
		this.value = value;
		this.initialized = true;
	}

	public StringType() {
		this.initialized = false;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isInitialized() {
		return this.initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	@Override
	public String toString() {
		if (initialized) {
			return value;
		} else {
			return "STRING";
		}
	}

}
