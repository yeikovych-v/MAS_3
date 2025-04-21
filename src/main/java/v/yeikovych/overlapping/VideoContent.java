package v.yeikovych.overlapping;

import static v.yeikovych.util.ValidationUtils.*;

public class VideoContent {

    private final int width;
    private final int height;
    private final int frameRate;
    private final boolean hasSubtitles;

    public VideoContent(int width, int height, int frameRate, boolean hasSubtitles) {
        throwIfFalse(() -> isPositiveOrZero(width), "Width must be positive or zero");
        throwIfFalse(() -> isPositiveOrZero(height), "Height must be positive or zero");
        throwIfFalse(() -> frameRate > 0, "Frame rate must be positive");

        this.width = width;
        this.height = height;
        this.frameRate = frameRate;
        this.hasSubtitles = hasSubtitles;
    }

    public String getResolution() {
        return width + "x" + height;
    }

    public String getFrameRate() {
        return frameRate + "fps";
    }

    public boolean hasSubtitles() {
        return hasSubtitles;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    public boolean isHasSubtitles() {
        return hasSubtitles;
    }
}
