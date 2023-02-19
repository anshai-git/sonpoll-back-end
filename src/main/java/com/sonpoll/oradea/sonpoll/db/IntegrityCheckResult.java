package com.sonpoll.oradea.sonpoll.db;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IntegrityCheckResult {
    private VerificationResultType status;
    private String description; // Should contain some info in case of bad results
}
