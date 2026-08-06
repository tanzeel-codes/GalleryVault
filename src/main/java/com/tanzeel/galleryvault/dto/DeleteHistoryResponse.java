package com.tanzeel.galleryvault.dto;

public class DeleteHistoryResponse {
    
    private final String message;

    private final long deletedRecords;

    public DeleteHistoryResponse(String message, long deletedRecords) {
        this.message = message;
        this.deletedRecords = deletedRecords;
    }

    public String getMessage() {
        return message;
    }

    public Long getDeletedRecords() {
        return deletedRecords;
    }

}
