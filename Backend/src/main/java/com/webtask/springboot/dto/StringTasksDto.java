package com.webtask.springboot.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StringTasksDto {
    private List<String> tasks;
}
