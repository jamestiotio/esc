abstract class Duties {

    public abstract Duties nextDuty();

    // public abstract void performAdminJob();
}


// My admin duties
class AdminDuties extends Duties {
    public AdminDuties() {

    }

    public Duties nextDuty() {
        return null;
    }

    public void performAdminJob() {
        System.out.println("I am doing admin job");
    }
}

// My outreach duties
class OutreachDuties extends Duties {
    public OutreachDuties() {

    }

    public Duties nextDuty() {
        return new AdminDuties();
        // return new PresidentDuties();
    }
}

// My research duties
class ResearchDuties extends Duties {
    public ResearchDuties() {

    }

    public Duties nextDuty() {
        // return new OutreachDuties();
        return new PresidentDuties();
    }
}


// My teaching duties
class TeachingDuties extends Duties {

    public TeachingDuties() {

    }

    public Duties nextDuty() {
        return new ResearchDuties();
    }
}


public class MessageChainSmell {

    public static void main(String args[]) {
        TeachingDuties myDuties = new TeachingDuties();
        // sudiptac: this is a long message chain smell
        // likely to introduce bug in the future
        ((AdminDuties) myDuties.nextDuty().nextDuty()).performAdminJob();
    }
}
