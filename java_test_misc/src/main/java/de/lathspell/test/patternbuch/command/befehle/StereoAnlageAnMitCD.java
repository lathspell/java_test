package de.lathspell.test.patternbuch.command.befehle;

import de.lathspell.test.patternbuch.command.geräte.StereoAnlage;

public class StereoAnlageAnMitCD implements Befehl {

    StereoAnlage stereo;

    public StereoAnlageAnMitCD(StereoAnlage stereo) {
        this.stereo = stereo;
    }

    @Override
    public void ausführen() {
        stereo.ein();
        stereo.setCD();
        stereo.setLautstärke(11);
    }

    @Override
    public void rückgängig() {
        stereo.aus();
    }
}
