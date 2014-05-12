package simple_streamer;

/**
 * @author twlei
 * @date 12th May 2014
 */

import org.json.simple.parser.JSONParser;

abstract class Response {

	protected static final JSONParser parser = new JSONParser();
	
	abstract String Type();
	
	abstract String ToJSON();

	// don't think we need this
	// abstract void FromJSON(String jst);
}