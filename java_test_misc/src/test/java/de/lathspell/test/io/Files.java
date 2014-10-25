package de.lathspell.test.io;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Arrays;
import java.util.Date;

import org.junit.Test;

public class Files {
    @Test
    public void creatingAndRenaming() throws IOException {
        File f = new File("tmp/Files.1.tmp");

        // lÃ¶schen
        f.delete();
        assertFalse(f.exists());
        
        // erzeugen
        f.createNewFile();
        assertTrue(f.canRead());
        
        // umbenennen
        File f2 = new File("tmp/Files.2.tmp");
        f.renameTo(f2);
        assertFalse(f.exists());
        assertTrue(f2.canWrite());
        
        // df
        long df = f2.getFreeSpace();
        assertTrue(df > Math.pow(2, 30));
        
        // tmpfile()
        File tmp = File.createTempFile("/tmp", "junit");
        assertTrue(tmp.canWrite());
        tmp.deleteOnExit();
        assertTrue(tmp.exists());
    }
    
    @Test
    public void writerAndReader() throws Exception {
        File f = new File("tmp", "Files.3.tmp");
        
        // Verzeichnis anlegen
        File dir = new File("tmp");
        if (dir.exists()) {
            if ( ! dir.isDirectory()) throw new Exception("Ist kein Verzeichnis!");
        } else {
            dir.mkdir();
        }
        
        // schreiben
        FileWriter fw = new FileWriter(f);
        fw.write(0x41);
        fw.write("test");
        fw.close();
        assertTrue(f.exists());
        assertTrue(f.lastModified() > new Date().getTime() - 10*1000);

        // lesen
        Reader fr = new FileReader(f);
        char[] cbuf = new char[255];
        int numread = fr.read(cbuf);
        fr.close();
        assertEquals(5, numread);
        // Caveat: das char[] von cbuf enthÃ¤lt noch einige \0 am Ende!
        assertEquals("Atest", new String(Arrays.copyOfRange(cbuf, 0, numread)));
        
        // buffered schreiben
        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
        bw.write("test");
        bw.close();
        
        // buffered lesen
        BufferedReader br = new BufferedReader(new FileReader(f));
        String s = br.readLine();
        assertEquals("test", s);
        
        // formatiert schreiben
        PrintWriter pw = new PrintWriter("tmp/Files.4.tmp");
        pw.printf("%07.2f", Math.PI);
        pw.close();
        
        // lesen (es gibt keinen PrintReader!)
        s = new BufferedReader(new FileReader("tmp/Files.4.tmp")).readLine();
        assertEquals("0003,14", s);
    }

    @Test
    public void listing() {
        File dir = new File("tmp");

        // Verzeichnis als Strings
        String[] direntries = dir.list();
        assertTrue(Arrays.asList(direntries).contains("Files.4.tmp"));
        
        // Verzeichnis als File
        File[] direntryfiles = dir.listFiles();
        assertTrue(Arrays.asList(direntryfiles).contains(new File("tmp/Files.4.tmp")));
        
        // Filter
        FilenameFilter fnfilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (name.matches("^Files\\.4\\.tmp$")) return true;
                return false;
            }
        };
        String[] filteredentries = dir.list(fnfilter);
        assertEquals("[Files.4.tmp]", Arrays.deepToString(filteredentries));
    }
}
