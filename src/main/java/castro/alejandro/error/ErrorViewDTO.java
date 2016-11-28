package castro.alejandro.error;

import java.util.Map;

import castro.alejandro.error.hierarchy.BusinessException;

public class ErrorViewDTO {
	
	private Map<String, String> data;
	private String key;
	private String description;
	
	public ErrorViewDTO(BusinessException model) {
		this.description = model.getMessage();
		this.data = model.getData();
		this.key = model.getKey();
	}
	
	public Map<String, String> getData() {
		return data;
	}
	public String getKey() {
		return key;
	}
	public String getDescription() {
		return description;
	}
	
	

}
