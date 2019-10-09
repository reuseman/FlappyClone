package utils;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Saver gestisce il salvataggio di stringhe con delle key mnemonche
 * che identificano in modo univoco il dato.
 * Inoltre la classe fornisce dei metodi per la scrittura e la lettura.
 * @author Alex Colucci
 */

public class Saver {
    public static final String separator = "=";

    private String path;
    private File file;

    /**
     * Crea un nuovo Saver a partire
     * dal path assoluto del file su cui si vuole salvare.
     *
     * @param path Il path assoluto di un file esistente o meno.
     */
    public Saver(String path) {
        this.path = "file://".concat(new File("").getAbsolutePath().concat("/src").concat(path));
        try {
            this.file = new File(new URI(this.path));
            if (!file.exists()) {
                FileWriter fileWriter = new FileWriter(this.file, false);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    /**
     * Scrive una riga sul file composta dalla key con il separatore e il value.
     * Inoltre se la key è già presente, il value verrà sostituito.
     *
     * @param key La key che identifica in modo univoco il valore.
     * @param value Il valore da salvare.
     */
    public void writeLine(String key, String value) {
        if (this.getValue(key) == null)
            this.writeLine(key, value, true);
        else {
            this.replace(key, value);
        }
    }

    /**
     * Gestisce la scrittura di una riga con la possibilità di aggiungere o cancellare il file.
     *
     * @param key La key che identifica in modo univoco il valore.
     * @param value Il valore da salvare.
     * @param append Se il valore è false il file esistente verrà cancellato e poi sarà scritto,
     *               altrimenti se il valore è true la riga verrà aggiunta al file.
     */
    private void writeLine(String key, String value, boolean append) {
        String stringToWrite = key.concat(Saver.separator).concat(value);
        try {
            FileWriter fileWriter = new FileWriter(this.file, append);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(stringToWrite);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gestisce la scrittura di un intero file con un ArrayList di righe.
     *
     * @param lines Un ArrayList di Stringhe in cui ogni elemento è una linea.
     */
    private void writeFile(ArrayList<String> lines) {
        try {
            FileReader fileReader = new FileReader(this.file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String[] splittedLine;
            for (int i = 0; i < lines.size(); i++) {
                splittedLine = lines.get(i).split(Saver.separator);
                if (i == 0) {
                    writeLine(splittedLine[0], splittedLine[1], false);
                }
                else {
                    writeLine(splittedLine[0], splittedLine[1]);
                }
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Gestisce la lettura di un intero file restituendo un ArrayList di righe.
     *
     * @return lines Un ArrayList di Stringhe in cui ogni elemento è una linea.
     */
    private ArrayList<String> readFile() {
        try {
            FileReader fileReader = new FileReader(this.file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String currentLine;
            ArrayList<String> lines = new ArrayList<>();

            while ((currentLine = bufferedReader.readLine()) != null) {
                lines.add(currentLine);
            }

            bufferedReader.close();
            return lines;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Sovrascrive una riga sul file composta dalla key con il separatore e il value.
     *
     * @param key La key che identifica in modo univoco il valore.
     * @param value Il valore da sostituire.
     */
    public void replace(String key, String value) {
        ArrayList<String> lines = this.readFile();
        String toCompare;
        String splittedLine[];

        for (int i = 0; i < lines.size(); i++) {
            toCompare = lines.get(i);
            if (toCompare.startsWith(key)) {
                splittedLine = toCompare.split(Saver.separator);
                toCompare = splittedLine[0].concat(Saver.separator).concat(value);
                lines.set(i, toCompare);
            }
        }

        this.writeFile(lines);
    }

    /**
     * Rimuove una riga sul file
     * composta dalla key con il separatore e il value.
     *
     * @param key La key che identifica in modo univoco il valore.
     */
    public void removeLine(final String key) {
        String toCompare;
        ArrayList<String> lines = this.readFile();

        for (int i = 0; i < lines.size(); i++) {
            toCompare = lines.get(i);
            if (toCompare.startsWith(key)) {
                lines.remove(i);
            }
        }

        this.writeFile(lines);
    }

    /**
     * Restituisce il valore di una riga identificata da Key.
     *
     * @return value Il valore della riga, se il valore è null la Key non è stata trovata
     */
    public String getValue(String key) {
        try {
            FileReader fileReader = new FileReader(this.file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String currentLine;

            while ((currentLine = bufferedReader.readLine()) != null) {
                if (currentLine.startsWith(key)) {
                    return currentLine.split(Saver.separator)[1];
                }
            }

            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
