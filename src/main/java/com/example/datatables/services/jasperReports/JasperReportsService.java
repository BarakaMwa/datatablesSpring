package com.example.datatables.services.jasperReports;

import com.example.datatables.dtos.ActorJasperDto;
import com.example.datatables.repositories.actors.ActorRepository;
import lombok.extern.log4j.Log4j2;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.sql.DataSource;
import java.io.*;
import java.util.HashMap;

@Service
@Log4j2
public class JasperReportsService {

    public static final String SRC_MAIN_RESOURCES_PATH = "src/main/resources/";
    private final ActorRepository actorRepository;
    private final DataSource dataSource;

    @Autowired
    public JasperReportsService(ActorRepository actorRepository, DataSource dataSource) {
        this.actorRepository = actorRepository;
        this.dataSource = dataSource;
    }

    public Boolean generatePDF(ActorJasperDto request) {


        try {

            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("firstName", request.getFirstName());
            parameters.put("lastName", request.getLastName());

            File fileReport = ResourceUtils.getFile("classpath:Actors.jrxml");
            InputStream inputStream = new FileInputStream(fileReport);
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource.getConnection());
            JasperExportManager.exportReportToPdfFile(jasperPrint, SRC_MAIN_RESOURCES_PATH + "Actors_By_" + request.getFirstName() + ".pdf");
//            ByteArrayOutputStream pdfReportStream = new ByteArrayOutputStream();

           /* File file = new File(SRC_MAIN_RESOURCES_PATH + "Actors_By_" + request.getFirstName() + ".pdf");
            if (file.exists()) {
                FileOutputStream outputStream = new FileOutputStream(file.getAbsolutePath());
                outputStream.write(pdfReportStream.toByteArray());
                outputStream.close();
            }*/

            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public File generateXLS(ActorJasperDto request) {


        try {
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("firstName", request.getFirstName());
            parameters.put("lastName", request.getLastName());

            System.out.println(dataSource);
            File fileReport = ResourceUtils.getFile("classpath:Actors.jrxml");
            InputStream inputStream = new FileInputStream(fileReport);
//            FileInputStream inputStream = new FileInputStream("C:\\Program Files\\Ampps\\www\\datatables\\src\\main\\resources\\Actors.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource.getConnection());

            File file = null;
            
            return file;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public File generatePdfFile(ActorJasperDto request){
        try {
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("firstName", request.getFirstName());
            parameters.put("lastName", request.getLastName());

            File file = new File(SRC_MAIN_RESOURCES_PATH + "Actors_By_" + request.getFirstName() + ".pdf");
            if (file.exists()) {
                file.delete();
            }
            Resource resource = new ClassPathResource("classpath:Actors.jrxml");
            InputStream reportStream = resource.getInputStream();
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters, dataSource.getConnection());
            JRPdfExporter jrPdfExporter = new JRPdfExporter();
            jrPdfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            ByteArrayOutputStream byteArrayInputStream = new ByteArrayOutputStream();
            jrPdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayInputStream));
            jrPdfExporter.exportReport();

            File fileDirectory = new File(file.getParent());
            if (!fileDirectory.exists() || !fileDirectory.isDirectory()) {
                fileDirectory.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream outputStream = new FileOutputStream(file.getAbsolutePath());
            outputStream.write(byteArrayInputStream.toByteArray());
            outputStream.close();

            return file;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
