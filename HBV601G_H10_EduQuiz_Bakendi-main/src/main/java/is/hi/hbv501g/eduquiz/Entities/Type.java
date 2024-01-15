package is.hi.hbv501g.eduquiz.Entities;

public enum Type {
    questionsAreText("Spurningar er texti"),
    questionsAreImageUrl("Spurningar er vefslóð á ljósmynd");

    private final String displayName;

    private Type(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static boolean contains(String test) {

        for (Type t : Type.values()) {
            if (t.name().equals(test)) {
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
