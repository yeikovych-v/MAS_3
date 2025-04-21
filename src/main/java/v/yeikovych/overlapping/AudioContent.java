package v.yeikovych.overlapping;

import static v.yeikovych.util.ValidationUtils.*;

public class AudioContent {

    private final long durationMillis;
    private final AudioQuality audioQuality;


    public AudioContent(long durationMillis, AudioQuality audioQuality) {
        throwIfFalse(() -> isPositiveOrZero(durationMillis), "Duration must be positive or zero");
        this.durationMillis = durationMillis;
        this.audioQuality = audioQuality;
    }

    public double getDurationMinutes() {
        return durationMillis / 1000.0 / 60.0;
    }

    public AudioQuality getAudioQuality() {
        return audioQuality;
    }
}
