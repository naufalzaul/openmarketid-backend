package com.naufalzaul.openmarketid.service;

import org.springframework.core.io.ByteArrayResource;

public interface TransactionReportService {
    public ByteArrayResource generateTransactionPdf();
}
