package de.lathspell.test.model.scopedproxy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserSettingsImpl implements UserSettings {

    private String theme;

    @Override
    public void setTheme(String theme) {
        log.info("setTheme({})", theme);
        this.theme = theme;
    }

    @Override
    public String getTheme() {
        log.info("getTheme() => {}", theme);
        return theme;
    }

}
