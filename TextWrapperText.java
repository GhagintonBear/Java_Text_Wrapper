import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static junit.framework.TestCase.assertEquals;

public class TextWrapperText {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
    @Test
    public void excessiveSpaces(){
        String input = "If    I    could   fall           into   the   sky";
        String expected = "If I could\nfall into\nthe sky\n";
        int width = 10;
        TextWrapper.wrapText(input,width);
        charCount(outContent.toString(),width);
        assertEquals(expected.trim(), outContent.toString().trim());
    }
    @Test
    public void wordExceedingWidthTest(){
        String input = "Do you think timeWould passMe by?";
        String expected = "Check width! String contains word of greater character size that given width.";
        int width = 5;
        TextWrapper.wrapText(input,width);
        assertEquals(expected.trim(), outContent.toString().trim());
    }
    @Test
    public void spaceAlreadyBeforeAfterTest(){
        String input = " 'Cause you know I'd walk a thousand miles ";
        String expected = "'Cause you\nknow I'd walk\na thousand\nmiles\n";
        int width = 13;
        TextWrapper.wrapText(input,width);
        charCount(outContent.toString(),width);
        assertEquals(expected.trim(), outContent.toString().trim());
    }
    @Test
    public void wordWithPunctuationExceedingWidth(){
        String input = "If i could just see you tonight...";
        String expected = "Check width! String contains word of greater character size that given width.";
        int width = 8;
        TextWrapper.wrapText(input,width);
        assertEquals(expected.trim(),outContent.toString().trim());
    }
    @Test
    public void exactWidthTest(){
        String input = "ExactWidth";
        String expected = "ExactWidth";
        int width = 10;
        TextWrapper.wrapText(input,width);
        charCount(outContent.toString().trim(),width);
        assertEquals(expected.trim(), outContent.toString().trim());
    }
    @Test
    public void singleCharacterWordsWithUniformWidth(){
        String input = "a e i o u";
        String expected = "a\ne\ni\no\nu\n";
        int width = 1;
        TextWrapper.wrapText(input,width);
        charCount(outContent.toString().trim(),width);
        assertEquals(expected.trim(), outContent.toString().trim());
    }
    @Test
    public void givenTest(){
        String input  = "Triometric creates unique end user monitoring products for high-value Web " +
                "applications, and offers unrivalled expertise in performance consulting. " +
                "Triometric Analyzer  captures and reports on Web application usage, errors and " +
                "performance as experienced by real users. " +
                "Our customers include some of the world’s largest enterprises, information " +
                "providers and online retailers. " +
                "Our technology helps them manage upgrades and implementations, monitor " +
                "service levels and maximise end user satisfaction.";
        String expected = "Triometric creates\n" +
                "unique end user\n" +
                "monitoring products\n" +
                "for high-value Web\n" +
                "applications, and\n" +
                "offers unrivalled\n" +
                "expertise in\n" +
                "performance\n" +
                "consulting.\n" +
                "Triometric Analyzer\n" +
                "captures and reports\n" +
                "on Web application\n" +
                "usage, errors and\n" +
                "performance as\n" +
                "experienced by real\n" +
                "users. Our customers\n" +
                "include some of the\n" +
                "world’s largest\n" +
                "enterprises,\n" +
                "information\n" +
                "providers and online\n" +
                "retailers. Our\n" +
                "technology helps\n" +
                "them manage upgrades\n" +
                "and implementations,\n" +
                "monitor service\n" +
                "levels and maximise\n" +
                "end user\n" +
                "satisfaction.\n";
        int width = 20;
        TextWrapper.wrapText(input, width);
        charCount(outContent.toString(),width);
        assertEquals(expected.trim(), outContent.toString().trim());
    }

    /**
     *  This method counts the number of characters in each line of a given string by looking for new line
     *  character: '\n'. If character count exceeds character line limit, output is provided indicating which
     *  line fault occurred at.
     * @param input input string
     * @param width desired character wrap width
     */
    private static void charCount(String input, int width){
        int lineCount = 0;
        int charCount = 0;
        int inputLength = input.length();
        for(int i = 0; i < inputLength; i++){
            if(input.charAt(i) != '\n'){
                charCount++;
                if(charCount > width){
                    System.out.println("Line: " + lineCount + ", Width: " + charCount + ", Character Limit: " + width);
                }
            }else {
                // System.out.println("Line: " + lineCount + ", Width: " + charCount + ", Character Limit: " + width);
                charCount = 0;
                lineCount++;
            }
        }
    }
}
