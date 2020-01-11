package gameClient;

import org.json.JSONException;
import org.json.JSONObject;

public class Robots {
	
	

	public Robots(String r) {
		init(r);
	}
	
	/**
	 * init new graph from string
	 * @param g - string with json format
	 */
	public void init(String r) {
		String pos;
		String split[];
		int id, src, dest;
		double value, speed;
		try {
			JSONObject obj = new JSONObject(r);
			JSONObject robot_param = obj.getJSONObject("Robot");
				id =  (int) robot_param.getInt("id");
				value = (double) robot_param.getDouble("value");
				src = (int) robot_param.getInt("src");
				dest = (int) robot_param.getInt("dest");
				speed = (double) robot_param.getDouble("speed");
				pos = (String) robot_param.getString("pos");
				split = pos.split(",");
				
				setId(id);
				setValue(value);
				setSrc(src);
				setDest(dest);
				setSpeed(speed);
				setPosX(Double.parseDouble(split[0]));
				setPosY(Double.parseDouble(split[1]));
				
		} catch (JSONException e) {e.printStackTrace();}

	}
	
	
	
	public int getId() {
		return _id;
	}
	public int getSrc() {
		return _src;
	}
	public int getDest() {
		return _dest;
	}
	public double getValue() {
		return _value;
	}
	public double getPosX() {
		return _posX;
	}
	public double getPosY() {
		return _posY;
	}
	public double getSpeed() {
		return _speed;
	}
	public void setId(int _id) {
		this._id = _id;
	}
	public void setSrc(int _src) {
		this._src = _src;
	}
	public void setDest(int _dest) {
		this._dest = _dest;
	}
	public void setValue(double _value) {
		this._value = _value;
	}
	public void setPosX(double _posX) {
		this._posX = _posX;
	}
	public void setPosY(double _posY) {
		this._posY = _posY;
	}
	public void setSpeed(double _speed) {
		this._speed = _speed;
	}



	private int _id, _src, _dest;
	private double _value, _posX, _posY;
	private double _speed;
	
}
