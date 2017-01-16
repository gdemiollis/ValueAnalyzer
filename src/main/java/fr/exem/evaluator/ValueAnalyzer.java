package fr.exem.evaluator;

public class ValueAnalyzer {

    private CircleBuffer mBuffer;

    private int cut_consecutifs = -1;
    private int maxCutConsecutifs = -1;
    private float seuilMouvement = -1;
    private float seuilBruit = -1;
    private float seuilAlerte = -1;

    private ValueAnalyzer() {
    }

    public void addValueAndResetCut(double value) {
        mBuffer.pushValue(value);
        cut_consecutifs = 0;
    }

    public void incrementCut() {
        cut_consecutifs++;
    }

    public boolean canAddValue(double value) {
        return isGreaterThanSeuilBruit(value) && (cut_consecutifs >= maxCutConsecutifs || mBuffer.isEmpty() || isNewValueDifferenceLowerThanSeuilMouvement(value));
    }

    boolean isNewValueDifferenceLowerThanSeuilMouvement(double value) {
        return mBuffer.getDifferentiel(value) < seuilMouvement;
    }

    protected boolean isGreaterThanSeuilBruit(double value) {
        return value > seuilBruit;
    }

    public boolean isAlert() {
        return mBuffer.getLastValue() >= seuilAlerte && isGreaterThanSeuilBruit(getDisplayableAverage());
    }

    //Value
    private double getDisplayableAverage() {
        return mBuffer.getDisplayableAverage();
    }

    public float getFullAverage() {
        return mBuffer.getFullAverage();
    }

    public void updateSeuil(float seuil) {
        this.seuilAlerte = seuil;
    }

    public static ValueAnalyzer.Builder newInstance() {
        return new Builder();
    }

    public static class Builder {

        private final ValueAnalyzer valueAnalyzer;
        private final CircleBuffer.Builder bufferBuilder;

        public Builder() {
            valueAnalyzer = new ValueAnalyzer();
            bufferBuilder = CircleBuffer.newInstance();
        }

        public ValueAnalyzer build() {
            valueAnalyzer.mBuffer = bufferBuilder.build();
            if (valueAnalyzer.maxCutConsecutifs == -1) {
                throw new IllegalArgumentException("Initialiser maxCutConsecutifs");
            }
            if (valueAnalyzer.seuilMouvement == -1) {
                throw new IllegalArgumentException("Initialiser seuilMouvement");
            }
            if (valueAnalyzer.seuilBruit == -1) {
                throw new IllegalArgumentException("Initialiser seuilBruit");
            }
            if (valueAnalyzer.seuilAlerte == -1) {
                throw new IllegalArgumentException("Initialiser seuilAlerte");
            }
            return valueAnalyzer;
        }

        public Builder withMaxCutConsecutif(int maxCutConsecutifs) {
            valueAnalyzer.maxCutConsecutifs = maxCutConsecutifs;
            return this;
        }

        public Builder withSeuilMouvement(float seuilMouvement) {
            valueAnalyzer.seuilMouvement = seuilMouvement;
            return this;
        }

        public Builder withSeuilBruit(float seuilBruit) {
            valueAnalyzer.seuilBruit = seuilBruit;
            return this;
        }

        public Builder withSeuilAlerte(float seuilAlerte) {
            valueAnalyzer.seuilAlerte = seuilAlerte;
            return this;
        }

        public Builder withNbEchantillonMoyenneBruit(int nbEchantillonsMoyenneBruit) {
            bufferBuilder.withSize(nbEchantillonsMoyenneBruit);
            return this;
        }

        public Builder withNbEchantillonMoyenneNormal(int nbEchantillonsMoyenneNormale) {
            bufferBuilder.withSampleSize(nbEchantillonsMoyenneNormale);
            return this;
        }
    }
}
