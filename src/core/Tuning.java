public record Tuning(double maxSpeed, double acceleration, double braking, double rollingDrag, double grip, double driftGrip, double steerRate, double steerResponse, double deadzone) {
    public static final Tuning DRIFT = new Tuning(24.0, 10.0, 18.0, 0.35, 7.0, 1.25, 1.7, 8.0, 0.12);
}
