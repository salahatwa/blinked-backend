package com.blinked.apis.requests;

import javax.validation.constraints.NotEmpty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthWithRefreshTokenParam {
  @Schema(example = "1767995b-7865-430f-9181-189704235ae7", required = true)
  @NotEmpty(message = "Refresh token can't be empty")
  private String refreshToken;
}
