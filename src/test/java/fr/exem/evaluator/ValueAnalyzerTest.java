package fr.exem.evaluator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValueAnalyzerTest {

    private ValueAnalyzer valueAnalyzer;

    @Before
    public void setUp() throws Exception {
        valueAnalyzer = ValueAnalyzer.newInstance()
                .withMaxCutConsecutif(4)
                .withNbEchantillonMoyenneBruit(6)
                .withNbEchantillonMoyenneNormal(3)
                .withSeuilAlerte(200)
                .withSeuilBruit(20)
                .withSeuilMouvement(1)
                .build();
    }

    @Test
    public void greaterThanSeuilBruit() throws Exception {
        assertFalse(valueAnalyzer.isGreaterThanSeuilBruit(19));
        assertFalse(valueAnalyzer.isGreaterThanSeuilBruit(20));
        assertTrue(valueAnalyzer.isGreaterThanSeuilBruit(21));
    }

    @Test
    public void lowerThanSeuilMouvement() throws Exception {
        valueAnalyzer.addValueAndResetCut(248);
        valueAnalyzer.addValueAndResetCut(249);
        valueAnalyzer.addValueAndResetCut(250);
        assertTrue(valueAnalyzer.isNewValueDifferenceLowerThanSeuilMouvement(249));
        assertFalse(valueAnalyzer.isNewValueDifferenceLowerThanSeuilMouvement(250));
        assertFalse(valueAnalyzer.isNewValueDifferenceLowerThanSeuilMouvement(251));
    }

    @Test
    public void canAddValue() throws Exception {
        assertTrue(valueAnalyzer.canAddValue(23));
        assertFalse(valueAnalyzer.canAddValue(18));
    }

    @Test
    public void isAlert() throws Exception {
        // Vérifier si on compare la dernière valeur ou la moyenne des valeurs
    }

    @Test
    public void getFullAverage() throws Exception {

    }

}