package com.apollo.dto.vault;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Temporary consultation access grant token details")
public class AccessGrantResponse {

    @Schema(description = "Access grant unique identifier")
    private UUID id;

    @Schema(description = "6-digit access code presented to clinician", example = "482910")
    private String accessCode;

    @Schema(description = "Expiration timestamp (15 minutes from generation)")
    private Instant expiresAt;

    @Schema(description = "Remaining seconds until token expires", example = "900")
    private long remainingSeconds;

    @JsonProperty("isUsed")
    @Schema(description = "Whether the token has already been consumed by a clinician", example = "false")
    private boolean isUsed;
}
