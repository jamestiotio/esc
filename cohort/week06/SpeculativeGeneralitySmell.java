abstract class Employee {
	
	
}

//sudiptac: over-engineering, no need to create these interfaces 
// if in near future, postdocs are unlikely to teach
interface ResearchFaculty {
	
}

interface TeachingFaculty {
	
}
// over engineered code ends

//class Professor extends Employee {
class Professor extends Employee implements ResearchFaculty,TeachingFaculty{

	// Professor do research and teaching
}

//class Postdoc extends Employee {
class Postdoc extends Employee implements ResearchFaculty{

	//Postdoc do research, but does not do teaching
}

class Admin extends Employee {
	//Admins help with administrative matter
}

public class SpeculativeGeneralitySmell {

}
