package is.hi.hbv501g.eduquiz.Entities;

public enum Category {
    MULTIPLECHOICE("Multiple Choice"),
    EDUCATIONAL("Lærdómsríkt"),
    MOVIES("Kvikmyndir"),
    QUOTES("Tilvitnanir"),
    GEOGRAPHY("Landafræði"),
    MUSIC("Tónlist"),
    PICTURE("Ljósmyndir");

    private final String displayName;

    private Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static boolean contains(String test) {

        for (Category c : Category.values()) {
            if (c.name().equals(test)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return getDisplayName();
    }


}