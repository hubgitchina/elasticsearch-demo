package cn.com.ut.util;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class MapDistanceUtil {

	// 地球半径（半径为米）
	private static double EARTH_RADIUS = 6378.137;

	private static double rad(double d) {

		// 角度转换成弧度
		return d * Math.PI / 180.0;
	}

	/**
	 * 计算两个点之间的距离(单位：米)
	 * 
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return
	 */
	public static double getDistance(double lat1, double lng1, double lat2, double lng2) {

		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000d) / 10000d;
		s = s * 1000;
		return s;
	}

	/**
	 * 判断点是否在多边形内
	 * 
	 * @param point
	 *            检测点
	 * @param pts
	 *            多边形的顶点
	 * @return 点在多边形内返回true,否则返回false
	 */
	public static boolean isPtInPoly(Point2D.Double point, List<Point2D.Double> pts) {

		int N = pts.size();
		boolean boundOrVertex = true; // 如果点位于多边形的顶点或边上，也算做点在多边形内，直接返回true
		int intersectCount = 0;
		double precision = 2e-10; // 浮点类型计算时候与0比较时候的容差
		Point2D.Double p1, p2;
		Point2D.Double p = point; // 当前点

		p1 = pts.get(0);
		for (int i = 1; i <= N; ++i) {
			if (p.equals(p1)) {
				return boundOrVertex;
			}

			p2 = pts.get(i % N);
			if (p.x < Math.min(p1.x, p2.x) || p.x > Math.max(p1.x, p2.x)) {
				p1 = p2;
				continue;
			}

			if (p.x > Math.min(p1.x, p2.x) && p.x < Math.max(p1.x, p2.x)) {
				if (p.y <= Math.max(p1.y, p2.y)) {
					if (p1.x == p2.x && p.y >= Math.min(p1.y, p2.y)) {
						return boundOrVertex;
					}

					if (p1.y == p2.y) {
						if (p1.y == p.y) {
							return boundOrVertex;
						} else {
							++intersectCount;
						}
					} else {
						double xinters = (p.x - p1.x) * (p2.y - p1.y) / (p2.x - p1.x) + p1.y;
						if (Math.abs(p.y - xinters) < precision) {
							return boundOrVertex;
						}

						if (p.y < xinters) {
							++intersectCount;
						}
					}
				}
			} else {
				if (p.x == p2.x && p.y <= p2.y) {
					Point2D.Double p3 = pts.get((i + 1) % N);
					if (p.x >= Math.min(p1.x, p3.x) && p.x <= Math.max(p1.x, p3.x)) {
						++intersectCount;
					} else {
						intersectCount += 2;
					}
				}
			}
			p1 = p2;
		}

		if (intersectCount % 2 == 0) { // 偶数在多边形外
			return false;
		} else { // 奇数在多边形内
			return true;
		}

	}

	public static void main(String[] args) {

		Point2D.Double point = new Point2D.Double(113.566955924, 22.2807176126);

		// 测试一个点是否在多边形内
		List<Point2D.Double> pts = new ArrayList<Point2D.Double>();
		pts.add(new Point2D.Double(113.5667145252, 22.2806779014));
		pts.add(new Point2D.Double(113.5670256615, 22.280881421));
		pts.add(new Point2D.Double(113.5671544075, 22.2806729375));
		pts.add(new Point2D.Double(113.5668969154, 22.2805339483));

		if (isPtInPoly(point, pts)) {
			System.out.println("点在多边形内");
		} else {
			System.out.println("点在多边形外");
		}
	}
}