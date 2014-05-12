package simple_streamer;

/**
 * @author twlei
 * @date 12th May 2014
 */

import org.json.simple.JSONObject;

public class StartStreamResponse extends Response {
	private String type = "startingstream";
	
	public StartStreamResponse() {
		
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
		return obj.toJSONString();
	}

//	@Override
//	void FromJSON(String jst) {	
//	}

}
