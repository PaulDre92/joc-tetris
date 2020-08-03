import java.util.Comparator;

import org.json.simple.JSONObject;

public class ScoreComparator implements Comparator<JSONObject> {

	@Override
	public int compare(JSONObject o1, JSONObject o2) {
		return Integer.parseInt(o2.get("score").toString()) - Integer.parseInt(o1.get("score").toString());
	}

}
