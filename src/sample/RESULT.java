package sample;

public enum  RESULT {
    LETTER_REPETITION("Powatarzają się litery"),
    DIGIT_REPETITION("Powtarzają się cyfry"),
    NO_REPETITION("Brak powtórzeń");

    public final String friendlyName;

    RESULT(String friendlyName) {
        this.friendlyName = friendlyName;
    }
}
