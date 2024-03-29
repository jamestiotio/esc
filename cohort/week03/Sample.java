public class Plane {
    public String getType() {
        // ...
    }

    public Miles MaximumDistance() {
        // ...
    }
}

public class Miles {
    // ...
}

public class Flight {
    Plane assignedPlane;
    boolean assignedPlane = false;
    Miles FlightDuration;

    public String getAssignedPlaneType() {
        if (assignedPlane) {
            return assignedPlane.getType();
        }
    }

    public boolean checkDistance() {
        if (assignedPlane) {
            return assignedPlane.MaximumDistance() > FlightDuration;
        }
    }
}