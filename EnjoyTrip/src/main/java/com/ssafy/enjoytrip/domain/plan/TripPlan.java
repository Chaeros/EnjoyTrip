package com.ssafy.enjoytrip.domain.plan;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TripPlan {
    private int id;
    private String title;
    private String content;
    private String departureDate;
    private String arrivalDate;
    private String image;
    private String memberId;
}
