package ru.nsu.g.solovev5.m.task131;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A util class to find substrings in text.
 */
public class Searcher {
    /**
     * Searches a word in given file using Knuth-Morris-Pratt algorithm.
     *
     * @param filename a file to search in
     * @param word a word to search
     * @return a list of occurrence start indices
     * @throws FileNotFoundException if the named file does not exist, is a directory rather
     *     than a regular file, or for some other reason cannot be opened for reading.
     * @throws IOException when an I/O exception occurred during file reading
     */
    public static List<Long> find(String filename, String word) throws IOException {
        var occurrences = new ArrayList<Long>();
        var table = computePartialMatchTable(word);
        long j = 0;
        int k = 0;
        try (var reader = new BufferedReader(new FileReader(filename))) {
            var code = reader.read();
            while (code != -1) {
                var ch = (char) code;

                if (word.charAt(k) == ch) {
                    code = reader.read();
                    j++;
                    k++;
                    if (k == word.length()) {
                        occurrences.add(j - k);
                        k = table[k];
                    }
                } else {
                    k = table[k];
                    if (k < 0) {
                        code = reader.read();
                        j++;
                        k++;
                    }
                }
            }
        }

        return occurrences;
    }

    /**
     * Computes a partial match table(failure function) for Knuth–Morris–Pratt algorithm.
     *
     * @param word a word to search
     * @return a computed table
     */
    private static int[] computePartialMatchTable(String word) {
        var table = new int[word.length() + 1];
        table[0] = -1;
        var pos = 1;
        var cnd = 0;
        while (pos < word.length()) {
            if (word.charAt(pos) == word.charAt(cnd)) {
                table[pos] = cnd;
            } else {
                table[pos] = cnd;
                while (cnd >= 0 && word.charAt(pos) != word.charAt(cnd)) {
                    cnd = table[cnd];
                }
            }

            pos++;
            cnd++;
        }

        table[pos] = cnd;
        return table;
    }
}
