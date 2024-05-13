package com.ssafy.enjoytrip.domain.attraction;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AttractionDescription {
    private int contentId;
    private String homepage;
    private String overview;
    private String telname;
}