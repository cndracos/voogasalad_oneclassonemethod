package engine.components;

public class Passable extends FlagComponent implements Component{

    public static final String KEY = "Passable";

    public Passable(int pid) {
        super(pid);
    }

    public String getKey() {
        return KEY;
    }
}
