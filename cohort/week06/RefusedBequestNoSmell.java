abstract class Employee {

}


interface Researcher {
    void doResearch();
}


class Professor extends Employee implements Researcher {
    // Professor do research, teaching, admin, outreach

    public void doResearch() {

    }

    void doTeaching() {

    }

    void doAdmin() {

    }

    void doOutreach() {

    }
}


class Postdoc implements Researcher {
    // Postdoc do research (but no teaching, admin or outreach duties)
    public void doResearch() {

    }
}


class Admin extends Employee {
    // Admins help with administrative matter
}


public class RefusedBequestNoSmell {

}
