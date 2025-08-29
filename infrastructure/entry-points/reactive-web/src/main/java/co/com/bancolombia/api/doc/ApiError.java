package co.com.bancolombia.api.doc;

import java.util.Map;

public record ApiError(
    String timestamp,
    int status,
    String error,
    String code,
    String message,
    java.util.List<Map<String, String>> details
) {}
