package com.huluxia.image.base.imagepipeline.core;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/* compiled from: DefaultExecutorSupplier */
public class a implements b {
    private static final int wE = 2;
    private static final int wF = 1;
    private final Executor wG = Executors.newFixedThreadPool(2);
    private final Executor wH;
    private final Executor wI;
    private final Executor wJ;

    public a(int numCpuBoundThreads) {
        ThreadFactory backgroundPriorityThreadFactory = new c(10);
        this.wH = Executors.newFixedThreadPool(numCpuBoundThreads, backgroundPriorityThreadFactory);
        this.wI = Executors.newFixedThreadPool(numCpuBoundThreads, backgroundPriorityThreadFactory);
        this.wJ = Executors.newFixedThreadPool(1, backgroundPriorityThreadFactory);
    }

    public Executor hH() {
        return this.wG;
    }

    public Executor hI() {
        return this.wG;
    }

    public Executor hJ() {
        return this.wH;
    }

    public Executor hK() {
        return this.wI;
    }

    public Executor hL() {
        return this.wJ;
    }
}
