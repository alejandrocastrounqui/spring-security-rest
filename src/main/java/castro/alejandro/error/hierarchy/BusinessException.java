package castro.alejandro.error.hierarchy;

import java.util.Map;

public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private Map<String, String> data;
	private String key;
	
	public BusinessException(String description, Map<String, String> data, String key) {
		super(description);
		this.data = data;
		this.key = key;
	}
	
	public Map<String, String> getData() {
		return data;
	}
	public void setData(Map<String, String> data) {
		this.data = data;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }
}
