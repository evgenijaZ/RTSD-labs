package edu.kpi.comsys.model;

import org.apache.commons.math3.distribution.GammaDistribution;

class Generator {
    private double shape = 3;
    private double[] periodCoeff = {1, 4, 8};
    private double periodScale = 2;
    private double[] processCoeff = {1, 4, 8};
    private double processScale = 3;

    private GammaDistribution periodDistribution;
    private GammaDistribution processDistribution;

    Generator(int i) {
        periodDistribution = new GammaDistribution(shape, periodScale*periodCoeff[i]);
        processDistribution = new GammaDistribution(shape, processScale * periodCoeff[i]);
    }

    int generateStartTime(int now) {
        return now + generatePeriod();
    }

    private int generatePeriod() {
         return (int)(Math.round(periodDistribution.sample()));
    }

    int generateProcessTime() {
        return (int)(Math.round(processDistribution.sample()));
    }
}