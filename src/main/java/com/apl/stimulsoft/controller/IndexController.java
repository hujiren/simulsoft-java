package com.apl.stimulsoft.controller;

import com.stimulsoft.base.licenses.StiLicense;
import com.stimulsoft.base.serializing.StiDeserializationException;
import com.stimulsoft.report.StiReport;
import com.stimulsoft.report.StiSerializeManager;
import com.stimulsoft.web.classes.StiRequestParams;
import com.stimulsoft.webdesigner.StiWebDesigerHandler;
import com.stimulsoft.webdesigner.StiWebDesignerOptions;
import com.stimulsoft.webdesigner.enums.StiWebDesignerTheme;
import com.stimulsoft.webviewer.StiWebViewerOptions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Controller
public class IndexController {

    private final String reportFileName = "test.mrt";
    private final String reportPath = "G:/java/apl-stimulsoft-report-java/reports/";
    //private final String savePath = "/Volumes/data/dev/rust/rust-study/apl-demo/webdesigner/reports/";


    @RequestMapping("/webdesigner")
    public String webdesigner(HttpServletRequest request){
        StiLicense.setKey("ff");
        StiWebDesignerOptions options = new StiWebDesignerOptions();
        //options.setLocalization(request.getSession().getServletContext().getRealPath("/localization/de.xml"));
        options.setTheme(StiWebDesignerTheme.Office2013DarkGrayBlue);
        StiWebDesigerHandler handler = new StiWebDesigerHandler() {
            //Occurred on loading webdesinger. Must return edited StiReport
            public StiReport getEditedReport(HttpServletRequest request) {
                try {
                    StiReport report = StiSerializeManager.deserializeReport(new FileInputStream(reportPath + reportFileName));
                    return report;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            //Occurred on opening StiReport. Method intended for populate report data.
            public void onOpenReportTemplate(StiReport report, HttpServletRequest request) {
            }

            //Occurred on new StiReport. Method intended for populate report data.
            public void onNewReportTemplate(StiReport report, HttpServletRequest request) {
            }

            //Occurred on save StiReport. Method must implement saving StiReport
            public void onSaveReportTemplate(StiReport report, StiRequestParams requestParams, HttpServletRequest request) {
                try {
                    //FileOutputStream fos = new FileOutputStream(reportPath + requestParams.designer.fileName);
                    FileOutputStream fos = new FileOutputStream(reportPath + reportFileName);
                    if (requestParams.designer.password != null) {
                        StiSerializeManager.serializeReport(report, fos, requestParams.designer.password);
                    } else {
                        StiSerializeManager.serializeReport(report, fos, true);
                    }
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        request.setAttribute("handler", handler);
        request.setAttribute("options", options);
        return "webdesigner";
    }

    @RequestMapping("/webviewer")
    public String webviewer(HttpServletRequest request) throws StiDeserializationException, SAXException, IOException {
        StiReport report = StiSerializeManager.deserializeReport(new File(reportPath));
        report.render();
        StiWebViewerOptions options = new StiWebViewerOptions();
        request.setAttribute("report", report);
        request.setAttribute("options", options);
        return "webviewer";
    }
}
