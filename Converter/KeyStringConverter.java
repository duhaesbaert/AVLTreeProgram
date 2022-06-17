// Desenvolvido por Eduardo Haesbaert

package Converter;

public class KeyStringConverter {

    // Cria uma chave de index numerica utilizando o texto recebido. Caso o texto seja
    // maior do que 6 characteres, utiliza os 3 primeiros e 3 ultimos characteres.
    public static long ConvertStringToKey(String key) {
        char[] letters = key.toCharArray();

        if (letters.length > 6) {
            letters = reduceArraySize(key.toCharArray());
        }

        return convertCharToLongKey(letters);
    }

    // Cria uma chave de index utilizando os primeiros tres characteres do nome de PersonInfo.
    public static long ConvertStringToShortKey(String key) {
        char[] letters = key.toCharArray();
        char[] keyArr = new char[3];

        for (int i = 0; i <= keyArr.length-1; i++) {
            keyArr[i] = letters[i];
        }

        return convertCharToLongKey(keyArr);
    }

    // Cria uma chave de index numerica utilizando o texto de data recebido.
    public static long ConvertStringDateToKey(String dateKey) {
        return Long.parseLong(dateKey.replace("/", ""));
    }
    private static long convertCharToLongKey(char[] letters){
        StringBuilder sb = new StringBuilder();

        for (char ch : letters) {
            sb.append((byte) ch);
        }

        return Long.parseLong(sb.toString());
    }

    // reduceArraySize é utilizada por ConvertStringToKey para reduzir a chave para uma chave de 6
    // characteres, 3 iniciais e 3 finais.
    private static char[] reduceArraySize(char[] letters) {
        char[] sixLetters = new char[6];
        int sixLettersIndex = 0;

        for (int i = 0; i <=2; i++) {
            sixLetters[sixLettersIndex] = letters[i];
            sixLettersIndex++;
        }
        // Calcula o próximo charactere para utilizar na chave
        int j = letters.length - 3;


        for (int i = j; i <= letters.length -1; i++){
            sixLetters[sixLettersIndex] = letters[i];
            sixLettersIndex++;
        }

        return sixLetters;
    }

}
