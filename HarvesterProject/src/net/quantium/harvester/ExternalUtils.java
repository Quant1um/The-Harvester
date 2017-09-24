package net.quantium.harvester;

/**
 * Utils from Project-Superposition
 */
public class ExternalUtils {
	
	public static float square(float v){
		return v * v;
	}
	
	public static float easeInElastic(float t) {
		return easeInElastic(t, 0.2f, 0.6f);
	}

	public static float easeInElastic(float t, float s, float l) {
		if(t <= 0) return 0;
		if(t >= 1) return 1;
		//(x/0.5)^2*(x<0.5)+(sin((x-0.5)*pi/0.1)*0.1*e*(1-x)+1)*(x>0.5)
		//http://fooplot.com/#W3sidHlwZSI6MCwiZXEiOiIoeC8wLjUpXjIqKHg8MC41KSsoc2luKCh4LTAuNSkqcGkvMC4xKSowLjEqZSooMS14KSsxKSooeD4wLjUpIiwiY29sb3IiOiIjMDAwMDAwIn0seyJ0eXBlIjoxMDAwLCJ3aW5kb3ciOlsiLTExLjg5NzMzNTg1NzE5MTg4IiwiMTIuODk3MzM1ODU3MTkxNjciLCItNi42Mjc5MjI2NDM4NzAwNzUiLCI4LjYyNzkyMjY0Mzg3MDAyNyJdfV0-
		if(t < l) return square(t / l);
		return (float) (Math.sin((t - l) * Math.PI / s) * s * Math.E * (1 - t) + 1);
	}
}
