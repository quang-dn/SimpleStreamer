package simple_streamer;

/**
 * @author twlei
 * @date 12th May 2014
 */

import org.json.simple.JSONObject;

public class OverloadResponse extends Response {
	private String type = "overloaded";
	private JSONObject[] clients = null;
	private JSONObject server = null;
	private boolean handover = false; // don't need this after implementing handover

	public OverloadResponse() {
		
	}
	
	public OverloadResponse(JSONObject[] clients, JSONObject server) {
		this.clients = clients;
		this.server = server;
		this.handover = true;
	}
	
	public JSONObject[] getClients() {
		return clients;
	}
	
	public JSONObject getServer() {
		return server;
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
		
		if (handover) {
			obj.put("clients", clients);
			obj.put("server", server);
		}
		
		return obj.toJSONString();
	}

//	@Override
//	void FromJSON(String jst) {	
//	}

}
