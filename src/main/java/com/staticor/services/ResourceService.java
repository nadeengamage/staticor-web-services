package com.staticor.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.staticor.exceptions.ReportNotFoundException;
import com.staticor.models.dtos.ReportChartDto;
import com.staticor.models.dtos.ViewReportDto;
import com.staticor.models.reports.Report;
import com.staticor.models.reports.ReportChart;
import com.staticor.repositories.ReportRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.*;

@Service
public class ResourceService extends ServiceResponse {

    private final static Logger LOGGER = LogManager.getLogger(ResourceService.class);

    private final ReportRepository reportRepository;

    private final ReportService reportService;

    public ResourceService(ReportRepository reportRepository, ReportService reportService) {
        this.reportRepository = reportRepository;
        this.reportService = reportService;
    }

    // get pdf file
    public ServiceResponse generatePdfFile(ViewReportDto dto) {
        try {

            Optional<Report> report = reportRepository.findById(dto.getReportId());
            report.orElseThrow(ReportNotFoundException::new);

            Report data = report.get();

            // set charts
            List<ReportChartDto> charts = new ArrayList<>();

            Set<ReportChart> reportCharts = data.getCharts();

            for (ReportChart c : reportCharts) {
                ReportChartDto chartDto = new ReportChartDto();
                chartDto.setType(c.getChart().getName());
                chartDto.setSize(c.getSize().name());

                // get data
                chartDto.setLabels(reportService.getLabels(c.getQuery(), c, data.getCollection().getId()));
                chartDto.setData(reportService.getDataSet(c.getQuery(), c, data.getCollection().getId()));
                charts.add(chartDto);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, out);

            document.open();
            PdfPTable table = new PdfPTable(charts.get(0).getLabels().size());

            // report labels
            charts.get(0).getLabels().forEach(headerTitle -> {
                PdfPCell header = new PdfPCell();
                Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setBorderWidth(2);
                Map<String, Object> title = (HashMap<String, Object>) headerTitle;
                Map.Entry<String, Object> entry = title.entrySet().iterator().next();
                header.setPhrase(new Phrase(String.valueOf(entry.getValue()), headFont));
                table.addCell(header);
            });

            // report data
            charts.get(0).getData().get(0).forEach(o -> {
                Map<String, Object> d = (HashMap<String, Object>) o;
                Map.Entry<String, Object> entry = d.entrySet().iterator().next();
                PdfPCell cell = new PdfPCell(new Phrase(String.valueOf(entry.getValue())));
                cell.setPaddingLeft(4);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            });

            document.add(table);
            document.close();

            return success(true).code(200).message("Data Saved!").result(new ByteArrayInputStream(out.toByteArray()));
        } catch (Exception e) {
            LOGGER.error("SQL editor error " + e.getMessage());
            return success(false).code(500).errors(e.getLocalizedMessage());
        }
    }
}
