package ua.edu.ucu.tempseries;

import lombok.Getter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.InputMismatchException;

public class TemperatureSeriesAnalysis {
    @Getter
    private double[] temperatureSeries;
    private int lastIdx;

    public TemperatureSeriesAnalysis() {

    }

    public TemperatureSeriesAnalysis(double[] temperatureSeries) {
        for(double temperature: temperatureSeries) {
            if(!isValidTemperature(temperature)) {
                throw new InputMismatchException();
            }
        }
        this.temperatureSeries = Arrays.copyOf(temperatureSeries, temperatureSeries.length);
        lastIdx = temperatureSeries.length - 1;
    }

    private boolean isValidTemperature(double temperature) {
        return temperature > -273;
    }

    public double average() {
        validateTemperaturesSeries();

        double sum = sum();
        return sum / temperatureSeries.length;
    }

    private void validateTemperaturesSeries() {
        if (temperatureSeries.length == 0) {
            throw new IllegalArgumentException();
        }
    }

    private double sum() {
        double sum = 0;
        for(double temperature: temperatureSeries) {
            sum += temperature;
        }
        return sum;
    }

    public double deviation() {
        validateTemperaturesSeries();
        double average = average();
        double sumOfDeviations = 0;

        for(double temperature: temperatureSeries) {
            sumOfDeviations += Math.pow(average - temperature, 2);
        }
        return sumOfDeviations / (temperatureSeries.length - 1);
    }

    public double min() {
        validateTemperaturesSeries();
        return findTempClosestToValue(Double.NEGATIVE_INFINITY);
    }

    public double max() {
        validateTemperaturesSeries();
        return findTempClosestToValue(Double.POSITIVE_INFINITY);
    }

    public double findTempClosestToZero() {
        return findTempClosestToValue(0);
    }

    public double findTempClosestToValue(double tempValue) {
        double minDiff = Double.POSITIVE_INFINITY;
        double closest = 0.0;
        for (int i = 0; i < temperatureSeries.length; i++) {
            double curr = Math.abs(temperatureSeries[i] - tempValue);
            if (curr < minDiff) {
                minDiff = curr;
                closest = temperatureSeries[i];
            } else if (curr == minDiff && tempValue > 0 && temperatureSeries[i] > closest) {
                closest = temperatureSeries[i];

            } else if (curr == minDiff && tempValue < 0 && temperatureSeries[i] < closest) {
                closest = temperatureSeries[i];
            }
        }
        return closest;
    }

    public double[] findTempsLessThen(double tempValue) {
        Comparator lesser = new Lesser();
        return findTempsByCondition(tempValue,lesser);

    }

    public double[] findTempsGreaterThen(double tempValue) {
        Comparator greater = new Greater();
        return findTempsByCondition(tempValue,greater);
    }

    private double[] findTempsByCondition(double tempValue, Comparator comparator) {
        validateTemperaturesSeries();
        int countOfTempGreater = 0;
        for (int i = 0; i < temperatureSeries.length; i++) {
            if (comparator.isSuitable(temperatureSeries[i],tempValue)) {
                countOfTempGreater++;
            }
        }
        double[] suitableTemperature = new double[countOfTempGreater];
        int j = 0;
        for (int i = 0; i < temperatureSeries.length; i++) {
            if (comparator.isSuitable(temperatureSeries[i],tempValue)) {
                suitableTemperature[j++] = temperatureSeries[i];
            }
        }

        return suitableTemperature;
    }

    public TempSummaryStatistics summaryStatistics() {
        validateTemperaturesSeries();
        TempSummaryStatistics statistics = new TempSummaryStatistics();
        statistics.setAvgTemp(average());
        statistics.setDevTemp(deviation());
        statistics.setMaxTemp(max());
        statistics.setMinTemp(min());
        return statistics;
    }

    public int addTemps(double... temps) {
        for (int i = 0; i < temps.length; i++) {
            if (lastIdx + i + 1 > temperatureSeries.length) {
                double[] extendedArr = new double[temperatureSeries.length * 2];
                System.arraycopy(temperatureSeries, 0, extendedArr, 0, temperatureSeries.length);
                temperatureSeries = extendedArr;
            }
            temperatureSeries[lastIdx + i] = temps[i];
            lastIdx++;
        }
        return (int) sum();
    }

}

