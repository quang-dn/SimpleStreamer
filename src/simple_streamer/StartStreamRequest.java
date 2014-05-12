package simple_streamer;

/**
 * @author twlei
 * @date 12th May 2014
 */

import org.json.simple.JSONObject;

public class StartStreamRequest extends Request {
	private String type = "startstream";
	private int ratelimit = 100; // default
	private boolean customRatelimit = false; // whether client has provided a custom ratelimit
	
	public StartStreamRequest() {
		
	}
	
	public StartStreamRequest(int ratelimit) {
		this.ratelimit = ratelimit;
		this.customRatelimit = true;
	}
	
	public int getRatelimit() {
		return ratelimit;
	}

	@Override
	String Type() {
		return type;
	}

	@SuppressWarnings("unchecked")
	@Override
	String ToJSON() {
		JSONObject obj = new JSONObject();
		obj.put("request", Type());
		
		if (customRatelimit) {
			obj.put("ratelimit", this.ratelimit);
		}
		
		return obj.toJSONString();
	}

//	@Override
//	void FromJSON(String jst) {	
//	}

}
