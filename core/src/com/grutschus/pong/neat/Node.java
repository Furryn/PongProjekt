package com.grutschus.pong.neat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Till on 01.11.2016
 */
public class Node {

    public final List<Connection> connections = new ArrayList<Connection>();
    public double value = 0.0;

    // Activation-Function
    public static double sigmoid(final double x) {
        return 1.0 / (1.0 + Math.exp(x));
    }
}
