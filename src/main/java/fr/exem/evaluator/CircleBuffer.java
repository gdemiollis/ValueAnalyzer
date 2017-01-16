package fr.exem.evaluator;

/**
 * Tableau de float circulaire (une fois rempli, les anciennes valeurs sont écrasées)
 */
public class CircleBuffer {

    private int writeIndex = 0;
    private int totalIndex = 0;
    private int size = -1;

    private double buffer[];
    private int sampleSize = -1;

    private CircleBuffer() {
    }

    /**
     * Rajouter une valeur au tableau
     *
     * @param val
     */
    public void pushValue(double val) {
        buffer[writeIndex++] = val;
        writeIndex = writeIndex % size;
        totalIndex++;
    }

    /**
     * Récupérer la moyenne des valeurs
     *
     * @return float
     */
    public float getFullAverage() {
        if (totalIndex < sampleSize) {
            return -1;
        }
        float sum = 0;
        int nbItem = 0;
        for (int i = 0; i < size; i++) {
            if (buffer[i] != -1) {
                sum += buffer[i];
                nbItem++;
            }
        }
        return sum / nbItem;
    }

    /**
     * Récupérer la moyenne des valeurs
     *
     * @return float
     */
    public double getDisplayableAverage() {
        if (totalIndex < sampleSize) {
//            throw new IllegalArgumentException("Pas assez d'échantillon pour calculer la moyenne");
            return -1;
        }
        float sum = 0;
        int itemCount = 0;
        int index_debut = (writeIndex > 0) ? (writeIndex - 1) : (size - 1);
        for (int i = index_debut; i > index_debut - sampleSize; i--) {
            int index = (i < 0) ? size + i : i;

            if (buffer[index % size] != -1) {
                sum += buffer[index % size];
                itemCount++;
            } else {
                break;
            }
        }
        return sum / itemCount;
    }

    double getLastValue() {
        return this.buffer[(writeIndex > 0) ? (writeIndex - 1) : (size - 1)];
    }

    private void setSize(int size) {
        this.size = size;
        buffer = new double[size];
        for (int i = 0; i < size; i++) {
            buffer[i] = -1;
        }
    }

    double getValue(int i) {
        return buffer[i];
    }

    public static CircleBuffer.Builder newInstance() {
        return new Builder();
    }

    private void setSampleSize(int sampleSize) {
        this.sampleSize = sampleSize;
    }

    public double getDifferentiel(double value) {
        double displayableAverage = getDisplayableAverage();
        if (displayableAverage == -1) {
            return -1;
        }
        return value - displayableAverage;
    }

    public boolean isEmpty() {
        for (double v : buffer) {
            if (v != -1) {
                return false;
            }
        }
        return true;
    }

    public static class Builder {

        private final CircleBuffer circleBuffer;

        Builder() {
            circleBuffer = new CircleBuffer();
        }

        public Builder withSize(int i) {
            circleBuffer.setSize(i);
            return this;
        }

        public Builder withSampleSize(int sampleSize) {
            circleBuffer.setSampleSize(sampleSize);
            return this;
        }

        public CircleBuffer build() {
            if (circleBuffer.sampleSize == -1) {
                throw new IllegalArgumentException("Préciser la taille de l'échantillon");
            }
            if (circleBuffer.size == -1) {
                throw new IllegalArgumentException("Préciser du buffer");
            }
            return circleBuffer;
        }
    }
}
