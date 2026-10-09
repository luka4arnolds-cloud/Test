public class PhysicsChecks {
    static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
    static DriftPhysics.State run(boolean drift, double steer, int ticks) {
        var s = new DriftPhysics.State(0, 0, 0, 12, 0, 0);
        for (int k = 0; k < ticks; k++)
            s = DriftPhysics.step(s, new DriftPhysics.Input(0.5, 0, steer, drift), Tuning.DRIFT, 0.05);
        return s;
    }
    static double slip(DriftPhysics.State s) {
        return Math.abs(-s.vx() * Math.sin(s.yaw()) + s.vz() * Math.cos(s.yaw()));
    }
    public static void main(String[] args) {
        var drift = run(true, 0.7, 30);
        var grip = run(false, 0.7, 30);
        check(slip(drift) > slip(grip) * 1.5, "Drift must retain more lateral velocity");
        var left = run(true, -0.7, 30);
        check(Math.abs(drift.x() - left.x()) < 1e-9 && Math.abs(drift.z() + left.z()) < 1e-9,
              "Left and right steering must be symmetric");
        check(run(false, 0.08, 30).yaw() == 0, "Stick noise inside deadzone must not steer");
        var s = new DriftPhysics.State(0, 0, 0, 12, 0, 0);
        for (int k = 0; k < 100; k++) s = DriftPhysics.step(s, new DriftPhysics.Input(0, 1, 0, false), Tuning.DRIFT, 0.05);
        check(Math.hypot(s.vx(), s.vz()) == 0, "Braking must stop without reversing");
        var fast = run(true, 1, 10000);
        check(Double.isFinite(fast.x()) && Math.hypot(fast.vx(), fast.vz()) <= Tuning.DRIFT.maxSpeed() + 1e-9,
              "Long simulation must remain finite and speed limited");
        check(Math.abs(run(false, 0.3, 30).yaw()) < Math.abs(run(false, 1, 30).yaw()),
              "Analog partial steering must differ from full keyboard steering");
        System.out.println("6 physics checks passed (headless core only; no Minecraft or controller-device test)");
    }
}
