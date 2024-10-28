import java.util.Objects;

public class State {
    private String name;
    private String abbreviation;

    // Constructor
    public State(String name, String abbreviation) {
        this.name = name;
        this.abbreviation = abbreviation.toUpperCase(); // Ensure the abbreviation is uppercase
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(abbreviation);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        State state = (State) obj;
        return abbreviation.equals(state.abbreviation);
    }

    @Override
    public String toString() {
        return name + " (" + abbreviation + ")";
    }
}