package grauly.hudcompass.util;

import grauly.hudcompass.waypoints.Waypoint;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;

public class MathHelper {

    private static final Vec3d forward = new Vec3d(0, 0, 1);
    private static final double atan2forward = 1.5707963267948966;
    private static final double arcToDegrees = 360d / (2d * Math.PI);

    private MathHelper() {
    }

    public static int determineCompassAngle(ClientPlayerEntity player) {
        var angle = (int) (-player.getYaw()) % 360;
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

    public static double determineWaypointAngleRelative(Vec3d playerPos, Waypoint waypoint) {
        var vecToPoint = newVectorFromAToB(makePlanarVector(playerPos), makePlanarVector(waypoint.getWaypoint()));
        vecToPoint = vecToPoint.normalize();
        return 360 - planarAngeToForward(vecToPoint);
    }

    public static Vec3d newVectorFromAToB(Vec3d a, Vec3d b) {
        return subtractVectors(b, a);
    }

    public static Vec3d subtractVectors(Vec3d a, Vec3d b) {
        return new Vec3d(a.x - b.x, a.y - b.y, a.z - b.z);
    }

    public static Vec3d makePlanarVector(Vec3d vector) {
        return new Vec3d(vector.x, 0, vector.z);
    }

    public static double dotProduct(Vec3d a, Vec3d b) {
        return a.x * b.x + a.y * b.y + a.z * b.z;
    }

    public static double planarAngleBetween(Vec3d a, Vec3d b) {
        double angle = Math.atan2(a.z, a.x) - Math.atan2(b.z, b.x);
        angle = angle * 360 / (2 * Math.PI);

        if (angle < 0) {
            angle += 360;
        }

        return angle;
    }

    public static double planarAngeToForward(Vec3d vector) {
        double angle = Math.atan2(vector.z, vector.x) - atan2forward;
        angle = angle * 360 / (2 * Math.PI);

        if (angle < 0) {
            angle += 360;
        }

        return angle;
    }
}
