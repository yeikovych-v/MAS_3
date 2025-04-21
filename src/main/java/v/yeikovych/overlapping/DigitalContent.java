package v.yeikovych.overlapping;

import java.time.LocalDate;

import static v.yeikovych.util.ValidationUtils.*;

public class DigitalContent {

    private String title;
    private String creator;
    private final LocalDate creationDate;
    private final ArticleContent articleComponent;
    private final AudioContent audioComponent;
    private final VideoContent videoComponent;

    public DigitalContent(
            String title,
            String creator,
            ArticleContent articleComponent,
            AudioContent audioComponent,
            VideoContent videoComponent
    ) {
        setTitle(title);
        setCreator(creator);
        this.creationDate = LocalDate.now();
        this.articleComponent = articleComponent;
        this.audioComponent = audioComponent;
        this.videoComponent = videoComponent;
    }

    public long getWordCount() {
        if (articleComponent == null) {
            throw new NotSupportedBehaviorException("This Digital Content does not have an article component");
        }
        return articleComponent.getWordCount();
    }

    public double getReadTimeInMinutes(int wordsPerMinute) {
        if (articleComponent == null) {
            throw new NotSupportedBehaviorException("This Digital Content does not have an article component");
        }
        return articleComponent.getReadTimeInMinutes(wordsPerMinute);
    }

    public double getDurationMinutes() {
        if (audioComponent == null) {
            throw new NotSupportedBehaviorException("This Digital Content does not have an audio component");
        }
        return audioComponent.getDurationMinutes();
    }

    public AudioQuality getAudioQuality() {
        if (audioComponent == null) {
            throw new NotSupportedBehaviorException("This Digital Content does not have an audio component");
        }
        return audioComponent.getAudioQuality();
    }

    public String getResolution() {
        if (videoComponent == null) {
            throw new NotSupportedBehaviorException("This Digital Content does not have an video component");
        }
        return videoComponent.getResolution();
    }

    public String getFrameRate() {
        if (videoComponent == null) {
            throw new NotSupportedBehaviorException("This Digital Content does not have an video component");
        }
        return videoComponent.getFrameRate();
    }

    public boolean hasSubtitles() {
        if (videoComponent == null) {
            throw new NotSupportedBehaviorException("This Digital Content does not have an video component");
        }
        return videoComponent.hasSubtitles();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        throwIfFalse(() -> isValidString(title), "Invalid title");
        this.title = title;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        throwIfFalse(() -> isValidName(creator), "Invalid name");
        this.creator = creator;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }
}
