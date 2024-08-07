package com.fawryEx.storyEx.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreDTO {
    private Long id;
    private String name;
    private String location;
    private String email;

    // Getters and setters
}
