package pw.common;
import java.util.*;
import javax.swing.*;
import java.awt.*;
public class Camera {
	public static ArrayList<GameObject> Objects = new ArrayList<GameObject>();
	public static int X = 0;
	public static int Y = 0;
	public static void Move(int x,int y) {
			X += x;
			Y += y;
	}
	public static void SetPosition(int x,int y) {
			X = x;
			Y = y;
	}
	public static void SetPosition(int x,int y,boolean ForceMode) {
		X = x;
		Y = y;
	}
}
