package de.lathspell.test.model.scopedproxy;


import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Manager {

    @Autowired
    @Getter
    private UserSettings settings;

}
