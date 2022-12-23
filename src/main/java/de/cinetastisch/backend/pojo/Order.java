package de.cinetastisch.backend.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Order {
    private Long userId;
    private Long showId;
    private List<Long> seatId;
}
