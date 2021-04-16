class Miles {
    Miles() {
        // Add implementation here
    }
}


class Plane {
    public String getType() {
        // Add implementation here
    }

    public Miles maximumDistance() {
        // Add implementation here
    }
}


class Flight {
    Plane assignedPlane;
    boolean isAssignedToPlane = false;
    Miles flightDuration;

    public String getAssignedPlaneType() {
        if (isAssignedToPlane) {
            return assignedPlane.getType();
        }
    }

    public boolean checkDistance() {
        if (isAssignedToPlane) {
            return assignedPlane.maximumDistance() > flightDuration;
        }
    }

}
