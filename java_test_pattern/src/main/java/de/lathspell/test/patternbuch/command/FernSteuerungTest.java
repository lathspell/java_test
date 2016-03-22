package de.lathspell.test.patternbuch.command;

import de.lathspell.test.patternbuch.command.befehle.LichtAnBefehl;
import de.lathspell.test.patternbuch.command.befehle.LichtAusBefehl;
import de.lathspell.test.patternbuch.command.befehle.StereoAnlageAnMitCD;
import de.lathspell.test.patternbuch.command.befehle.StereoAnlageAusMitCD;
import de.lathspell.test.patternbuch.command.geräte.*;

public class FernSteuerungTest {

    public static void main(String[] args) {
        FernSteuerung fernsteuerung = new FernSteuerung();

        /*
         * Geräte laden
         */
        Licht licht = new Licht();
        StereoAnlage stereo = new StereoAnlage();

        /*
         * Befehle
         */
        LichtAnBefehl lichtEin = new LichtAnBefehl(licht);
        LichtAusBefehl lichtAus = new LichtAusBefehl(licht);
        StereoAnlageAnMitCD stereoAnlageAnFürCD = new StereoAnlageAnMitCD(stereo);
        StereoAnlageAusMitCD stereoAnlageAusFürCD = new StereoAnlageAusMitCD(stereo);

        /*
         * Zuordnung
         */
        fernsteuerung.setBefehl(0, stereoAnlageAnFürCD, stereoAnlageAusFürCD);
        fernsteuerung.setBefehl(1, lichtEin, lichtAus);

        /*
         * Demo
         */
        System.out.println(fernsteuerung);
        
        fernsteuerung.anKnopfWurdeGedrückt(0);
        fernsteuerung.anKnopfWurdeGedrückt(1);
        fernsteuerung.rückgängigKnopfWurdeGedrückt();
        fernsteuerung.ausKnopfWurdeGedrückt(0);
        fernsteuerung.ausKnopfWurdeGedrückt(1);
        fernsteuerung.anKnopfWurdeGedrückt(2);
    }
}
