package com.jcertpre.model;

public enum JapaneseLevel {
    NEWBIE("Beginner"),  // nếu bạn muốn có trường hợp “Người mới”
    N5("N5"),
    N4("N4"),
    N3("N3"),
    N2("N2"),
    N1("N1");

    private final String displayName;

    JapaneseLevel(String displayName) {
        this.displayName = displayName;
    }
    public String getDisplayName() {
        return displayName;
    }
    // Nếu cần parse từ string:
    public static JapaneseLevel fromString(String value) {
        if (value == null) return null;
        for (JapaneseLevel lvl : values()) {
            if (lvl.name().equalsIgnoreCase(value) || lvl.displayName.equalsIgnoreCase(value)) {
                return lvl;
            }
        }
        throw new IllegalArgumentException("Không hợp lệ JapaneseLevel: " + value);
    }
}
