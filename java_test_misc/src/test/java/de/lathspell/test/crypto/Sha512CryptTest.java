package de.lathspell.test.crypto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class Sha512CryptTest {
    /** Based on the tests from the Ganymede project.
     *
     * See http://fossies.org/dox/ganymede-22nov2010/Sha512Crypt_8java_source.html.
     */
    @Test
    public void testSha512UnitTest() throws Exception {
        String msgs[] = {
            /* my tests: */
            "$6$xxxxxxxx", "geheim", "$6$xxxxxxxx$wuSdyeOvQXjj/nNoWnjjo.6OxUWrQFRIj019kh1cDpun6l6cpr3ywSrBprYRYZXcm4Kv9lboCEFI3GzBkdNAz/",
            "$6$xxxxxxxx$", "geheim", "$6$xxxxxxxx$wuSdyeOvQXjj/nNoWnjjo.6OxUWrQFRIj019kh1cDpun6l6cpr3ywSrBprYRYZXcm4Kv9lboCEFI3GzBkdNAz/",
            "$6$xxxxxxxxxxxxxxxx", "geheim", "$6$xxxxxxxxxxxxxxxx$Sd9AXORFsSJDUZpiTYq38IIJNv3U1yJLnfcEWXxQ6Y6MwC20cSLikLS4GDn.DajoTZip8MZWbQNGbBKELT2xY0",
            "$6$xxxxxxxxxxxxxxxx$", "geheim", "$6$xxxxxxxxxxxxxxxx$Sd9AXORFsSJDUZpiTYq38IIJNv3U1yJLnfcEWXxQ6Y6MwC20cSLikLS4GDn.DajoTZip8MZWbQNGbBKELT2xY0",
            /* original tests: */
            "$6$saltstring", "Hello world!", "$6$saltstring$svn8UoSVapNtMuq1ukKS4tPQd8iKwSMHWjl/O817G3uBnIFNjnQJuesI68u4OTLiBFdcbYEdFCoEOfaS35inz1",
            "$6$rounds=10000$saltstringsaltstring", "Hello world!", "$6$rounds=10000$saltstringsaltst$OW1/O6BYHV6BcXZu8QVeXbDWra3Oeqh0sbHbbMCVNSnCM/UrjmM0Dp8vOuZeHBy/YTBmSK6H9qs/y3RnOaw5v.",
            "$6$rounds=5000$toolongsaltstring", "This is just a test", "$6$rounds=5000$toolongsaltstrin$lQ8jolhgVRVhY4b5pZKaysCLi0QBxGoNeKQzQ3glMhwllF7oGDZxUhx1yxdYcz/e1JSbq3y6JMxxl8audkUEm0" /* rounds=5000 is omitted */,
            "$6$rounds=1400$anotherlongsaltstring", "a very much longer text to encrypt.  This one even stretches over morethan one line.", "$6$rounds=1400$anotherlongsalts$POfYwTEok97VWcjxIiSOjiykti.o/pQs.wPvMxQ6Fm7I6IoYN3CmLs66x9t0oSwbtEW7o7UmJEiDwGqd8p4ur1",
            "$6$rounds=77777$short", "we have a short salt string but not a short password", "$6$rounds=77777$short$WuQyW2YR.hBNpjjRhpYD/ifIw05xdfeEyQoMxIXbkvr0gge1a1x3yRULJ5CCaUeOxFmtlcGZelFl5CxtgfiAc0",
            "$6$rounds=123456$asaltof16chars..", "a short string", "$6$rounds=123456$asaltof16chars..$BtCwjqMJGx5hrJhZywWvt0RLE8uZ4oPwcelCjmw2kSYu.Ec6ycULevoBK25fs2xXgMNrCzIMVcgEJAstJeonj1",
            "$6$rounds=10$roundstoolow", "the minimum number is still observed", "$6$rounds=1000$roundstoolow$kUMsbe306n21p9R.FRkW3IGn.S9NPN0x50YhH1xhLsPuWGsUSklZt58jaTfF4ZEQpyUNGc0dqbpBYYBaHHrsX."
        };

        String result, correct;
        for (int i = 0; i < msgs.length/3; i++) {
            assertTrue(Sha512Crypt.verifyHashTextFormat(msgs[i * 3 + 2]));

            result = Sha512Crypt.Sha512_crypt(msgs[i * 3 + 1], msgs[i * 3], 0);
            correct = msgs[i * 3 + 2];
            assertEquals("i=" + i, correct, result);
        }
    }
}
