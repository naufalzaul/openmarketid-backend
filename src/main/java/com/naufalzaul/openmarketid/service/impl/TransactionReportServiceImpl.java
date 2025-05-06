package com.naufalzaul.openmarketid.service.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.naufalzaul.openmarketid.entity.Customer;
import com.naufalzaul.openmarketid.exception.DataNotFoundException;
import com.naufalzaul.openmarketid.model.response.TransactionDetailResponse;
import com.naufalzaul.openmarketid.model.response.TransactionResponse;
import com.naufalzaul.openmarketid.repository.CustomerRepository;
import com.naufalzaul.openmarketid.service.TransactionReportService;
import com.naufalzaul.openmarketid.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class TransactionReportServiceImpl implements TransactionReportService {

    private final CustomerRepository customerRepository;
    private final TransactionService transactionService;

    @Override
    public ByteArrayResource generateTransactionPdf() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Customer customer = customerRepository.findByUserEmail(email)
                .orElseThrow(() -> new DataNotFoundException("Customer not found with user email " + email));

        List<TransactionResponse> transactions = transactionService.findAllTransactionsByUserEmail();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Font boldFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
            Font regularFont = new Font(Font.FontFamily.HELVETICA, 10);
            NumberFormat rupiahFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

            document.add(new Paragraph("Transaction Report for: " + customer.getName(), boldFont));
            document.add(new Paragraph(" "));

            for (TransactionResponse trx : transactions) {
                for (TransactionDetailResponse detail : trx.transactionDetails()) {

                    PdfPTable table = new PdfPTable(2);
                    table.setWidthPercentage(100);
                    table.setSpacingBefore(12f);
                    table.setWidths(new float[]{3f, 7f});

                    table.addCell(createHeaderCell("Transaction ID", boldFont));
                    table.addCell(createValueCell(trx.id(), regularFont));

                    table.addCell(createHeaderCell("Customer", boldFont));
                    table.addCell(createValueCell(trx.customer().name(), regularFont));

                    table.addCell(createHeaderCell("Product", boldFont));
                    table.addCell(createValueCell(detail.productName(), regularFont));

                    table.addCell(createHeaderCell("Price", boldFont));
                    table.addCell(createValueCell(rupiahFormat.format(detail.productPrice()), regularFont));

                    table.addCell(createHeaderCell("Quantity", boldFont));
                    table.addCell(createValueCell(String.valueOf(detail.totalQuantity()), regularFont));

                    table.addCell(createHeaderCell("Total Tax", boldFont));
                    table.addCell(createValueCell(rupiahFormat.format(detail.taxAmount()), regularFont));

                    table.addCell(createHeaderCell("Total Amount", boldFont));
                    table.addCell(createValueCell(rupiahFormat.format(trx.totalAmount()), regularFont));

                    table.addCell(createHeaderCell("Payment Method", boldFont));
                    table.addCell(createValueCell(trx.paymentMethod().name(), regularFont));

                    table.addCell(createHeaderCell("Transaction Date", boldFont));
                    table.addCell(createValueCell(trx.transactionDate().toString(), regularFont));

                    table.addCell(createHeaderCell("Status", boldFont));
                    table.addCell(createValueCell(trx.transactionStatus().name(), regularFont));

                    document.add(table);
                }
            }

            document.close();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF", e);
        }

        return new ByteArrayResource(out.toByteArray());
    }

    private PdfPCell createHeaderCell(String content, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setPadding(7f);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        return cell;
    }

    private PdfPCell createValueCell(String content, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setPadding(7f);
        return cell;
    }

}
