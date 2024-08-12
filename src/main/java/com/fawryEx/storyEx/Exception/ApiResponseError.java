package com.fawryEx.storyEx.Exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponseError {
  private boolean success;
  private String message;
  private List<String> details;
  private LocalDateTime dateTime;

  private int statusCode;
}
