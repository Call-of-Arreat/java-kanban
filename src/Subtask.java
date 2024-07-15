public class Subtask extends Task {
    private final Epic epic;

    public Epic getEpic() {
        return epic;
    }

    public Subtask(String name, String description, Epic epic) {
        super(name, description);
        this.epic = epic;

    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "epic=" + epic.getName() +
                "} " + super.toString();
    }
}
