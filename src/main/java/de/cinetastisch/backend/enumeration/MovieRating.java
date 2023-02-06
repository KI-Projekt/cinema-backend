package de.cinetastisch.backend.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum MovieRating {
    INFO("INFO", 1),
    G   ("G", 2),
    PG  ("PG", 3),
    PG13("PG-13", 4),
    R   ("R", 5),
    NC17("NC-17", 6),
    NA  ("N/A", 7);

    public final String label;
    public final int order;

    MovieRating(String value, int order) {
        this.label = value;
        this.order = order;
    }

    private static final Map<String, MovieRating> BY_LABEL = new HashMap<>();

    static {
        for (MovieRating e: values()) {
            BY_LABEL.put(e.label, e);
        }
    }

    public static MovieRating valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }

    public static Object[] getLabels(){
        return BY_LABEL.keySet().toArray();
    }
}
