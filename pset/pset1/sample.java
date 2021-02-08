public class Miles {
    Miles() {
        // Add implementation here
    }
}

public class Plane {
    public String getType() {
        // Add implementation here
    }

    public Miles MaximumDistance() {
        // Add implementation here
    }
}

public class Flight {
      Plane assignedPlane;
      boolean isAssignedToPlane = false;
      Miles FlightDuration;

      public String getAssignedPlaneType() {
          if (isAssignedToPlane) {
              return assignedPlane.getType();
          }
      }

      public boolean checkDistance() {
          if (isAssignedToPlane) {
              return assignedPlane.MaximumDistance() > FlightDuration;
          }
      }

}