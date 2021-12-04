package ua.edu.ucu.tempseries;

public class Lesser implements Comparator{
    @Override
    public boolean isSuitable(double a, double b) {
        return a < b;
    }
}
