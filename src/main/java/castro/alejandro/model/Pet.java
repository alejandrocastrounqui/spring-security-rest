package castro.alejandro.model;

public class Pet {

	private String ownername;
	private String name;
	private String kind;
	
	
	
	public Pet(String name, String owner, String kind) {
		super();
		this.name = name;
		this.ownername = owner;
		this.kind = kind;
	}
	
	public String getOwnername() {
		return ownername;
	}
	public void setOwnername(String ownername) {
		this.ownername = ownername;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	
}
