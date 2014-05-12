package simple_streamer;

/**
 * @author twlei
 * @date 12th May 2014
 */

import org.json.simple.JSONObject;

public class ImageResponse extends Response {
	private String type = "image";
	private byte[] data = null;
	
	public ImageResponse(byte[] data) {
		this.data = data;
	}
	
	public byte[] getData() {
		return data;
	}

	@Override
	String Type() {
		return type;
	}

	@SuppressWarnings("unchecked")
	@Override
	String ToJSON() {
		JSONObject obj = new JSONObject();
		obj.put("response", Type());
		obj.put("data", data);
		
		return obj.toJSONString();
	}

//	@Override
//	void FromJSON(String jst) {	
//	}

}
