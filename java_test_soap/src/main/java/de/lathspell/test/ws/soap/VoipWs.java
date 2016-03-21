package de.lathspell.test.ws.soap;

import java.util.TreeSet;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;

@WebService
@XmlSeeAlso(value = { TDO.class })
public interface VoipWs {

    public void sayHi();


    public TreeSet<TDO> getDates(@WebParam(name="jahr")  int i);
}
