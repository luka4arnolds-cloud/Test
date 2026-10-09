/** Standalone simulation core; Minecraft collision and rendering are not implemented. */
public final class DriftPhysics {
    public record State(double x, double z, double yaw, double vx, double vz, double steering) {}
    public record Input(double throttle, double brake, double steering, boolean drift) {}

    private static double clamp(double v, double lo, double hi) {
        return Math.max(lo, Math.min(hi, v));
    }

    public static State step(State s, Input i, Tuning t, double dt) {
        if (!Double.isFinite(dt) || dt <= 0 || dt > 0.05) {
            throw new IllegalArgumentException("Use fixed simulation steps of at most 50ms");
        }
        double raw = clamp(i.steering(), -1, 1);
        double target = Math.abs(raw) <= t.deadzone() ? 0 :
            Math.copySign((Math.abs(raw) - t.deadzone()) / (1 - t.deadzone()), raw);
        double steer = s.steering() + (target - s.steering()) * (1 - Math.exp(-t.steerResponse() * dt));
        double forward = s.vx() * Math.cos(s.yaw()) + s.vz() * Math.sin(s.yaw());
        double yaw = s.yaw() + steer * t.steerRate() * clamp(forward / 6, -1, 1) * dt;
        double c = Math.cos(yaw), n = Math.sin(yaw);
        double f = s.vx() * c + s.vz() * n;
        double lateral = -s.vx() * n + s.vz() * c;
        f += clamp(i.throttle(), 0, 1) * t.acceleration() * dt;
        double decel = clamp(i.brake(), 0, 1) * t.braking() * dt;
        f = Math.copySign(Math.max(0, Math.abs(f) - decel), f);
        f *= Math.exp(-t.rollingDrag() * dt);
        lateral *= Math.exp(-(i.drift() ? t.driftGrip() : t.grip()) * dt);
        double speed = Math.hypot(f, lateral);
        if (speed > t.maxSpeed()) { f *= t.maxSpeed() / speed; lateral *= t.maxSpeed() / speed; }
        double vx = f * c - lateral * n, vz = f * n + lateral * c;
        return new State(s.x() + vx * dt, s.z() + vz * dt, yaw, vx, vz, steer);
    }
}
