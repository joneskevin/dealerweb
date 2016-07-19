package com.ava.system;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ava.base.TestBase;
import com.ava.dealer.service.IDriveLineService;
import com.ava.util.gis.GisUtil;

public class TestDriveLine extends TestBase {
	@Autowired
	public IDriveLineService driveLineService;
	
	@Test
	@Ignore
	public void testGetDriveLineListByVehicleId() {
		List driveLists = driveLineService.getDriveLineListByVehicleId(11);
		if(driveLists != null && driveLists.size() > 0){
			String dd = "";
		}
	}
	
	@Test
	public void testInitDriveLineMileage() {
		
		System.out.print("111");
		driveLineService.initDriveLineMileage();
	}
	
	static double EARTH_RADIUS = 6378.137;
	private static double rad(double d){
	   return d * Math.PI / 180.0;
	}

	public static double GetDistance(double lat1, double lng1, double lat2, double lng2) {
	   double radLat1 = rad(lat1);
	   double radLat2 = rad(lat2);
	   double a = radLat1 - radLat2;
	   double b = rad(lng1) - rad(lng2);
	   double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) + 
	   Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
	   s = s * EARTH_RADIUS  * 10000/10000;
	   return s;
	}
	
	public static void main(String[] args) {
		double lat1 = 39.870037, lng1 = 116.31526,lat2 = 39.862284,lng2 =116.316553;
		double lat3 = 39.864887, lng3 = 116.328195,lat4 = 39.870037,lng4 =116.31526;
		double a = GetDistance(lat1, lng1, lat2, lng2) + GetDistance(lat2, lng2, lat3, lng3)+ GetDistance(lat3, lng3, lat4, lng4);
		double b = GisUtil.getDistance(lat1, lng1, lat2, lng2) + GisUtil.getDistance(lat2, lng2, lat3, lng3)+ GisUtil.getDistance(lat3, lng3, lat4, lng4);
		//String mileage = new DecimalFormat("#.#").format((float)a);
		//System.out.print(mileage);
		//System.out.print(GisUtil.getDistance(lat1, lng1, lat2, lng2));
		System.out.println(a);
		System.out.println(Math.round(b));
    }
}