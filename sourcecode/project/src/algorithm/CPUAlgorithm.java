package algorithm;

public abstract class CPUAlgorithm {
	private String name;
	public abstract String displayHelp();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
