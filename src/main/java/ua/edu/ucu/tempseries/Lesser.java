package ua.edu.ucu.tempseries;

public class Lesser implements Comparator{
    @Override
    public boolean isSuitable(double toBeLesser, double toBeGreater) {
        return toBeLesser < toBeGreater;
    }
}
