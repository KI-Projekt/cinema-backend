package de.cinetastisch.backend.enumeration;

import java.util.stream.Stream;

public enum RoomAvailableFeatures {
    HAS_THREE_D("3D"),
    HAS_DOLBY_ATMOS("Dolby Atmos");

    private final String value;

    RoomAvailableFeatures(String value) {
        this.value = value;
    }

    public static RoomAvailableFeatures fromValue(final String value){
//        for (RoomAvailableFeatures features : values()){
//            if (features.value.equalsIgnoreCase(value)){
//                return features;
//            }
//        }
//        throw new IllegalArgumentException("Unknown enum given");
        return Stream.of(RoomAvailableFeatures.values())
                     .filter(RoomAvailableFeatures -> RoomAvailableFeatures.value.equalsIgnoreCase(value))
                     .findFirst()
                     .orElseThrow(() -> new IllegalArgumentException("Unknown enum given"));
    }


//    	public static QuestionCategory fromValue(String value) {
//		for (QuestionCategory category : values()) {
//			if (category.value.equalsIgnoreCase(value)) {
//				return category;
//			}
//		}
//		throw new IllegalArgumentException(
//				"Unknown enum type " + value + ", Allowed values are " + Arrays.toString(values()));
//	}
}
