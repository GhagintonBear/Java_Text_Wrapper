import java.io.*;
import java.util.ArrayList;

/**
 * This class enables a user to feed in a text file with a desired output
 * column width, which is output to the screen wrapped to the specifed output
 * column width.
 */
public class TextWrapper
{

    /**
     * Run this method to run this program.
     */
    public static void main(String[] args)
    {
        /* this program requires 2 parameters to run */
        if (args.length >= 2)
        {
            try
            {
                TextWrapper textWrapper = new TextWrapper();
                String text = textWrapper.readText(new BufferedReader(new FileReader(args[0])));
                int colWidth = Integer.parseInt(args[1]);
                textWrapper.wrapText(text, colWidth);
            }
            catch (FileNotFoundException e)
            {
                System.err.println("Could not find file '" + args[0] + "'");
            }
            catch (NumberFormatException e)
            {
                System.err.println("'" + args[1] + "' is not a number");
            }
        }
        else
        {
            System.out.println("This program requires 2 parameters to run: " +
                    "a filename (string) and a column width (integer)");
        }
    }

    /**
     * Reads from the BufferedReader and returns the contents as a String.
     * @param br - the BufferedReader to read from.
     * @return the text as a String.
     */
    public String readText(BufferedReader br)
    {
        StringBuffer sb = new StringBuffer();
        try
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                sb.append(line);
            }
            br.close();
        }
        catch (IOException e)
        {
            System.err.println("An IOException occurred: " + e);
        }
        return sb.toString();
    }

    /**
     * Prints the text to the screen, wrapping each line at the 'width'.
     * @param text - the text to wrap and output.
     * @param width - the column width for outputting the text.
     */
    public static void wrapText(String text, int width) {
        ArrayList<Integer> spaceLocation = new ArrayList<Integer>();
        // adds space before/after string to ensure we spaceLocation knows where string starts/ends
        String inputText = " " + text + " ";
        // loop to find space locations in string and store int index in spaceLocation list
        for(int i = 0; i < inputText.length(); i++){
            if(inputText.charAt(i) == ' '){
                spaceLocation.add(i);
            }
        }
        String outputText = "";
        int count = 0;
        // loop through spaceLocation list, two indexes at a time, to determine word length size
        for(int i = 0; i < spaceLocation.size() - 1; i++){
            int wordStart = spaceLocation.get(i) + 1;
            int wordFinish = spaceLocation.get(i+1);
            int wordLength = wordFinish - wordStart;
            // conditional to determine if any word (including punctuation) is greater than given width
            if(wordLength > width){
                System.out.println("Check width! String contains word of greater character size that given width.");
                return;
            }
            // conditional to ensure first word in string isn't proceeded by unnecessary space: " "
            if(i == 0){
                outputText = outputText + inputText.substring(wordStart, wordFinish).trim();
                count = wordLength;
            }
            // conditional to deal with double spaces
            else if (wordLength > 0) {
                count = count + wordLength + 1;
                // add word to line if count<=width
                if(count <= width) {
                    outputText = outputText + " " + inputText.substring(wordStart, wordFinish).trim();
                } // else, add new line and then next word followed by resetting count
                else {
                    outputText = outputText + "\n" + inputText.substring(wordStart, wordFinish);
                    count = wordLength;
                }
            }
        }
        System.out.println(outputText.trim());
    }
}


