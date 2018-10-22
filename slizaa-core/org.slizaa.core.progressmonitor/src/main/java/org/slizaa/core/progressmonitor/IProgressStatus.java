package org.slizaa.core.progressmonitor;

public interface IProgressStatus {

    /**
     * @return
     */
    int getWorkDoneInTicks();

    /**
     * @return
     */
    int getWorkDoneInPercentage();

    /**
     * @return
     */
    int getTotalWorkTicks();

    /**
     *
     * @return
     */
    boolean isComplete();

    /**
     * @return
     */
    String getCurrentStep();
}
