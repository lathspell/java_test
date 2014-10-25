package de.lathspell.test.patternbuch.command.befehle;

import de.lathspell.test.patternbuch.command.geräte.StereoAnlage;

public class StereoAnlageAusMitCD implements Befehl {

    StereoAnlage stereo;

    public StereoAnlageAusMitCD(StereoAnlage stereo) {
        this.stereo = stereo;
    }

    @Override
    public void ausführen() {
        stereo.aus();
    }

    @Override
    public void rückgängig() {
        stereo.ein();
        stereo.setCD();
        stereo.setLautstärke(11);
    }
}
