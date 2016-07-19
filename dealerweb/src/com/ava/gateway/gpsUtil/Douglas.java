package com.ava.gateway.gpsUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class Douglas implements Serializable{
	public List<GPSPoint> narrayLPoints = null;

	protected List<GPSPoint> algorithm(List<GPSPoint> arrayLPoints,
			double WParam, double lngPosion) {
		this.narrayLPoints = arrayLPoints;
		compress(
				(GPSPoint) this.narrayLPoints.get(0),
				(GPSPoint) this.narrayLPoints.get(this.narrayLPoints.size() - 2),
				GpsUtil.distanceUnit(WParam, lngPosion));
		return this.narrayLPoints;
	}

	private void compress(GPSPoint from, GPSPoint to, double D) {
		boolean switchvalue = false;

		double A = (from.getLat() - to.getLat())
				/ Math.sqrt(Math.pow(from.getLat() - to.getLat(), 2.0D)
						+ Math.pow(from.getLng() - to.getLng(), 2.0D));

		double B = (to.getLng() - from.getLng())
				/ Math.sqrt(Math.pow(from.getLat() - to.getLat(), 2.0D)
						+ Math.pow(from.getLng() - to.getLng(), 2.0D));

		double C = (from.getLng() * to.getLat() - (to.getLng() * from.getLat()))
				/ Math.sqrt(Math.pow(from.getLat() - to.getLat(), 2.0D)
						+ Math.pow(from.getLng() - to.getLng(), 2.0D));

		double d = 0.0D;
		double dmax = 0.0D;
		int m = this.narrayLPoints.indexOf(from);
		int n = this.narrayLPoints.indexOf(to);
		if (n == m + 1)
			return;
		GPSPoint middle = null;
		List distance = new ArrayList();
		for (int i = m + 1; i < n; ++i) {
			d = Math.abs(A * ((GPSPoint) this.narrayLPoints.get(i)).getLng()
					+ B * ((GPSPoint) this.narrayLPoints.get(i)).getLat() + C)
					/ Math.sqrt(Math.pow(A, 2.0D) + Math.pow(B, 2.0D));

			distance.add(Double.valueOf(d));
		}
		dmax = ((Double) distance.get(0)).doubleValue();
		for (int j = 1; j < distance.size(); ++j) {
			if (((Double) distance.get(j)).doubleValue() > dmax)
				dmax = ((Double) distance.get(j)).doubleValue();
		}
		if (dmax > D)
			switchvalue = true;
		else
			switchvalue = false;
		if (!(switchvalue)) {
			List delArray = new ArrayList();
			for (int i = m + 1; i < n; ++i) {
				delArray.add((GPSPoint) this.narrayLPoints.get(i));
			}
			this.narrayLPoints.removeAll(delArray);
		} else {
			for (int i = m + 1; i < n; ++i) {
				if (Math.abs(A
						* ((GPSPoint) this.narrayLPoints.get(i)).getLng() + B
						* ((GPSPoint) this.narrayLPoints.get(i)).getLat() + C)
						/ Math.sqrt(Math.pow(A, 2.0D) + Math.pow(B, 2.0D)) != dmax)
					continue;
				middle = (GPSPoint) this.narrayLPoints.get(i);
			}
			compress(from, middle, D);
			compress(middle, to, D);
		}
	}
}