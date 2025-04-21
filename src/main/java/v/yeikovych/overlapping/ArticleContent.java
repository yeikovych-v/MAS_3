package v.yeikovych.overlapping;


import static v.yeikovych.util.ValidationUtils.*;

public class ArticleContent {

    private final String text;

    public ArticleContent(String text) {
        throwIfFalse(() -> isValidString(text), "Text cannot be null or empty");
        this.text = text;
    }

    public long getWordCount() {
        return text.split("\\s").length;
    }

    public double getReadTimeInMinutes(int wordsPerMinute) {
        return (double) getWordCount() / wordsPerMinute;
    }

    public String getText() {
        return text;
    }
}
