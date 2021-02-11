import java.util.List;

class GlobalNetworkManager {
    RailwayNetwork railwayNetwork;
    List<Train> trains;

    void update() {
        // Add implementation here
    }

    void move(Train t) {
        // Add implementation here
    }

    void wait(Train t) {
        // Add implementation here
    }
}

class Train {
    int trainID;
    int trainType; // 0 - narrow, 1 - meter, 2 - broad
    Engine engine;
    RailwayObject currentObject;
    double moveSpeed;
    double position;
    Route trainRoute;

    void changeEngine(int type) {
        // Add implementation here
    }
}

class Engine {
    int engineID;
}

class Route {
    List<Junction> waypoints;
    
    void push() {
        // Add implementation here
    }

    void pop() {
        // Add implementation here
    }
}

class RailwayNetwork {
    List<Track> tracks;
    List<Junction> junctions;

    void addTrack(int trackID) {
        // Add implementation here
    }

    void removeTrack(int trackID) {
        // Add implementation here
    }

    void addJunction(int junctionID) {
        // Add implementation here
    }

    void removeJunction(int junctionID) {
        // Add implementation here
    }
}

abstract class RailwayObject {
    int id;
    boolean isOccupied;
}

class Track extends RailwayObject {
    int trackType; // 0 - narrow, 1 - meter, 2 - broad
    double trackLength;
    Junction startJunction;
    Junction endJunction;
}

class Junction extends RailwayObject {
    List<Track> incidentTracks;

    List<Track> listConnectedTracks(int type) {
        // Add implementation here
    }
}