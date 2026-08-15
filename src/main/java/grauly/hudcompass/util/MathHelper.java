package grauly.hudcompass.util;

import grauly.hudcompass.waypoints.Waypoint;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.Vec3;

public class MathHelper {

    private static final Vec3 forward = new Vec3(0, 0, 1);
    private static final double atan2forward = 1.5707963267948966;
    private static final double arcToDegrees = 360d / (2d * Math.PI);

    private MathHelper() {
    }

    public static int determineCompassAngle(LocalPlayer player) {
        var angle = (int) (-player.getYRot()) % 360;
        if (angle < 0) {
            angle += 360;
        }
        return angle;
    }

    public static int determineXPosOnCompass(int currentAngle, int absoluteAngle) {
        var pos = currentAngle - absoluteAngle;
        if (pos > 180) {
            pos -= 360;
        }
        if (pos < -180) {
            pos += 360;
        }
        return pos;
    }

    public static double determineWaypointAngleRelative(Vec3 playerPos, Waypoint waypoint) {
        var vecToPoint = newVectorFromAToB(makePlanarVector(playerPos), makePlanarVector(waypoint.getWaypoint()));
        vecToPoint = vecToPoint.normalize();
        return 360 - planarAngeToForward(vecToPoint);
    }

    public static Vec3 newVectorFromAToB(Vec3 a, Vec3 b) {
        return subtractVectors(b, a);
    }

    public static Vec3 subtractVectors(Vec3 a, Vec3 b) {
        return new Vec3(a.x - b.x, a.y - b.y, a.z - b.z);
    }

    public static Vec3 makePlanarVector(Vec3 vector) {
        return new Vec3(vector.x, 0, vector.z);
    }

    public static double dotProduct(Vec3 a, Vec3 b) {
        return a.x * b.x + a.y * b.y + a.z * b.z;
    }

    public static double planarAngleBetween(Vec3 a, Vec3 b) {
        double angle = Math.atan2(a.z, a.x) - Math.atan2(b.z, b.x);
        angle = angle * 360 / (2 * Math.PI);

        if (angle < 0) {
            angle += 360;
        }

        return angle;
    }

    public static double planarAngeToForward(Vec3 vector) {
        double angle = Math.atan2(vector.z, vector.x) - atan2forward;
        angle = angle * 360 / (2 * Math.PI);

        if (angle < 0) {
            angle += 360;
        }

        return angle;
    }
}
